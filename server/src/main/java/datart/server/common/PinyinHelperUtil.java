package datart.server.common;

import net.sourceforge.pinyin4j.PinyinHelper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PinyinHelperUtil {

    /**
     * 返回中文的首字母
     *
     * @param str
     * @return
     */
    public static String getPinYinHeadChar(String str, Boolean isname) {

        String convert = "";

        for (int j = 0; j < str.length(); j++) {

            char word = str.charAt(j);
            String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
            if (pinyinArray != null) {
                convert += pinyinArray[0].charAt(0);
            } else {
                convert += word;
            }
        }
        return regReplace(convert.toUpperCase(), isname);
    }

    public static String regReplace(String str, Boolean isname) {

        // '*','/',':','?','[','\\',']'
        String s = str.replaceAll("[\\\\\\*/:?\\[\\][\\s*]&[+-]%=：（）、()。._，,]", "");
        if (!isname) {
            Pattern pattern = Pattern.compile("[0-9]*");
            Matcher isNum = pattern.matcher(s.charAt(0) + "");
            if (isNum.matches()) {
                return 'A' + s;
            }
        }
        return s;
    }

    public static void main(String[] args) {
        String a = "-0.003";

        System.out.println(getPinYinHeadChar(a, false));
    }
}
