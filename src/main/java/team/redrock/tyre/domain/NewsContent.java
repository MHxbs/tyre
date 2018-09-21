package team.redrock.tyre.domain;

import lombok.Data;

import java.util.List;


public class NewsContent {
    Long fileId;
    String title;
    List<String> content;
//    List<String> url;


    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

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
