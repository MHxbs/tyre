package team.redrock.tyre.util.analyzer;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import team.redrock.tyre.domain.NewsContent;
import team.redrock.tyre.domain.NewsData;
import team.redrock.tyre.domain.Url;
import team.redrock.tyre.util.NewsUtils;
import team.redrock.tyre.util.response.NewsContentResponse;

import java.util.ArrayList;
import java.util.List;

@Component
public class NewsContentAnalyzer {


    /**
     * 获取新闻内容
     * @param document
     * @param newsContentResponse
     * @return
     */

    public NewsContent getNewsContent(Document document, NewsContentResponse newsContentResponse){
        NewsContent data = new NewsContent();
        List<Url> urlData = new ArrayList<>();
        List<String> strs = new ArrayList<>();
        String context =new String();

        NewsUtils newsUtils = new NewsUtils();

        Element element = document.body().getElementById("mainPanel");

        Element div = element.getElementsByTag("div").get(0);
        String title  = div.getElementsByTag("h3").text();
        data.setTitle(title);

        Elements content = div.getElementsByTag("p");

        //获取页头（内容的时间、作者和阅读数）
        String pagehead = content.get(0).text().replaceAll(" {2,}", " ");
        System.out.println(pagehead);
        String[] splits = pagehead.split(" ");
        List<String> infos = new ArrayList<>();

        for (String s:splits) {
            String[]ss =  s.split("：");
            infos.add(ss[1]);
        }
        String datetime = newsUtils.getDate(infos.get(0));
        data.setPubTime(datetime);
        data.setTeaName(infos.get(1));
        data.setReadCount(Integer.parseInt(infos.get(2)));
        System.out.println("datetime"+datetime);

        //获取新闻正文
        content.forEach(con->{
            strs.add(con.toString());
        });
        strs.remove(0);
        strs.remove(strs.size()-1);
        for (String c: strs) {
            context += c+"\n";
        }

        String c  = context.replace("<br>","\n")
                .replaceAll("<[^>]*>","")
                .replace("&nbsp;"," ");

        data.setContent(c);

        Elements ul = div.getElementsByTag("ul").get(0).children();
        ul.forEach(li ->{
            Url url = new Url();
            url.setUrl(li.getElementsByTag("a").attr("href"));
            url.setUrlname(li.getElementsByTag("a").text());
            urlData.add(url);
        });
        data.setUrlData(urlData);
        if(title==null){
            newsContentResponse.setStatus(-20);
        }
        return data;

    }
}
