package team.redrock.tyre.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import team.redrock.tyre.entity.CourseInfo;
import team.redrock.tyre.entity.KebiaoResult;
import team.redrock.tyre.domain.KebiaoTime;
import team.redrock.tyre.util.KebiaoUtil;
import team.redrock.tyre.util.SendUrl;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
public class KebiaoService {

    @Value("${jwzx.courseInfo}")
    private String courseInfoURL;

    public KebiaoResult getKebiao(String stu_num)  {

        KebiaoResult kebiaoResult=new KebiaoResult();

        String param="xh="+stu_num;
        String data= null;
        try {
            data = SendUrl.getDataByGet(courseInfoURL,param);
            log.info("请求教务在线courseInfoUrl出现问题");
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<CourseInfo> courseInfoList=KebiaoUtil.getTimeTableFromJWZX(data);
        KebiaoTime kebiaoTime=KebiaoUtil.getKebiaoTime(data);

        Gson gson=new Gson();
        String lis=gson.toJson(courseInfoList,new TypeToken<List<CourseInfo>>(){}.getRawType());

        kebiaoResult.setData(lis);

        kebiaoResult.setNowWeek(kebiaoTime.getNowWeek());
        kebiaoResult.setStuNum(stu_num);
        kebiaoResult.setTerm(kebiaoTime.getTerm());
        kebiaoResult.setSuccess(true);
        kebiaoResult.setVersion("1.0.1");
        kebiaoResult.setStatus(200);


       return kebiaoResult;
    }
}
