package team.redrock.tyre.util.analyzer;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import team.redrock.tyre.entity.GradeInfo;
import team.redrock.tyre.util.response.GradeResponse;

import java.util.ArrayList;
import java.util.List;

@Component
public class GradeAnalyzer {
    /**
     * 分析数据，爬取成绩信息的工具类
     * @param document
     * @param response
     * @return
     */
    public List<GradeInfo> getGrade(Document document, GradeResponse response){
        List<GradeInfo> results = new ArrayList<>();

        if(ObjectUtils.isEmpty(document)){
            return results;
        }
        if(document.body().text().equals("未查到匹配学生信息..")){
    response.setStatus(415);

    return results;
        }



        Element pTable = document.body().getElementsByClass("pTable").get(0);
;
        Elements trs = pTable.getElementsByTag("tbody").get(0).children();

        trs.forEach(tr ->{

            if(!tr.children().isEmpty()){
                Element element = tr.getElementsByTag("td").get(0);

            if(!element.text().equals( "课程类型"))
            {
                GradeInfo gradeInfo = new GradeInfo();
                gradeInfo.setProperty(tr.getElementsByTag("td").get(0).text());
                String term = tr.getElementsByTag("td").get(1).text();

                response.setTerm(term);
                gradeInfo.setTerm(term);
                gradeInfo.setStudent(tr.getElementsByTag("td").get(2).text());
                gradeInfo.setCourse(tr.getElementsByTag("td").get(5).text());
                gradeInfo.setStatus(tr.getElementsByTag("td").get(6).text());
                gradeInfo.setGrade(tr.getElementsByTag("td").get(7).text());
                results.add(gradeInfo);
              }
            }

        });

        return results;

    }
}
