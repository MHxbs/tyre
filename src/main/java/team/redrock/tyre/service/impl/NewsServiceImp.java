package team.redrock.tyre.service.impl;


import com.alibaba.fastjson.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import team.redrock.tyre.domain.NewsContent;
import team.redrock.tyre.domain.NewsData;
import team.redrock.tyre.domain.NewsInfo;
import team.redrock.tyre.service.NewsService;
import team.redrock.tyre.util.NewsUtils;
import team.redrock.tyre.util.analyzer.NewsContentAnalyzer;
import team.redrock.tyre.util.response.NewsContentResponse;
import team.redrock.tyre.util.response.NewslistResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Service
@CacheConfig(cacheNames = "newslist")
public class NewsServiceImp implements NewsService {
    @Value("${jwzx.newslist}")
    private String nlUrl;
    @Value("${jwzx.newscontent}")
    private String ncUrl;
    @Autowired
    private NewsContentAnalyzer newsContentAnalyzer;



    @Override
    public NewslistResponse getListFromIp(int page) {
        int size = 10;
        if(page == 0 ){
            page = 1;
        }
    String url = nlUrl+"pageNo="+page+"&pageSize="+size+"&searchKey=";
        NewsUtils newsUtils = new NewsUtils();
        NewslistResponse response = new NewslistResponse();
        JSONObject object = newsUtils.getNewsList(url);

        NewsInfo newsInfo = JSONObject.toJavaObject(object,NewsInfo.class);

        List<NewsData> datalist = newsInfo.getData();
        for (NewsData newsData: datalist) {
            newsData.setPubTime( newsUtils.getDate(newsData.getPubTime()));
        }

        response.setData(newsInfo.getData());
        response.setStatus(200);
        response.setInfo("success");
        response.setPage(page);

        return response;

    }

    @Cacheable(sync = true,value = "newsUser",key = "#page")
    @Override
    public NewslistResponse getListFromDB(int page){
        List<NewsData> data = new ArrayList<>();
        NewsInfo newsInfo = new NewsInfo();
        NewslistResponse response = new NewslistResponse();

         return getListFromIp(page);

    }



    @Override
    public NewsContentResponse getContentFromIp(Long id) {
        String url=ncUrl+"id="+id;
        Connection con = Jsoup.connect(url).maxBodySize(0);
        NewsContentResponse response = new NewsContentResponse();
        NewsContent results = new NewsContent();

        if(id == null)
        response.setStatus(-20);
        try {
             results = newsContentAnalyzer.getNewsContent(con.get(),response);

        } catch (IOException e) {
            e.printStackTrace();
        }
        if(response.getStatus()!=-20) {
            response.setData(results);
            response.setId(id);
            response.setStatus(200);
            response.setInfo("success");}
            return response;
    }

    @Override
    @Cacheable(sync = true,value = "newsUser",key = "#id")
    public NewsContentResponse getContentFromDb(Long id){
        NewsContent content = new NewsContent();
        NewsContentResponse response = new NewsContentResponse();

            return getContentFromIp(id);

    }
    @CacheEvict(value = "newsUser",allEntries = true)
    public void deleteNewslist(){
//        System.out.println("清除缓存");
    }

}
