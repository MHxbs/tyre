package team.redrock.tyre.domain;

/**
 * 补考信息的bean
 */
public class ReExamInfo {
    private String student;
    private String course;
    private String classroom;
    private String seat;
    private String date;
    private String time;
    private int  week;
    private int weeday;

    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public int getWeeday() {
        return weeday;
    }

    public void setWeeday(int weeday) {
        this.weeday = weeday;
    }
}
