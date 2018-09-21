package team.redrock.tyre.domain;

import lombok.Data;

import java.util.List;


public class NewsContent {

    String title;
    List<String> content;
//    List<String> url;



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getContent() {
        return content;
    }

    public void setContent(List<String> content) {
        this.content = content;
    }
}
