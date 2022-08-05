package datart.server.common;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatUtil {

    /**
     * 传入一个date类型的数据，将其转换为指定格式的并返回
     */
    public static String dateFormatUtil(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }
}
