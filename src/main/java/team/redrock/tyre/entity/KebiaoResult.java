package team.redrock.tyre.entity;

import java.util.List;

/**
 * 课表的result
 * @param <T>
 */
public class KebiaoResult<T> {

    private int status;
    private boolean success;
    private String version;
    private String term;
    private String stuNum;
    private T data;
    private int nowWeek;
    private long cachedTimestamp;
    private long outOfDateTimestamp;

    public long getCachedTimestamp() {
        return cachedTimestamp;
    }

    public void setCachedTimestamp(long cachedTimestamp) {
        this.cachedTimestamp = cachedTimestamp;
    }

    public long getOutOfDateTimestamp() {
        return outOfDateTimestamp;
    }

    public void setOutOfDateTimestamp(long outOfDateTimestamp) {
        this.outOfDateTimestamp = outOfDateTimestamp;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

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
