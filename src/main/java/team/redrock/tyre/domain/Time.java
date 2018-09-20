package team.redrock.tyre.domain;

import java.util.List;

/**
 * 个人课表中相关时间的bean
 */
public class Time {
    private String day;
    private String lesson;
    private List<Integer> week;
    private int hash_day;
    private int hash_lesson;
    private int period;

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

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

    public List<Integer> getWeek() {
        return week;
    }

    public void setWeek(List<Integer> week) {
        this.week = week;
    }
}
