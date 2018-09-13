package team.redrock.tyre.util;

import org.omg.CORBA.TIMEOUT;
import team.redrock.tyre.domain.Time;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class TimeUtil {
    /**
     * 通过timeStr 例如：星期1第9-10节1-16周
     * 得到day lesson week hash_day,hash_lesson
     * @param timeStr
     * @return
     */
    public static Time getTime(String timeStr){
        timeStr.replace(" ","");
        Time time=new Time();
        time.setDay(getDay(timeStr,false));
        time.setLesson(getLesson(timeStr,false));
        time.setWeek(getWeek(timeStr));
        time.setHash_day(Integer.parseInt(getDay(timeStr,true))-1);
        time.setHash_lesson(Integer.parseInt(getLesson(timeStr,true))-1);
        return time;
    }


    //传入这样的格式：：星期一第9-10节1-16周，解析得到有哪些周要上课，返回TreeSet
    public static List getWeek(String time){
        String time1=time.replaceAll(" ","");

        time1+=" ";
        List<Integer> allWeek=new ArrayList();
        //把周数按逗号分开，然后分别判断其中的周数
        Pattern pattern=Pattern.compile("星期\\d第(.*?)节(.*?) ");
        Matcher matcher=pattern.matcher(time1);
        if (matcher.find()){

            String[] ss=matcher.group(2).split(",");
            for (int i=0;i<ss.length;i++) {

                allWeek.addAll(getIntWeek(ss[i]));

            }
        }
        allWeek.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1-o2;
            }
        });
        return allWeek;

    }

    public static String getWeekModel(String weekStr){
        weekStr=weekStr.replaceAll(" ","");
        String result="";

        Pattern pattern=Pattern.compile("星期\\d第(.*?)节(.*?)");
        Matcher matcher=pattern.matcher(weekStr);
        if (matcher.find()) {
            String week=matcher.group(2);

            Pattern pattern2=Pattern.compile("(\\d*?)-(\\d*?)周双周");
            Matcher matcher2=pattern2.matcher(week);
            Pattern pattern3=Pattern.compile("(\\d*?)-(\\d*?)周单周");
            Matcher matcher3=pattern3.matcher(week);


            if (matcher2.find()){
                result="double";
            }else if (matcher3.find()){
                result="single";
            }else {
                result="all";
            }
        }
        return result;
    }

    // 把传过来的String类型的周数转化为set类型
    public static Set getIntWeek(String week){
        TreeSet weeks=new TreeSet();

        Pattern pattern1=Pattern.compile("(\\d*?)-(\\d*?)周");
        Matcher matcher1=pattern1.matcher(week);
        Pattern pattern2=Pattern.compile("(\\d*?)-(\\d*?)周双周");
        Matcher matcher2=pattern2.matcher(week);
        Pattern pattern3=Pattern.compile("(\\d*?)-(\\d*?)周单周");
        Matcher matcher3=pattern3.matcher(week);
        Pattern pattern4=Pattern.compile("(\\d*?)周");
        Matcher matcher4=pattern4.matcher(week);
        //是(\d*？)-(\d*？)周
        if (matcher1.matches()){
            int i;
            int weekBegin=Integer.parseInt(matcher1.group(1));
            int weekEnd=Integer.parseInt(matcher1.group(2));
            for (i=weekBegin;i<=weekEnd;i++) {
                weeks.add(i);
            }

            return weeks;
        }


        //是(\d*？)-(\d*？)双周
        if (matcher2.matches()){


            int weekBegin = Integer.parseInt(matcher2.group(1));
            int weekEnd = Integer.parseInt(matcher2.group(2));
            for (int i = weekBegin; i <= weekEnd; i++) {
                if (i % 2 == 0) {
                    weeks.add(i);
                }
            }
            return weeks;
        }

        // 是(\d*？)-(\d*？)单周
        if (matcher3.matches()){

            int weekBegin=Integer.parseInt(matcher3.group(1));
            int weekEnd=Integer.parseInt(matcher3.group(2));
            for (int i=weekBegin;i<=weekEnd;i++){
                if (i%2!=0){
                    weeks.add(i);
                }
            }
            return weeks;
        }

        // 是(\d*？)周
        if (matcher4.matches()){

            int singleWeek=Integer.parseInt(matcher4.group(1));
            weeks.add(singleWeek);
            return weeks;

        }
        return weeks;
    }

    //传入这样的格式：：星期一第9-10节1-16周，得到是星期几
    public static String getDay(String time,boolean num){
        //把字符串中空格全部去掉
        String time1=time.replaceAll(" ","");

        String result=null;
        Pattern pattern=Pattern.compile("星期(\\d)");
        Matcher matcher=pattern.matcher(time1);
        if (matcher.find()){
            if (num){
                result=matcher.group(1);
            }else {
                result = matcher.group();
            }
        }
        return  result;
    }
    //传入这样的格式：：星期一第9-10节1-16周，解析得到是第几节课
    public static String getLesson(String time,boolean num){
        String time1=time.replaceAll(" ","");

        String result=null;
        Pattern pattern=Pattern.compile("第(.*?)节");
        Matcher matcher=pattern.matcher(time1);
        if (matcher.find()){
            if (num){
                String[] a=matcher.group(1).split("-");
                result=a[0];
            }else {
                result = matcher.group();
            }
        }
        return  result;
    }
    // 得到是那几周的字符串
    public static String getWeekName(String time) {
        String time1 = time.replaceAll(" ", "");
        String result = null;
        time1 += " ";
        Pattern pattern = Pattern.compile("星期\\d第(.*?)节(.*?) ");
        Matcher matcher = pattern.matcher(time1);
        if (matcher.find()){

            result=matcher.group(2);
        }
        return result;
    }
}
