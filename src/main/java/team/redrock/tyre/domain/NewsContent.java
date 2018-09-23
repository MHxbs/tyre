package team.redrock.tyre.domain;

import lombok.Data;

import java.util.List;


public class NewsContent {

    String title;
    String pubTime;
    String teaName;
    int readCount;
    String content;
    List<Url> urlData;

    public String getPubTime() {
        return pubTime;
    }

    public void setPubTime(String pubTime) {
        this.pubTime = pubTime;
    }

    public String getTeaName() {
        return teaName;
    }

    public void setTeaName(String teaName) {
        this.teaName = teaName;
    }

    public int getReadCount() {
        return readCount;
    }

    public void setReadCount(int readCount) {
        this.readCount = readCount;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Url> getUrlData() {
        return urlData;
    }

    public void setUrlData(List<Url> urlData) {
        this.urlData = urlData;
    }





    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
