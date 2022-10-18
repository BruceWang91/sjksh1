package datart.server.common;

import datart.core.entity.FileSheets;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class UniversalExcelReaderUtil {
    private static final Logger log = LoggerFactory.getLogger(UniversalExcelReaderUtil.class);
    private static final String XLS = "xls";
    private static final String DXLS = "XLS";
    private static final String XLSX = "xlsx";
    private static final String DXLSX = "XLSX";
    private static final String ET = "et";
    private static Calendar calendar = Calendar.getInstance();
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");

    /**
     * 获取excle中的数据据
     *
     * @return List<List < Map < String, Object>>> 存放整个excle所有的sheet表数据，
     * 格式：
     * [
     * [{},{},{}], // sheet1数据
     * [{},{},{}], // sheet2数据
     * [{},{},{}], // sheet3数据
     * ....
     * ]
     */
    public static List<List<Map<String, Object>>> getDataFromExcel(MultipartFile file, List<FileSheets> fileSheetsList) {
        String fileName = file.getOriginalFilename();
        if (StringUtils.isEmpty(fileName)) {
            log.warn("文件名获取不到");
            throw new RuntimeException("文件名获取不到");
        }
        // 获取Excel后缀名
        String fileType = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
        if (Arrays.asList(XLS, DXLS, XLSX, DXLSX, ET).contains(fileType) == false) {
            log.warn("文件后缀名不正确");
            throw new RuntimeException("文件后缀名不正确");
        }
        // 开始获取工作簿对象
        Workbook excelObject = null;
        try {
            excelObject = getExcelObject(file.getInputStream(), fileType);
        } catch (IOException e) {
            log.error("获取工作簿对象异常：", e);
            throw new RuntimeException("获取工作簿对象异常");
        }
        if (excelObject == null) {
            log.warn("获取工作簿对象失败");
            throw new RuntimeException("获取工作簿对象失败");
        }
        //开始获取excel文件中的数据
        List<List<Map<String, Object>>> lists = readExcelData(excelObject, fileSheetsList);
        return lists;
    }

    /**
     * 获取excel中的数据
     * 支持多个sheet表格
     */
    private static List<List<Map<String, Object>>> readExcelData(Workbook workbook, List<FileSheets> fileSheetsList) {
        //获取excle中的sheet表
        int numberOfSheets = workbook.getNumberOfSheets();
        if (numberOfSheets <= 0) {
            log.warn("没有读取到excle文件中的sheet表格");
            throw new RuntimeException("没有读取到excle文件中的sheet表格");
        }

        /*
            存放整个excle所有的sheet表数据，
            格式：
            [
                [{},{},{}], // sheet1数据
                [{},{},{}], // sheet2数据
                [{},{},{}], // sheet3数据
                ....
            ]
        */
        List<List<Map<String, Object>>> excelDataList = new LinkedList<List<Map<String, Object>>>();
        // 开始循环每个sheet表格
        for (int sheetNum = 0; sheetNum < workbook.getNumberOfSheets(); sheetNum++) {
            if (CollectionUtils.isNotEmpty(fileSheetsList)) {
                for (FileSheets fileSheets : fileSheetsList) {
                    if (fileSheets.getOrderNum() == sheetNum) {
                        //预设好的开始行
                        int startRow = fileSheets.getStartRow();
                        //当前sheet的数据
                        List<Map<String, Object>> sheetData = new LinkedList<Map<String, Object>>();
                        Sheet sheet = workbook.getSheetAt(sheetNum);
                        if (sheet == null) {
                            log.warn("读取不到第" + (sheetNum + 1) + "个工作表");
                            continue;
                        }
                        // 开始获取工作表中的每一行数据
                        // 获取第一行数据 ，一般用于表头
                        int firstRowNum = sheet.getFirstRowNum();
                        Row firstRow = sheet.getRow(firstRowNum);
                        if (firstRow == null) {
                            log.warn("解析Excel失败，在第一行没有读取到任何数据！行号:{}", firstRowNum);
                        }
                        int rowStart = firstRowNum + startRow; // 数据开始的行
                        int rowEnd = sheet.getPhysicalNumberOfRows(); // 数据结束的行
                        // 循环数据开始的行和数据结束的行，其中的数据为需要的数据
                        for (int rowNum = rowStart; rowNum < rowEnd; rowNum++) {
                            Row row = sheet.getRow(rowNum); // 获取数据行
                            if (row == null) {
                                log.warn("第" + (sheetNum + 1) + "sheet表的第" + rowNum + "行读取不到数据");
                                continue;
                            }
                            //自定义读取的数据列
                            //todo
                            Map<String, Object> map = readColumnData(row, sheet.getRow(rowNum)); // 每一行的数据
                            if ((boolean) map.get("flag")) {
                                sheetData.add(map);
                            }
                        }
                        if (sheetData != null && sheetData.size() > 0) {
                            excelDataList.add(sheetData);
                        }
                    }
                }
            }
        }
        return excelDataList;
    }

    /**
     * 根据文件后缀获取excel工作簿对象
     */
    private static Workbook getExcelObject(InputStream inputStream, String fileType) throws IOException {
        Workbook workbook = null;
        if (fileType.equalsIgnoreCase(XLS) || fileType.equalsIgnoreCase(ET)) {
            workbook = new HSSFWorkbook(inputStream);
        } else if (fileType.equalsIgnoreCase(XLSX)) {
            workbook = new XSSFWorkbook(inputStream);
        }
        return workbook;
    }

    //------------------自定义读取的数据列方法-------------------

    /**
     * 读取sheet表的每一行每一列的内容
     *
     * @param dataRow  数据行
     * @param titleRow 表头行
     */
    private static Map<String, Object> readColumnData(Row dataRow, Row titleRow) {
        Map<String, Object> resMap = new LinkedHashMap<String, Object>();
        int lastTitleCells = titleRow.getPhysicalNumberOfCells();//最后一列数据列号，根据表头获取，只读取有表头的数据列
        Cell cell = null;
        boolean flag = false;
        for (int i = 0; i < lastTitleCells; i++) {
            cell = dataRow.getCell(i);
            String value = convertCellValueToString(cell);
            if (StringUtils.isNotBlank(value)) {
                flag = true;
            }
            resMap.put(i + "", value);
        }
        resMap.put("flag", flag);
        return resMap;
    }


    /**
     * 将单元格内容转换为字符串
     *
     * @param cell
     * @return
     */
    private static String convertCellValueToString(Cell cell) {
        if (cell == null) {
            return "";
        }
        //把数字转换成string，防止12.0这种情况
//        if(cell.getCellType() == CellType.NUMERIC){
//            cell.setCellType(CellType.STRING);
//        }
//        cell.setCellType(CellType.STRING);
        String returnValue = "";
        switch (cell.getCellType()) {
            case NUMERIC:   //数字
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    // 日期格式化
                    Date date = cell.getDateCellValue();
                    returnValue = DateFormatUtil.dateFormatUtil(date);
                } else {
                    Double doubleValue = cell.getNumericCellValue();
                    // 格式化科学计数法，保留小数位
                    DecimalFormat df = new DecimalFormat("0.000000");
                    returnValue = df.format(doubleValue);
                }
                break;
            case STRING:    //字符串
                returnValue = cell.getStringCellValue();
                break;
            case BOOLEAN:   //布尔
                Boolean booleanValue = cell.getBooleanCellValue();
                returnValue = booleanValue.toString();
                break;
            case BLANK:     // 空值
                returnValue = "";
                break;
            case FORMULA:   // 公式
//                returnValue = cell.getCellFormula();
                try {
                    returnValue = String.valueOf(cell.getNumericCellValue());
                } catch (IllegalStateException e) {
                    returnValue = "";
                }
                break;
            case ERROR:     // 故障
                break;
            default:
                break;
        }
        return returnValue;
    }

    //转换excel导入之后时间变为数字,年月日时间
    public static String getCorrectDay(int i) {
        calendar.set(1900, 0, -1, 0, 0, 0);
        calendar.add(calendar.DATE, i);
        Date time = calendar.getTime();
        String s = simpleDateFormat.format(time);
        return s;
    }

    /**
     * 给实体赋值，由于不止一个需要这样，所以做成泛型的方式
     *
     * @param clazz
     * @param map
     * @param <T>
     * @return
     */
    public static <T> T convent(Class<T> clazz, Map<String, Object> map) {
        T t = null;
        try {
            t = clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        Field[] fields = clazz.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            //跳过访问控制
            field.setAccessible(true);
            try {
                if (map.containsKey(field.getName())) {
                    field.set(t, map.get(field.getName()));
                }
            } catch (IllegalAccessException e) {
                System.out.println("属性注入失败：" + e);
            }
        }
        return t;
    }
}