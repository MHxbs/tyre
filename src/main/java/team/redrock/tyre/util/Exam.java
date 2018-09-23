package team.redrock.tyre.util;

import lombok.extern.slf4j.Slf4j;
import team.redrock.tyre.domain.ExamInfo;
import team.redrock.tyre.domain.ExamTime;
import team.redrock.tyre.domain.ReExamInfo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class Exam {

    public static List<ExamInfo> getExam(String data){
        String term="";
        Pattern pattern1=Pattern.compile("<li>〉〉(.*?) 考试安排查询 〉〉(.*?) </li>");
        Matcher matcher1=pattern1.matcher(data);
        if (matcher1.find()){
            term=getTerm(matcher1.group(1));
        }


        Pattern pattern=Pattern.compile("<tr><td>(.*?)</td><td>(.*?)</td><td>(.*?)</td><td>(.*?)</td><td>(.*?)</td><td>(.*?)</td>\t\t\t<td>(.*?)</td><td>(.*?)</td><td>(.*?)</td><td>(.*?)</td><td>(.*?)</td><td>(.*?)</td></tr>");
        Matcher matcher=pattern.matcher(data);
        List<ExamInfo> examInfoList=new ArrayList<>();
        while (matcher.find()){
            ExamInfo examInfo=new ExamInfo();
            examInfo.setTerm(term);
            examInfo.setStudent(matcher.group(2));
            examInfo.setCourse(matcher.group(6));
            examInfo.setWeekday(matcher.group(8));
            examInfo.setClassroom(matcher.group(10));
            examInfo.setSeat(matcher.group(11));
            String status=matcher.group(12);
            if (status.equals("")){
                examInfo.setStatus("待定");
            }else {
                examInfo.setStatus(status);
            }
            String weekStr=matcher.group(7);
            String time=matcher.group(9);

            ExamTime examTime=getExamTime(time);
            examInfo.setTime(examTime.getTime());
            examInfo.setBegin_time(examTime.getBegin_time());
            examInfo.setEnd_time(examTime.getEnd_time());

            examInfo.setDate("第"+weekStr+"星期"+matcher.group(8)+"第"+examTime.getLesson_time()+"节");

            examInfo.setWeek(getWeek(weekStr));



            examInfoList.add(examInfo);
        }

        return examInfoList;
    }


    public static List<ReExamInfo> getReExam(String data,String stuNum){
        Pattern pattern=Pattern.compile("<tr><td>(.*?)</td><td>"+stuNum+"</td><td >(.*?)</td><td >(.*?)</td>\t\t\t\t<td >(.*?)</td><td >(.*?)</td><td >(.*?)</td><td >(.*?)</td>\t\t\t\t<td >(.*?)</td></tr>");
        Matcher matcher=pattern.matcher(data);
        List<ReExamInfo> reExamInfoList= new ArrayList<>();
        while (matcher.find()){
            ReExamInfo reExamInfo=new ReExamInfo();
            reExamInfo.setStudent(stuNum);
            reExamInfo.setCourse(matcher.group(4));
            reExamInfo.setClassroom(matcher.group(7));
            reExamInfo.setSeat(matcher.group(8));
            reExamInfo.setDate(getDate(matcher.group(5)));
            reExamInfo.setTime(matcher.group(6));
            reExamInfo.setWeek(-1);
            reExamInfo.setWeeday(getDayOfWeekByDate(matcher.group(5)));
            reExamInfoList.add(reExamInfo);
        }

        return reExamInfoList;
    }


    public static String getWeek(String weekStr){
        weekStr=weekStr.replaceAll(" ","");

        Pattern pattern=Pattern.compile("(\\d*?)周");
        Matcher matcher=pattern.matcher(weekStr);
        if (matcher.find()){
            return matcher.group(1);
        }
        else {
            return null;
        }

    }


    public static ExamTime getExamTime(String time){

        time=time.replaceAll(" ","");
        time+=" ";

        Pattern pattern=Pattern.compile("第(.*?)节(.*?)-(.*?) ");

        Matcher matcher=pattern.matcher(time);
        ExamTime examTime=new ExamTime();
        if (matcher.find()){
            examTime.setLesson_time(matcher.group(1));
            examTime.setTime(matcher.group(2)+"-"+matcher.group(3));
            examTime.setBegin_time(matcher.group(2));
            examTime.setEnd_time(matcher.group(3));
        }

        return examTime;

    }

    public static String getTerm(String termStr){
        String term="";
        Pattern pattern=Pattern.compile("(.*?)-(.*?)学年(.*?)学期");
        Matcher matcher=pattern.matcher(termStr);
        if (matcher.find()){
            term=matcher.group(1)+matcher.group(3);
        }
        return term;
    }


    public static int  getDayOfWeekByDate(String date)
    {
        int dayOfweek =-1;
        try
        {
            SimpleDateFormat myFormatter = new SimpleDateFormat("yyyyMMdd");

            Calendar calendar=Calendar.getInstance();
            calendar.setTime(myFormatter.parse(date));
            dayOfweek=calendar.get(Calendar.DAY_OF_WEEK);
        }
        catch (Exception e)
        {
            log.error("错误！");
        }
        dayOfweek-=1;
        if (dayOfweek==0){
            dayOfweek=7;
        }
        return dayOfweek;
    }

    public static String getDate(String dateStr){
        dateStr=dateStr.replaceAll(" ","");
        String month=dateStr.substring(4,6);
        String day=dateStr.substring(6,8);


        String date=month+"月"+day+"日";
        return date;

    }
}
