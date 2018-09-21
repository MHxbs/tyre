package team.redrock.tyre.util.analyzer;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import team.redrock.tyre.domain.NewsContent;
import team.redrock.tyre.util.response.NewsContentResponse;

import java.util.ArrayList;
import java.util.List;

@Component
public class NewsContentAnalyzer {

    public NewsContent getNewsContent(Document document, NewsContentResponse newsContentResponse){
        NewsContent data = new NewsContent();
        List<String> urls = new ArrayList<>();
        List<String> strs = new ArrayList<>();
        Element element = document.body().getElementById("mainPanel");

        Element div = element.getElementsByTag("div").get(0);
        String title  = div.getElementsByTag("h3").text();
        data.setTitle(title);
//        System.out.println(div);
        String content = div.getElementsByTag("p").get(1).text();
        if (content.equals("")){
            Elements contents = div.getElementsByTag("span");
            contents.forEach(str->{
                strs.add(str.text());
            });
        }else {
            strs.add(content);
        }

//        System.out.println("--------------------content----------------------\n"+content);
        data.setContent(strs);
        ;
//        Elements ul = div.getElementsByTag("ul").get(0).children();
//        ul.forEach(li ->{
//            String url = li.getElementsByTag("a").attr("href");
//            urls.add(url);
//        });
//        data.setUrl(urls);
        if(title==null){
            newsContentResponse.setStatus(-20);
        }

        return data;

    }
}
