package team.redrock.tyre.util;

import team.redrock.tyre.entity.CourseInfo;
import team.redrock.tyre.domain.KebiaoTime;
import team.redrock.tyre.domain.Time;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KebiaoUtil {
    /**
     * 得到现在是第几周，是哪一个学期
     * @param data jwzx html
     * @return KebiaoTime
     */
    public static KebiaoTime getKebiaoTime(String data){
        KebiaoTime kebiaoTime=new KebiaoTime();
        Pattern pattern=Pattern.compile("今天是第 (\\d*?) 周");

        Matcher matcher=pattern.matcher(data);
        if (matcher.find()){
            kebiaoTime.setNowWeek(Integer.parseInt(matcher.group(1)));
        }

        Pattern pattern1=Pattern.compile("<li>〉〉(.*?) 学生课表>>(.*?)</li>");
        Matcher matcher1=pattern1.matcher(data);
        if (matcher1.find()){
            kebiaoTime.setTerm(matcher1.group(1));
        }

        return kebiaoTime;
    }

    /**
     * 从教务在线上拿课表
     * 通过列表爬，先爬主行，在通过courseNum，rowspan爬剩下的
     * @param data
     * @return
     * @throws IOException
     */

    public static List<CourseInfo> getTimeTableFromJWZX(String data) throws IOException {

        // 得到教务在线的html

        //System.out.println(data);

        // 因为按列表查询每个课程可能有几行
        // 所以先查第一个，得到rowspan，然后拼接restRegex得到完整的正则表达式

        // 最开始的匹配式
        String firstRegex="<tr ><td rowspan='(.*?)' align='center'>(.*?)</td>  \t\t\t\t\t<td (.*?)>(.*?)</td>\t\t\t\t\t<td (.*?)>(.*?)</td>\t\t\t\t\t<td (.*?)>(.*?)</td><td (.*?)>(.*?)</td><td>(.*?)</td> \t\t\t\t\t<td>(.*?)</td><td>(.*?)</td>\t\t\t\t\t<td (.*?)><a href='(.*?)' target=_blank>名单</a></td>\t\t\t\t\t<td (.*?)></td>\t\t\t\t\t</tr>";
        // 每行的匹配式
        String restRegex="<tr><td >(.*?)</td>\t\t\t\t\t<td>(.*?) </td><td>(.*?)</td></tr>";

        Pattern pattern=Pattern.compile(firstRegex);
        Matcher matcher=pattern.matcher(data);

        List<CourseInfo> courseInfoList=new ArrayList<>();
        while (matcher.find()){
            // 将内容set到对象中
            CourseInfo courseInfo=new CourseInfo();

            String courseStr=matcher.group(4);
            //System.out.println(courseStr);
            String[] courseArray=courseStr.split("-");
            courseInfo.setCourse(courseArray[1]);
            courseInfo.setCourse_num(courseArray[0]);
            courseInfo.setType(matcher.group(8));
            courseInfo.setTeacher(matcher.group(11));

            //time 的格式如：星期一第9-10节1-16周，调用
            //getDay()和getLesson()和getWeek()来分别得到
            //得到是星期几
            Time time=TimeUtil.getTime(matcher.group(12));
            courseInfo.setDay(time.getDay());
            //得到是第几节课
            courseInfo.setLesson(time.getLesson());
            courseInfo.setPeriod(time.getPeriod());
            courseInfo.setHash_day(time.getHash_day());
            courseInfo.setHash_lesson(time.getHash_lesson());
            //得到是那几周
            courseInfo.setRawWeek(TimeUtil.getWeekName(matcher.group(12)));
            courseInfo.setWeekModel(TimeUtil.getWeekModel(matcher.group(12)));

            List<Integer> totalWeek =time.getWeek();

            if(totalWeek.size()>0) {
                courseInfo.setWeek(totalWeek);

                courseInfo.setWeekBegin(totalWeek.get(0));

                courseInfo.setWeekEnd(totalWeek.get(totalWeek.size() - 1));
            }


            courseInfo.setClassroom(matcher.group(13));


            courseInfoList.add(courseInfo);


            String courseNum=matcher.group(6);
            int rowspan= Integer.parseInt(matcher.group(1));
            // 将得到的courseNum拼接到regex中
            String regex="<tr ><td rowspan='(.*?)' align='center'>(.*?)</td>  \t\t\t\t\t<td (.*?)>(.*?)</td>\t\t\t\t\t<td (.*?)>"+courseNum+"</td>\t\t\t\t\t<td (.*?)>(.*?)</td><td (.*?)>(.*?)</td><td>(.*?)</td> \t\t\t\t\t<td>(.*?)</td><td>(.*?)</td>\t\t\t\t\t<td (.*?)><a href='(.*?)' target=_blank>名单</a></td>\t\t\t\t\t<td (.*?)></td>\t\t\t\t\t</tr>";
            // 将剩下的几行拼接上
            for (int i=1;i<rowspan;i++){
                regex=regex+restRegex;
            }

            Pattern restPattern=Pattern.compile(regex);
            Matcher restMatcher=restPattern.matcher(data);

            if (restMatcher.find()){
                for (int i=16;i<=14+(rowspan-1)*3;i=i+3){
                    CourseInfo courseInfo1=CourseInfoUtil.exchange(courseInfo);
                    courseInfo1.setTeacher(restMatcher.group(i));
                    courseInfo1.setClassroom(restMatcher.group(i+2));
                    Time time1=TimeUtil.getTime(restMatcher.group(i+1));

                    courseInfo1.setDay(time1.getDay());
                    //得到是第几节课
                    courseInfo1.setLesson(time1.getLesson());
                    courseInfo1.setHash_lesson(time1.getHash_lesson());
                    courseInfo1.setHash_day(time1.getHash_day());
                    //得到是那几周
                    courseInfo1.setRawWeek(TimeUtil.getWeekName(restMatcher.group(i+1)));
                    courseInfo1.setWeekModel(TimeUtil.getWeekModel(restMatcher.group(i+1)));

                    List<Integer> totalWeek1 =time1.getWeek();
                    courseInfo1.setWeek(totalWeek1);
                    courseInfo1.setWeekBegin(totalWeek1.get(0));
                    courseInfo1.setWeekEnd(totalWeek1.get(totalWeek1.size()-1));

                    courseInfoList.add(courseInfo1);

                }
            }

        }
        // 因为是按列表爬的，所以要通过hash_day,hash_lesson排序
        courseInfoList.sort(new Comparator<CourseInfo>() {
            @Override
            public int compare(CourseInfo o1, CourseInfo o2) {
                int a1=(o1.getHash_day()+1)*10+o1.getHash_lesson();
                int a2=(o2.getHash_day()+1)*10+o2.getHash_lesson();
                return a1-a2;
            }
        });

        return courseInfoList;
    }


    // 通过教务处爬课表
   /* public static String getTimeTableFromJWC(String xh,String sfzh)  {

        // 拿到教务处的html
        String url="http://jwc.cqupt.edu.cn/showStuQmcj.php";
        String param="xh="+xh+"&sfzh="+sfzh;

        String data=SendUrl.getDataByPOST(url,param);
        System.out.println(data);

        // 进行正则匹配
        Pattern pattern=Pattern.compile("<tr><td>(.*?)</td><td>(.*?)</td>" +
                "<td>(.*?)</td><td>(.*?)</td><td>(.*?)</td><td>(.*?)</td><td>(.*?)</td><td>(.*?)</td></tr>");
        Matcher matcher=pattern.matcher(data);

        //
        matcher.find();
        while (matcher.find()){

            System.out.println(matcher.group(2));
        }





        return "";

    }*/


    // 通过教务在线公众号得到课表信息
   /* public static String getTimeTableFromJWCWeChat(String stuNum)  {


        String url="http://jwc.cqupt.edu.cn/weixin/search.php?action=kebiao&kebiaoTarget=student&searchKebiaoKey=";
        url+=stuNum;
        String data=null;
        JSONObject jsonObject = new JSONObject();

        // 得到教务在线公众号的html
        try {
            data=getDataByGet(url,"");
            System.out.println(data);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 每个星期的正则
        String weekRegex="<li data-role=\"list-divider\">(.*?)<span class=\"ui-li-count\">(.*?)</span></li>";
        // 每个课程的正则
        String courseRegex="<li><h2>(.*?)</h2><p><strong>(.*?)</strong></p><p>地点：(.*?) <br>教师：(.*?) <br>课程类别：(.*?)\t\t\t\t\t\t<br>选课状态：(.*?)</p><p class=\"ui-li-aside\">(.*?)</p></li>";


        // 先对星期进行正则匹配，得到该天有几节课
        Pattern pattern=Pattern.compile(weekRegex);
        Matcher matcher=pattern.matcher(data);

        while (matcher.find()){

            String weekStr=matcher.group(1);
            // 得到有几节课
            int num= Integer.parseInt(matcher.group(2));
            System.out.println(num);

            // 将courseRegex 与 weekRegex 拼在一起，进行再一次匹配
            String regex="";
            regex=weekRegex;
            for (int i=1;i<=num;i++){
                regex+=courseRegex;
            }
            //
            Pattern pattern1=pattern.compile(regex);
            Matcher matcher1=pattern1.matcher(data);

            if (matcher1.find()){
                for (int i=3;i<=num*7+2;i+=7) {
                    jsonObject.put("week",weekStr);
                    jsonObject.put("lesson",matcher1.group(i));
                    jsonObject.put("course",matcher1.group(i+1));
                    jsonObject.put("classroom",matcher1.group(i+2));
                    jsonObject.put("teacher",matcher1.group(i+3));
                    jsonObject.put("type",matcher1.group(i+4));

                    String week=matcher1.group(i+6);

                    jsonObject.put("rawWeek",week);
                    System.out.println(week);
                    String day=TimeUtil.getDay(week);


                }
            }


        }

        return "0;0";
    }*/
}
