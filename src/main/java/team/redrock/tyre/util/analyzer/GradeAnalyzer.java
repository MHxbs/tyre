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

    public List<GradeInfo> getGrade(Document document, GradeResponse response){
        List<GradeInfo> results = new ArrayList<>();

        if(ObjectUtils.isEmpty(document)){
            return results;
        }
        if(document.body().text().equals("未查到匹配学生信息..")){
    response.setStatus(415);

    return results;
        }


//        System.out.println(document);
        Element pTable = document.body().getElementsByClass("pTable").get(0);
//        System.out.println(pTable);
        Elements trs = pTable.getElementsByTag("tbody").get(0).children();
//        System.out.println(trs);
        trs.forEach(tr ->{
//            System.out.println("tr:"+tr);
            if(!tr.children().isEmpty()){
                Element element = tr.getElementsByTag("td").get(0);
//                System.out.println(element);
            if(!element.text().equals( "课程类型"))
            {
                GradeInfo gradeInfo = new GradeInfo();
                gradeInfo.setProperty(tr.getElementsByTag("td").get(0).text());
                String term = tr.getElementsByTag("td").get(1).text();
//                System.out.println("trem:"+term);
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
