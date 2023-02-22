package datart.server.common;

import org.springframework.util.FileCopyUtils;

import java.io.*;

/**
 * 文件二进制流转换
 */
public class BinUtil {

    /**
     * 文件转为二进制数组
     * 等价于fileToBin
     *
     * @param file
     * @return
     */
    public static byte[] getFileToByte(File file) {
        byte[] by = new byte[(int) file.length()];
        try {
            InputStream is = new FileInputStream(file);
            ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
            byte[] bb = new byte[2048];
            int ch;
            ch = is.read(bb);
            while (ch != -1) {
                bytestream.write(bb, 0, ch);
                ch = is.read(bb);
            }
            by = bytestream.toByteArray();
        } catch (Exception ex) {
            throw new RuntimeException("transform file into bin Array 出错1", ex);
        }
        return by;
    }

    /**
     * 文件转为二进制数组
     *
     * @param file
     * @return
     */
    public static byte[] fileToBinArray(File file) {
        try {
            InputStream fis = new FileInputStream(file);
            byte[] bytes = FileCopyUtils.copyToByteArray(fis);
            return bytes;
        } catch (Exception ex) {
            throw new RuntimeException("transform file into bin Array 出错2", ex);
        }
    }

    /**
     * 文件转为二进制字符串
     *
     * @param file
     * @return
     */
    public static String fileToBinStr(File file) {
        try {
            InputStream fis = new FileInputStream(file);
            byte[] bytes = FileCopyUtils.copyToByteArray(fis);
            return new String(bytes, "ISO-8859-1");
        } catch (Exception ex) {
            throw new RuntimeException("transform file into bin string 出错3", ex);
        }
    }

    /**
     * 二进制字符串转文件
     *
     * @param bin
     * @param fileName
     * @param parentPath
     * @return
     */
    public static File binToFile(String bin, String fileName, String parentPath) {
        try {
            File fout = new File(parentPath, fileName);
            fout.createNewFile();
            byte[] bytes = bin.getBytes("ISO-8859-1");
            FileCopyUtils.copy(bytes, fout);
            return fout;
        } catch (Exception ex) {
            throw new RuntimeException("transform bin into File 出错", ex);
        }
    }

    public static byte[] str2byte(String str){

        byte[] bytes = new byte[0];
        try {
            bytes = str.getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        return bytes;
    }

    public static String byte2str (byte[] bytes){

        String str = null;
        try {
            str = new String(bytes,"utf-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        return str;
    }

    public static void main(String[] args) {

        File file = new File("D://人员名单(1).doc");
        String fileName = file.getName();
        try {
            binToFile(new String(getFileToByte(file), "ISO-8859-1"), fileName, "D://测试byte");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        byte[] str = getFileToByte(file);
        System.out.println(str);
    }
}
