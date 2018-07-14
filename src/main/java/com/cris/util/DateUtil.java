package com.cris.util;


import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;
import java.util.regex.Pattern;

public class DateUtil {



    // 格式：年－月－日 小时：分钟：秒
    public static final String FORMAT_ONE = "yyyy-MM-dd HH:mm:ss";
    // 格式：年－月－日 小时：分钟
    public static final String FORMAT_TWO = "yyyy-MM-dd HH:mm";
    // 格式：年月日-小时分钟秒
    public static final String FORMAT_THREE = "yyyyMMdd-HHmmss";
    // 格式：年月日小时分钟秒
    public static final String FORMAT_FOUR = "yyyyMMddHHmmss";
    // 格式： 年-月-日 小时:分钟:秒
    public static final String FORMAT_FIVE = "yyyy-MM-dd HH:mm:ss";
    // 格式： 年-月-日 小时
    public static final String FORMATE_SIX = "yyyy-MM-dd HH";
    // 格式： 年月日小时
    public static final String FORMATE_SEVEN = "yyyyMMddHH";
    // 格式： 年月日
    public static final String FORMATE_EIGHT = "yyyyMMdd";
    // 格式： 年/月/日
    public static final String FORMATE_NINE = "yyyy/MM/dd";
    // 格式： 年-月-日T小时:分钟:秒
    public static final String FORMAT_TEN = "yyyy-MM-dd'T'HH:mm:ss";
    // 格式： 年月日小时分钟秒毫秒
    public static final String FORMAT_ELEVEN = "yyyyMMddHHmmssSSS";
    // 格式：年－月－日
    public static final String LONG_DATE_FORMAT = "yyyy-MM-dd";
    // 格式：月－日
    public static final String SHORT_DATE_FORMAT = "MM-dd";
    // 格式：小时：分钟：秒
    public static final String LONG_TIME_FORMAT = "HH:mm:ss";
    // 格式：年-月
    public static final String MONTG_DATE_FORMAT = "yyyy-MM";

    // 年的加减
    public static final int SUB_YEAR = Calendar.YEAR;

    // 月加减
    public static final int SUB_MONTH = Calendar.MONTH;

    // 天的加减
    public static final int SUB_DAY = Calendar.DATE;

    // 小时的加减
    public static final int SUB_HOUR = Calendar.HOUR;

    // 分钟的加减
    public static final int SUB_MINUTE = Calendar.MINUTE;

    // 秒的加减
    public static final int SUB_SECOND = Calendar.SECOND;

    public static final int HOUR_OF_DAY = Calendar.HOUR_OF_DAY;

