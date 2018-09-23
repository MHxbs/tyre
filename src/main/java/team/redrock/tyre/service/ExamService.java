package team.redrock.tyre.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import team.redrock.tyre.domain.ExamInfo;
import team.redrock.tyre.domain.ExamResult;
import team.redrock.tyre.domain.ReExamInfo;
import team.redrock.tyre.util.Exam;
import team.redrock.tyre.util.SendUrl;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class ExamService {

    @Value("${jwzx.examUrl}")
    private String examUrl;

    @Value("${jwzx.reexamUrl}")
    private String reexamUrl;


    public ExamResult getExamResult(String stuNum){

        examUrl+=stuNum;
        String data="";
        try {
            data= SendUrl.getDataByGet(examUrl,"");
            log.info("请求教务在线examUrl出现问题");
        } catch (IOException e) {
            e.printStackTrace();
        }


        ExamResult examResult=new ExamResult();

        List<ExamInfo> examInfoList=Exam.getExam(data);

        examResult.setStatus(200);
        examResult.setInfo("success");
        examResult.setVersion("1.0.1");
        examResult.setStuNum(stuNum);
        examResult.setData(examInfoList);

        Pattern pattern1=Pattern.compile("<li>〉〉(.*?) 考试安排查询 〉〉(.*?) </li>");
        Matcher matcher1=pattern1.matcher(data);
        if (matcher1.find()){
            examResult.setTerm(Exam.getTerm(matcher1.group(1)));
        }

        return examResult;

    }


    public ExamResult getReexam(String stuNum){


        reexamUrl+=stuNum;
        String data="";
        try {
            data= SendUrl.getDataByGet(reexamUrl,"");
            log.info("请求教务在线reexamUrl出现问题");
        } catch (IOException e) {
            e.printStackTrace();
        }


        List<ReExamInfo> examInfoList=Exam.getReExam(data,stuNum);
        ExamResult examResult=new ExamResult();
        examResult.setStatus(200);
        examResult.setInfo("success");
        examResult.setVersion("1.0.1");
        examResult.setStuNum(stuNum);
        examResult.setData(examInfoList);


        examUrl+=stuNum;
        String dataTerm="";
        try {
            dataTerm= SendUrl.getDataByGet(examUrl,"");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Pattern pattern1=Pattern.compile("<li>〉〉(.*?) 考试安排查询 〉〉(.*?) </li>");
        Matcher matcher1=pattern1.matcher(dataTerm);
        if (matcher1.find()){
            examResult.setTerm(Exam.getTerm(matcher1.group(1)));
        }
        return examResult;

    }
}
