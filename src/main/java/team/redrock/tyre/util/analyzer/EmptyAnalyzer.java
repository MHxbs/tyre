package team.redrock.tyre.util.analyzer;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import team.redrock.tyre.domain.response.EmptyResponse;


import java.util.ArrayList;
import java.util.List;

@Component
public class EmptyAnalyzer {

    /**
     * 空教室分析数据，爬取信息的工具类
     * @param document
     * @param response
     * @return
     */
    public List<String> getEmptyRoomt(Document document, EmptyResponse response){
        List<String> rooms = new ArrayList<>();
        Elements table = document.body().getElementsByTag("tbody").get(0).children();
        table.forEach(tr->{
            tr.children().forEach(td->{
                String room  = td.getElementsByTag("input").attr("value");

                rooms.add(room);

            });
        });

        return rooms;
    }
}
