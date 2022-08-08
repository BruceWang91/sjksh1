package datart.core.common;

import org.jodconverter.JodConverter;
import org.jodconverter.LocalConverter;
import org.jodconverter.office.LocalOfficeManager;
import org.jodconverter.office.OfficeException;
import org.jodconverter.office.OfficeManager;
import org.jodconverter.office.OfficeUtils;

import java.io.File;

public class OfficeToPdfUtil {

    public static void f2pdf(String home, String old, String pdf) {

        String oldf = old;
//        .replace("/", "\\\\");
        String pdff = pdf;
//                .replace("/", "\\\\");
        //从本地建立连接
        OfficeManager officeManager = LocalOfficeManager.builder().officeHome(home).build();
        try {
            officeManager.start();
            //转换
            LocalConverter.builder()
                    .officeManager(officeManager)
                    .build()
                    .convert(new File(oldf))
                    .to(new File(pdff))
                    .execute();
        } catch (OfficeException e) {
            //捕捉异常自定义操作
        } finally {
            //关闭office连接
            OfficeUtils.stopQuietly(officeManager);
        }
    }

    public static void main(String[] args) {
        try {
            //据说jodconverter可以自动寻找转码路径，windows上试了可以，linux上好像没法自动找到路径，会报officehome not set and could not be auto-detected，所以设置转码工具路径
            LocalOfficeManager manager = LocalOfficeManager.builder().officeHome("D:\\LibreOffice").install().build();
            manager.start();
            JodConverter.convert(new File("D:\\ruoyi\\uploadPath\\upload\\2022\\06\\15\\法人治理结构及“三会一层”运作情况表（季报）_20220615152229A001.xlsx")).
                    to(new File("D:\\ruoyi\\uploadPath\\upload\\2022\\06\\15\\法人治理结构及“三会一层”运作情况表（季报）_20220615152229A001.pdf")).execute();
        } catch (OfficeException e) {
            e.printStackTrace();
        }
    }
}
