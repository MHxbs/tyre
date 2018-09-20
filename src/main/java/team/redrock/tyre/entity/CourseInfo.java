package team.redrock.tyre.entity;

import com.google.gson.JsonObject;

import java.util.List;

/**
 * 课程信息的bean
 */
public class CourseInfo {

    private int hash_day;
    private int hash_lesson;
    private int begin_lesson;
    private String day;
    private String lesson;
    private String course;
    private String course_num;
    private String teacher;
    private String classroom;
    private String rawWeek;
    private String weekModel;
    private int weekBegin;
    private int weekEnd;
    private List<Integer> week;
    private String type;
    private int period;


    public int getHash_day() {
        return hash_day;
    }

    public void setHash_day(int hash_day) {
        this.hash_day = hash_day;
    }

    public int getHash_lesson() {
        return hash_lesson;
    }

    public void setHash_lesson(int hash_lesson) {
        this.hash_lesson = hash_lesson;
    }

    public int getBegin_lesson() {
        return begin_lesson;
    }

    public void setBegin_lesson(int begin_lesson) {
        this.begin_lesson = begin_lesson;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getLesson() {
        return lesson;
    }

    public void setLesson(String lesson) {
        this.lesson = lesson;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getCourse_num() {
        return course_num;
    }

    public void setCourse_num(String course_num) {
        this.course_num = course_num;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public String getRawWeek() {
        return rawWeek;
    }

    public void setRawWeek(String rawWeek) {
        this.rawWeek = rawWeek;
    }

    public String getWeekModel() {
        return weekModel;
    }

    public void setWeekModel(String weekModel) {
        this.weekModel = weekModel;
    }

    public int getWeekBegin() {
        return weekBegin;
    }

    public void setWeekBegin(int weekBegin) {
        this.weekBegin = weekBegin;
    }

    public int getWeekEnd() {
        return weekEnd;
    }

    public void setWeekEnd(int weekEnd) {
        this.weekEnd = weekEnd;
    }

    public List<Integer> getWeek() {
        return week;
    }

    public void setWeek(List<Integer> week) {
        this.week = week;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }
}
