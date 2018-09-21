package team.redrock.tyre.domain;

import lombok.Data;

import java.util.List;


public class NewsInfo {
    int totalPage;
    List<NewsData> data;

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<NewsData> getData() {
        return data;
    }

    public void setData(List<NewsData> data) {
        this.data = data;
    }
}
