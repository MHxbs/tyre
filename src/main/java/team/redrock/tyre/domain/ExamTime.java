package team.redrock.tyre.domain;

/**
 * 考试中处理相关时间的bean
 */
public class ExamTime {

    private String time;
    private String begin_time;
    private String end_time;
    private String lesson_time;

    public String getLesson_time() {
        return lesson_time;
    }

    public void setLesson_time(String lesson_time) {
        this.lesson_time = lesson_time;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getBegin_time() {
        return begin_time;
    }

    public void setBegin_time(String begin_time) {
        this.begin_time = begin_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }
}