    static final String dayNames[] = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};

    @SuppressWarnings("unused")
    private static final SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public DateUtil() {
    }

    /**
     * 把符合日期格式的字符串转换为日期类型
     * @param format 格式化
     * @param dateStr 日期格式的字符串
     * @return Date 日期类型
     */
    public static Date stringtoDate(String dateStr, String format) {
        Date d = null;
        if ("".equals(dateStr)) {
            return d;
        }
        SimpleDateFormat formater = new SimpleDateFormat(format);
        try {
            formater.setLenient(false);
            d = formater.parse(dateStr);
        } catch (Exception e) {
            // logger.error ( e );
            d = null;
        }
        return d;
    }

    /**
     * 把符合日期格式的字符串转换为日期类型
     * @param dateStr 日期字符串
     * @param format format
     * @param pos pos
     * @return Date
     */
    public static Date stringtoDate(String dateStr, String format,
                                              ParsePosition pos) {
        Date d = null;
        SimpleDateFormat formater = new SimpleDateFormat(format);
        try {
            formater.setLenient(false);
            d = formater.parse(dateStr, pos);
        } catch (Exception e) {
            // logger.error ( e );
            d = null;
        }
        return d;
    }

    /**
     * 把日期转换为字符串
     *
     * @param date 日期
     * @param format format
     * @return String 日期字符串
     */
    public static String dateToString(Date date, String format) {
        String result = "";
        SimpleDateFormat formater = new SimpleDateFormat(format);
        try {
            result = formater.format(date);
        } catch (Exception e) {
            // logger.error ( e );
        }
        return result;
    }

    /**
     * 获取当前时间的指定格式
     *
     * @param format 格式化串
     * @return String 当前时间的指定格式
     */
    public static String getCurrDate(String format) {
        return dateToString(new Date(), format);
    }

    /**
     * 两个日期相加
     * @param dateKind 日期类型
     * @param dateStr  日期字符串
     * @param amount 天数
     * @return String
     */
    public static String dateSub(int dateKind, String dateStr, int amount) {
        Date date = stringtoDate(dateStr, FORMAT_ONE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(dateKind, amount);
        return dateToString(calendar.getTime(), FORMAT_ONE);
    }

    /**
     * 两个日期相减
     *
     * @param firstTime 被减日期
     * @param secTime 减日期
     * @return 相减得到的秒数
     */
    public static long timeSub(String firstTime, String secTime) {
        long first = stringtoDate(firstTime, FORMAT_ONE).getTime();
        long second = stringtoDate(secTime, FORMAT_ONE).getTime();
        return (second - first) / 1000;
    }

    /**
     * 两个日期相减
     *
     * @param firstTime 被减日期
     * @param secTime 减日期
     * @return 相减得到的秒数
     */
    public static long timeSub(Date firstTime, Date secTime) {
        long first = firstTime.getTime();
        long second = secTime.getTime();
        return (second - first) / 1000;
    }

    /**
     * 获得某月的天数
     *
     * @param year  年份
     * @param month 月份
     * @return int
     */
    public static int getDaysOfMonth(String year, String month) {
        int days = 0;
        if (month.equals("1") || month.equals("3") || month.equals("5")
                || month.equals("7") || month.equals("8") || month.equals("10")
                || month.equals("12")) {
            days = 31;
        } else if (month.equals("4") || month.equals("6") || month.equals("9")
                || month.equals("11")) {
            days = 30;
        } else {
            if ((Integer.parseInt(year) % 4 == 0 && Integer.parseInt(year) % 100 != 0)
                    || Integer.parseInt(year) % 400 == 0) {
                days = 29;
            } else {
                days = 28;
            }
        }

        return days;
    }

    /**
     * 获取某年某月的天数
     *
     * @param year  int
     * @param month int 月份[1-12]
     * @return int
     */
    public static int getDaysOfMonth(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, 1);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获得当前日期
     *
     * @return int
     */
    public static int getToday() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DATE);
    }

    /**
     * 获得当前月份
     *
     * @return int
     */
    public static int getToMonth() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 获得当前年份
     *
     * @return int
     */
    public static int getToYear() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 返回日期的天
     *
     * @param date Date
     * @return int
     */
    public static int getDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DATE);
    }

    /**
     * 返回日期的年
     *
     * @param date Date
     * @return int
     */
    public static int getYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 返回日期的月份，1-12
     *
     * @param date Date
     * @return int
     */
    public static int getMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 计算两个日期相差的天数，如果date2大于date1 返回正数，否则返回负数
     *
     * @param date1 Date
     * @param date2 Date
     * @return long
     */
    public static long dayDiff(Date date1, Date date2) {
        return (date2.getTime() - date1.getTime()) / 86400000;
    }

    /**
     * 比较两个日期的年差
     *
     * @param before  日期
     * @param after 日期
     * @return int
     */
    public static int yearDiff(String before, String after) {
        Date beforeDay = stringtoDate(before, LONG_DATE_FORMAT);
        Date afterDay = stringtoDate(after, LONG_DATE_FORMAT);
        return getYear(afterDay) - getYear(beforeDay);
    }

    /**
     * 比较指定日期与当前日期的差
     *
     *
     * @param after 日期
     * @return int
     */
    public static int yearDiffCurr(String after) {
        Date beforeDay = new Date();
        Date afterDay = stringtoDate(after, LONG_DATE_FORMAT);
        return getYear(beforeDay) - getYear(afterDay);
    }

    /**
     * 比较指定日期与当前日期的差
     *
     * @param before 指定日期
     * @return long
     * @author chenyz
     */
    public static long dayDiffCurr(String before) {
        Date currDate = DateUtil.stringtoDate(currDay(), LONG_DATE_FORMAT);
        Date beforeDate = stringtoDate(before, LONG_DATE_FORMAT);
        return (currDate.getTime() - beforeDate.getTime()) / 86400000;

    }

    /**
     * 获取每月的第一周
     *
     * @param year 年份
     * @param month 月份
     * @return int
     * @author chenyz
     */
    public static int getFirstWeekdayOfMonth(int year, int month) {
        Calendar c = Calendar.getInstance();
        c.setFirstDayOfWeek(Calendar.SATURDAY); // 星期天为第一天
        c.set(year, month - 1, 1);
        return c.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 获取每月的最后一周
     *
     * @param year 年份
     * @param month 月份
     * @return int
     * @author chenyz
     */
    public static int getLastWeekdayOfMonth(int year, int month) {
        Calendar c = Calendar.getInstance();
        c.setFirstDayOfWeek(Calendar.SATURDAY); // 星期天为第一天
        c.set(year, month - 1, getDaysOfMonth(year, month));
        return c.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 获得当前日期字符串，格式"yyyy-MM-dd HH:mm:ss"
     *
     * @return String
     */
    public static String getNow() {
        Calendar today = Calendar.getInstance();
        return dateToString(today.getTime(), FORMAT_ONE);
    }

    /**
     * 根据生日获取星座
     *
     * @param birth YYYY-mm-dd
     * @return String
     */
    public static String getAstro(String birth) {
        if (!isDate(birth)) {
            birth = "2000" + birth;
        }
        if (!isDate(birth)) {
            return "";
        }
        int month = Integer.parseInt(birth.substring(birth.indexOf("-") + 1,
                birth.lastIndexOf("-")));
        int day = Integer.parseInt(birth.substring(birth.lastIndexOf("-") + 1));
        String s = "魔羯水瓶双鱼牡羊金牛双子巨蟹狮子处女天秤天蝎射手魔羯";
        int[] arr = {20, 19, 21, 21, 21, 22, 23, 23, 23, 23, 22, 22};
        int start = month * 2 - (day < arr[month - 1] ? 2 : 0);
        return s.substring(start, start + 2) + "座";
    }

    /**
     * 判断日期是否有效,包括闰年的情况
     *
     * @param date YYYY-mm-dd
     * @return boolean
     */
    public static boolean isDate(String date) {
        StringBuffer reg = new StringBuffer(
                "^((\\d{2}(([02468][048])|([13579][26]))-?((((0?");
        reg.append("[13578])|(1[02]))-?((0?[1-9])|([1-2][0-9])|(3[01])))");
        reg.append("|(((0?[469])|(11))-?((0?[1-9])|([1-2][0-9])|(30)))|");
        reg.append("(0?2-?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][12");
        reg.append("35679])|([13579][01345789]))-?((((0?[13578])|(1[02]))");
        reg.append("-?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))");
        reg.append("-?((0?[1-9])|([1-2][0-9])|(30)))|(0?2-?((0?[");
        reg.append("1-9])|(1[0-9])|(2[0-8]))))))");
        Pattern p = Pattern.compile(reg.toString());
        return p.matcher(date).matches();
    }

    /**
     * 取得指定日期过 months 月后的日期 (当 months 为负数表示指定月之前);
     * @param months  月份
     * @param date  日期 为null时表示当天
     * @return Date
     */
    public static Date nextMonth(Date date, int months) {
        Calendar cal = Calendar.getInstance();
        if (date != null) {
            cal.setTime(date);
        }
        cal.add(Calendar.MONTH, months);
        return cal.getTime();
    }

    /**
     * 取得指定日期过 day 天后的日期 (当 day 为负数表示指日期之前);
     * @param day 几天后的日期
     * @param date  日期 为null时表示当天
     *
     */
    public static Date nextDay(Date date, int day) {
        Calendar cal = Calendar.getInstance();
        if (date != null) {
            cal.setTime(date);
        }
        cal.add(Calendar.DAY_OF_YEAR, day);
        return cal.getTime();
    }

    /**
     * 取得距离今天 day 日的日期
     *
     * @param day 几天后的日期
     * @param format 格式化
     * @return String
     * @author chenyz
     */
    public static String nextDay(int day, String format) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DAY_OF_YEAR, day);
        return dateToString(cal.getTime(), format);
    }

    /**
     * 取得指定日期过 day 周后的日期 (当 day 为负数表示指定月之前)
     *
     * @param date 日期 为null时表示当天
     * @param week 几周后
     * @return Date
     */
    public static Date nextWeek(Date date, int week) {
        Calendar cal = Calendar.getInstance();
        if (date != null) {
            cal.setTime(date);
        }
        cal.add(Calendar.WEEK_OF_MONTH, week);
        return cal.getTime();
    }

    public static Date nextHour(Date date, int hour) {
        Calendar cal = Calendar.getInstance();
        if (date != null) {
            cal.setTime(date);
        }
        cal.add(Calendar.HOUR_OF_DAY, hour);
        return cal.getTime();
    }

    public static Date nextMinute(Date date, int minute) {
        Calendar cal = Calendar.getInstance();
        if (date != null) {
            cal.setTime(date);
        }
        cal.add(Calendar.MINUTE, minute);
        return cal.getTime();
    }

    public static Date nextSecond(Date date, int second) {
        Calendar cal = Calendar.getInstance();
        if (date != null) {
            cal.setTime(date);
        }
        cal.add(Calendar.SECOND, second);
        return cal.getTime();
    }

    /**
     * 获取当前的日期(yyyy-MM-dd)
     * @return String
     */
    public static String currDay() {
        return DateUtil.dateToString(new Date(), DateUtil.LONG_DATE_FORMAT);
    }

    /**
     * 获取昨天的日期
     *
     * @return String
     */
    public static String befoDay() {
        return befoDay(DateUtil.LONG_DATE_FORMAT);
    }

    /**
     * 根据时间类型获取昨天的日期
     *
     * @param format 格式化
     * @return String
     * @author chenyz
     */
    public static String befoDay(String format) {
        return DateUtil.dateToString(DateUtil.nextDay(new Date(), -1), format);
    }

    /**
     * 获取明天的日期
     * @return String
     */
    public static String afterDay() {
        return DateUtil.dateToString(DateUtil.nextDay(new Date(), 1),
                DateUtil.LONG_DATE_FORMAT);
    }

    /**
     * 取得当前时间距离1900/1/1的天数
     *
     * @return int
     */
    public static int getDayNum() {
        int daynum = 0;
        GregorianCalendar gd = new GregorianCalendar();
        Date dt = gd.getTime();
        GregorianCalendar gd1 = new GregorianCalendar(1900, 1, 1);
        Date dt1 = gd1.getTime();
        daynum = (int) ((dt.getTime() - dt1.getTime()) / (24 * 60 * 60 * 1000));
        return daynum;
    }

    /**
     * getDayNum的逆方法(用于处理Excel取出的日期格式数据等)
     *
     * @param day 天
     * @return Date
     */
    public static Date getDateByNum(int day) {
        GregorianCalendar gd = new GregorianCalendar(1900, 1, 1);
        Date date = gd.getTime();
        date = nextDay(date, day);
        return date;
    }

    /**
     * 针对yyyy-MM-dd HH:mm:ss格式,显示yyyymmdd
     * @param datestr 日期字符串
     * @return String
     */
    public static String getYmdDateCN(String datestr) {
        if (datestr == null)
            return "";
        if (datestr.length() < 10)
            return "";
        StringBuffer buf = new StringBuffer();
        buf.append(datestr.substring(0, 4)).append(datestr.substring(5, 7))
                .append(datestr.substring(8, 10));
        return buf.toString();
    }

    /**
     * 获取本月第一天
     *
     * @param format 格式化
     * @return String
     */
    public static String getFirstDayOfMonth(String format) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DATE, 1);
        return dateToString(cal.getTime(), format);
    }

    /**
     * 获取本月最后一天
     *
     * @param format 格式化
     * @return String
     */
    public static String getLastDayOfMonth(String format) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DATE, 1);
        cal.add(Calendar.MONTH, 1);
        cal.add(Calendar.DATE, -1);
        return dateToString(cal.getTime(), format);
    }

    /**
     * 获取某一个field之前指定时长的时间
     *
     * @param getField 指定时间
     * @param destField 结束时间
     * @param before 时间间隔
     * @return int
     */
    public static int getFieldBeforeNow(int getField, int destField, int before) {
        Calendar cal = Calendar.getInstance();
        cal.add(destField, -before);
        return cal.get(getField);
    }

    public static Date getDateBeforeNow(int destField, int before) {
        Calendar cal = Calendar.getInstance();
        cal.add(destField, -before);
        return cal.getTime();
    }

    public static Date getDateBeforeNow(int destField, int before, Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(destField, -before);
        return cal.getTime();
    }

    public static Date startHour() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();
    }

    public static Long getSequence() {
        String detestr = System.currentTimeMillis() + "";
        Random random = new Random();
        int n = random.nextInt(1000);
        while ((n + "").length() < 3) {
            n = random.nextInt(1000);
        }
        String result = detestr + n;
        Long dateLong = Long.valueOf(result);
        // System.out.println(dateLong+":"+n);
        return dateLong;
    }

    public static String long2Date(String longDate) {
        long sd = Long.parseLong(longDate + "000");
        Date dat = new Date(sd);
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(dat);
        SimpleDateFormat format = new SimpleDateFormat(FORMAT_ONE);
        String sb = format.format(gc.getTime());
        return sb;
    }

    public static String oneWeekByBeforOneDate() {
        return oneWeekByBeforOneDate(null);
    }

    public static String oneWeekByBeforOneDate(String format) {
        if (format == null || "".equals(format)) {
            format = DateUtil.LONG_DATE_FORMAT;
        }
        Date nowDate = new Date();
        Calendar nowcal = Calendar.getInstance();
        nowcal.setTime(nowDate);
        nowcal.add(Calendar.DAY_OF_YEAR, -7);
        return DateUtil.dateToString(nowcal.getTime(), format);
    }

    public static String getLast5MinByDate(Date date) {
        String time = dateToString(date, FORMAT_FOUR);
        String newTime = time.substring(0, 11);
        int min = Integer.parseInt(time.substring(11, 12));
        if (min >= 5) {
            min = 5;
        } else {
            min = 0;
        }
        newTime = newTime + String.valueOf(min) + "00";
        return newTime;
    }

    public static void main(String[] args) throws Exception {
    }

}
