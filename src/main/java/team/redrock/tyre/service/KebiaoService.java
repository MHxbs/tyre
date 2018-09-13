package team.redrock.tyre.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Service;
import team.redrock.tyre.entity.CourseInfo;
import team.redrock.tyre.entity.KebiaoResult;
import team.redrock.tyre.domain.KebiaoTime;
import team.redrock.tyre.util.KebiaoUtil;
import team.redrock.tyre.util.SendUrl;

import java.io.IOException;
import java.util.List;

@Service
public class KebiaoService {

    public KebiaoResult getKebiao(String stu_num) throws IOException {

        KebiaoResult kebiaoResult=new KebiaoResult();

        String param="xh="+stu_num;
        String data= SendUrl.getDataByGet("http://jwzx.cqupt.edu.cn/kebiao/kb_stu.php",param);

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