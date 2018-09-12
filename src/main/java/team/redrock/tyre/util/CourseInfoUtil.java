package team.redrock.tyre.util;

import team.redrock.tyre.entity.CourseInfo;

public class CourseInfoUtil {

    public static CourseInfo exchange(CourseInfo courseInfo2){
        CourseInfo courseInfo1=new CourseInfo();
        courseInfo1.setCourse(courseInfo2.getCourse());
        courseInfo1.setClassroom(courseInfo2.getClassroom());
        courseInfo1.setTeacher(courseInfo2.getTeacher());
        courseInfo1.setWeekEnd(courseInfo2.getWeekEnd());
        courseInfo1.setClassroom(courseInfo2.getClassroom());
        courseInfo1.setWeekBegin(courseInfo2.getWeekBegin());
        courseInfo1.setRawWeek(courseInfo2.getRawWeek());
        courseInfo1.setLesson(courseInfo2.getLesson());
        courseInfo1.setDay(courseInfo1.getDay());
        courseInfo1.setType(courseInfo2.getType());
        courseInfo1.setCourse_num(courseInfo2.getCourse_num());
        courseInfo1.setBegin_lesson(courseInfo2.getBegin_lesson());
        courseInfo1.setHash_day(courseInfo2.getHash_day());
        courseInfo1.setHash_lesson(courseInfo2.getHash_lesson());
        courseInfo1.setPeriod(courseInfo2.getPeriod());
        courseInfo1.setWeekModel(courseInfo2.getWeekModel());

        return courseInfo1;
    }
}
