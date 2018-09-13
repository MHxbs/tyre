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
import java.util.concurrent.TimeUnit;

@RestController
public class IndexController {


    @Autowired
    private KebiaoService kebiaoService;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Autowired
    private KebiaoResultMapper courseInfoMapper;



    @PostMapping(value = "/kebiao")
    public KebiaoResult getKebiao(@RequestParam(name = "stu_num",required = false,defaultValue = "0") String stu_num,
                                  @RequestParam(name = "forceFetch",required = false,defaultValue = "false") String  forceFetch ) throws IOException {


        // redis


        if ( redisTemplate.opsForValue().get(stu_num)!=null){

            KebiaoResult kebiaoResult=(KebiaoResult) redisTemplate.opsForValue().get(stu_num);
            //Long begin =System.currentTimeMillis();
            System.out.println("通过缓存："+stu_num);
           // Long end=System.currentTimeMillis();
            //System.out.println("开始时间："+begin+"stu_num:"+stu_num+"节数时间："+end+"时间"+(end-begin));

            return kebiaoResult;
        }else {

            // mysql 索引

            if (courseInfoMapper.selectOnrByStuNum(stu_num)!=null) {
                KebiaoResult kebiaoResult2=courseInfoMapper.selectOnrByStuNum(stu_num);
                String dataStr= (String) kebiaoResult2.getData();
                Gson gson=new Gson();
                List<CourseInfo> courseInfos=gson.fromJson(dataStr,new TypeToken<List<CourseInfo>>(){}.getType());
                kebiaoResult2.setData(courseInfos);

                redisTemplate.opsForValue().set(stu_num,kebiaoResult2);
                redisTemplate.expire(stu_num,60, TimeUnit.SECONDS);
                System.out.println("从数据库拿："+stu_num);
                return kebiaoResult2;
            }else {
                System.out.println("爬的："+stu_num);
                KebiaoResult kebiaoResult1=kebiaoService.getKebiao(stu_num);
                String dataStr= (String) kebiaoResult1.getData();
                courseInfoMapper.insertOne(kebiaoResult1);
                Gson gson=new Gson();
                List<CourseInfo> courseInfos=gson.fromJson(dataStr,new TypeToken<List<CourseInfo>>(){}.getType());
                kebiaoResult1.setData(courseInfos);

                redisTemplate.opsForValue().set(stu_num,kebiaoResult1);
                redisTemplate.expire(stu_num,60, TimeUnit.SECONDS);
                return kebiaoResult1;
            }


        }




        //return kebiaoService.getKebiao(stu_num);
    }




}
