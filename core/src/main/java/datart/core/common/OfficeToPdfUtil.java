package datart.core.common;

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
}
