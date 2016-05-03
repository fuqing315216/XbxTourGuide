package com.xbx.tourguide.util;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串验证
 */
public class VerifyUtil {

    /**
     * 判读字符串是否为空
     *
     * @param inputString
     * @return
     */
    public static boolean isNullOrEmpty(String inputString) {
        if (TextUtils.isEmpty(inputString)) {
            return true;
        }
        if ("null".equals(inputString)) {
            return true;
        }
        if (inputString == null) {
            return true;
        }
        return false;
    }

    /**
     * 比较两个字符串是否相等
     *
     * @param arg1
     * @param arg2
     * @return
     */
    public static boolean compareEquals(String arg1, String arg2) {
        if (VerifyUtil.isNullOrEmpty(arg1)) {
            if (VerifyUtil.isNullOrEmpty(arg2)) {
                return true;
            } else {
                return false;
            }
        } else {
            return arg1.equals(arg2);
        }
    }

    /**
     * 复制字符串
     *
     * @param inputString
     * @return
     */
    public static String copy(String inputString) {
        if (inputString == null) {
            return null;
        }
        return new String(inputString);
    }

    /**
     * 时间格式转换HH:mm:ss
     *
     * @param hour
     * @param min
     * @param sec
     * @return
     */
    public static String formatDuration(int hour, int min, int sec) {

        StringBuilder strBuider = new StringBuilder();
        if (hour < 10) {
            strBuider.append(0);
        }
        strBuider.append(hour).append(":");
        if (min < 10) {
            strBuider.append(0);
        }
        strBuider.append(min).append(":");
        if (sec < 10) {
            strBuider.append(0);
        }
        strBuider.append(sec);

        return strBuider.toString();
    }

    /**
     * 是否为电话号码13X、14X，15X、17X，18X
     *
     * @param phoneNumber
     * @return
     */
    public static boolean isTelPhoneNumber(String phoneNumber) {
        Pattern p = Pattern
                .compile("^13[0-9]{1}[0-9]{8}$|14[0-9]{1}[0-9]{8}$|15[0-9]{1}[0-9]{8}$|17[0-9]{1}[0-9]{8}$|18[0-3,5-9]{1}[0-9]{8}$");
        Matcher m = p.matcher(phoneNumber);
        return m.matches();
    }

    /**
     * 是否为邮箱
     *
     * @param email
     * @return
     */
    public static boolean isEmailNumber(String email) {
        Pattern p = Pattern
                .compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * 身份证验证
     *
     * @param id
     * @return
     */
    public static boolean isCardID(String id) {
        Pattern p = Pattern
                .compile("^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$|^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$");
        Matcher m = p.matcher(id);
        return m.matches();
    }

    /**
     * 密码验证（字母、数字、特殊符号）
     * 8-20 至少有大学字母
     *
     * @param id
     * @return
     */
    public static boolean isPassWord(String id) {
        Pattern p = Pattern
                .compile("^(?=.*\\d)(?=.*[A-Z])[a-zA-Z\\d]{8,20}$");
        Matcher m = p.matcher(id);
        return m.matches();
    }

    /**
     * 中文字验证
     *
     * @param
     * @return
     */
    public static boolean checkNameChese(String name) {
        boolean res = true;
        char[] cTemp = name.toCharArray();
        for (int i = 0; i < name.length(); i++) {
            if (!isChinese(cTemp[i])) {
                res = false;
                break;
            }
        }
        return res;
    }

    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }

//    public static boolean isChinese(String str) {
//
//        char[] chars = str.toCharArray();
//        boolean isGB2312 = false;
//        for (int i = 0; i < chars.length; i++) {
//            byte[] bytes = ("" + chars[i]).getBytes();
//            if (bytes.length == 2) {
//                int[] ints = new int[2];
//                ints[0] = bytes[0] & 0xff;
//                ints[1] = bytes[1] & 0xff;
//
//                if (ints[0] >= 0x81 && ints[0] <= 0xFE &&
//                        ints[1] >= 0x40 && ints[1] <= 0xFE) {
//                    isGB2312 = true;
//                    break;
//                }
//            }
//        }
//        return isGB2312;
//    }

    /**
     * 字母
     *
     * @param id
     * @return
     */
    public static boolean isLetter(String id) {
        Pattern p = Pattern.compile("^[A-Za-z]+");
        Matcher m = p.matcher(id);
        return m.matches();
    }

    /**
     * 数字
     *
     * @param id
     * @return
     */
    public static boolean isNumber(String id) {
        Pattern p = Pattern.compile("^[0-9]\\d*|0");
        Matcher m = p.matcher(id);
        return m.matches();
    }

    /**
     * @param date
     * @param format yy-hh-mm
     * @return
     */
    public static String dateFomat(Date date, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(date);
    }

    public static String dateFormat(String date, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(date);
    }

    public static long dateFomat(String date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return formatter.parse(date).getTime();
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 字符串转为经纬度
     *
     * @param str
     * @return
     */
    public static int stringToGeopoint(String str) {
        // int) (39.918* 1E6)
        int i = 0;
        try {
            float temp = Float.parseFloat(str);
            i = (int) (temp * 1E6);
        } catch (Exception ex) {
            i = 0;
        }
        return i;
    }

    public static String dateFormat(long ms) {
        long temp = System.currentTimeMillis() - ms;
        Date date = new Date(ms);
        if (temp < 86400000) {
            return dateFomat(date, "HH:mm");
        } else {
            return dateFomat(date, "MM-dd");
        }
    }

}
