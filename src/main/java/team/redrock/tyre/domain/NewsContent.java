package team.redrock.tyre.domain;

import lombok.Data;

import java.util.List;


public class NewsContent {

    String title;
    String content;

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

    List<Url> urlData;



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
