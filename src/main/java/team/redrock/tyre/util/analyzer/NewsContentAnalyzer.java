package team.redrock.tyre.util.analyzer;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import team.redrock.tyre.domain.NewsContent;
import team.redrock.tyre.domain.Url;
import team.redrock.tyre.util.NewsUtils;
import team.redrock.tyre.domain.response.NewsContentResponse;
import team.redrock.tyre.util.NormalUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class NewsContentAnalyzer {

    @Value("${saveFilePath}")
    private String savePath;

    /**
     * 获取新闻内容
     * @param document
     * @param newsContentResponse
     * @return
     */

    public NewsContent getNewsContent(Document document, NewsContentResponse newsContentResponse){
        NewsContent data = new NewsContent();
        NormalUtils normalUtils = new NormalUtils();
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

        String[] splits = pagehead.split(" ");
        List<String> infos = new ArrayList<>();

        for (String s:splits) {
            String[]ss =  s.split("：");
            if(ss.length==2){                                      //避免出现无上传者的情况
                infos.add(ss[1]);
            }else{
                infos.add("");
            }
        }
        String datetime = newsUtils.getDate(infos.get(0));
        data.setPubTime(datetime);
        data.setTeaName(infos.get(1));
        data.setReadCount(Integer.parseInt(infos.get(2)));


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

        //获取下载链接并下载文件到静态文件夹
        final String[] fileUrl = {new String()};
        Elements ul = div.getElementsByTag("ul").get(0).children();
        ul.forEach(li ->{
            if(li.getElementsByTag("a").attr("target").equals("_blank")){
                Url url = new Url();
                String uri = li.getElementsByTag("a").attr("href");
                String[] urlNameSpilts = li.getElementsByTag("a").text().split("\\(");        //删除文件名后自带的下载数
                String urlName = urlNameSpilts[0];
//                System.out.println("urlName:"+urlName);
                synchronized (this){
                    fileUrl[0] = normalUtils.traverseFolder(urlName,savePath);
                    if(fileUrl[0] !=null){
                       log.info("文件已存在");

                    } else {
                        log.info("调用文件接口");
                        String downLoadUrl = "http://jwzx.cqupt.edu.cn/" + uri;
                        try {
                            String suffix = newsUtils.downloadFile(downLoadUrl, savePath, urlName);
                            fileUrl[0] = savePath+"/"+urlName+suffix;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }
                url.setUrl(fileUrl[0]);
                url.setUrlname(urlName);
                urlData.add(url);
            }
        });
        data.setUrlData(urlData);
        if(title==null){
            newsContentResponse.setStatus(-20);
        }
        return data;

    }
}
