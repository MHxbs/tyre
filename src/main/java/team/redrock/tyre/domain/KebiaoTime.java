package team.redrock.tyre.domain;

/**
 * 课表中处理相关时间的bean
 */
public class KebiaoTime {
    private String term;
    private String stuNum;
    private int nowWeek;

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getStuNum() {
        return stuNum;
    }

    public void setStuNum(String stuNum) {
        this.stuNum = stuNum;
    }

    public int getNowWeek() {
        return nowWeek;
    }

    public void setNowWeek(int nowWeek) {
        this.nowWeek = nowWeek;
    }
}
