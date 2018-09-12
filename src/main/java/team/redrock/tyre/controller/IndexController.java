package team.redrock.tyre.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import team.redrock.tyre.entity.CourseInfo;
import team.redrock.tyre.entity.KebiaoResult;
import team.redrock.tyre.mapper.KebiaoResultMapper;
import team.redrock.tyre.service.KebiaoService;

import java.io.IOException;
import java.util.List;

@RestController
public class IndexController {


    @Autowired
    private KebiaoService kebiaoService;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Autowired
    private KebiaoResultMapper courseInfoMapper;



    @PostMapping(value = "/kebiao")
    public KebiaoResult getKebiao(@RequestParam("stu_num") String stu_num,
                                  @RequestParam("forceFetch") String  forceFetch ) throws IOException {
/*
        KebiaoResult kebiaoResult=(KebiaoResult) redisTemplate.opsForValue().get(stu_num);

        if (kebiaoResult!=null){
            //Long begin =System.currentTimeMillis();
            System.out.println("通过缓存："+stu_num);
           // Long end=System.currentTimeMillis();
            //System.out.println("开始时间："+begin+"stu_num:"+stu_num+"节数时间："+end+"时间"+(end-begin));

            return kebiaoResult;
        }else {
            System.out.println("通过爬:"+stu_num);
             kebiaoResult=kebiaoService.getKebiao(stu_num);
            redisTemplate.opsForValue().set(stu_num,kebiaoResult);
            return kebiaoResult;
        }*/


        // mysql 索引
        KebiaoResult kebiaoResult=courseInfoMapper.selectOnrByStuNum(stu_num);
        if (kebiaoResult!=null) {
            String dataStr= (String) kebiaoResult.getData();
            Gson gson=new Gson();
            List<CourseInfo> courseInfos=gson.fromJson(dataStr,new TypeToken<List<CourseInfo>>(){}.getType());
            kebiaoResult.setData(courseInfos);


            System.out.println("从数据库拿："+stu_num);
            return kebiaoResult;
        }else {
            System.out.println("爬的："+stu_num);
            KebiaoResult kebiaoResult1=kebiaoService.getKebiao(stu_num);
            courseInfoMapper.insertOne(kebiaoResult1);


            return kebiaoResult1;
        }

        //return kebiaoService.getKebiao(stu_num);
    }




}
