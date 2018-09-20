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
import team.redrock.tyre.exception.StuidValidException;
import team.redrock.tyre.mapper.KebiaoResultMapper;
import team.redrock.tyre.service.KebiaoService;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 个人课表
 */

@RestController
public class IndexController {


    @Autowired
    private KebiaoService kebiaoService;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Autowired
    private KebiaoResultMapper kebiaoResultMapper;


    /**
     *
     * @param stu_num
     * @param forceFetch 为true则强制爬一次，再更新mysql，并更新缓存
     * @return
     * @throws IOException
     * @throws StuidValidException
     */
    @PostMapping(value = "/kebiao")
    public KebiaoResult getKebiao(@RequestParam(name = "stu_num",required = false,defaultValue = "-1") String stu_num,
                                  @RequestParam(name = "forceFetch",required = false,defaultValue = "false") String  forceFetch ) throws IOException, StuidValidException {

        // 如果没传
        if (stu_num.equals("-1")){
            throw new StuidValidException(-1,"stuNum not allowed");
    }
        // 如果学号包含标点符号
        if (check(stu_num)){

            throw new StuidValidException(-1,"stuNum not allowed");
        }


        KebiaoResult kebiaoResult=new KebiaoResult();

        // 判断缓存中是否有
        if ( redisTemplate.opsForValue().get(stu_num)!=null){

                // 如果forceFetch为true，强行爬数据
                if (forceFetch.equals("true")){


                // 得到课表
                kebiaoResult=kebiaoService.getKebiao(stu_num);
                String dataStr= (String) kebiaoResult.getData();
                // 如果data为空就不存入数据库
                if (!kebiaoResult.getData().equals("[]")) {
                    kebiaoResultMapper.updateData(dataStr, kebiaoResult.getStuNum());
                }
                // 因为存mysql时，存的String，所以需要json反序列化
                Gson gson=new Gson();
                List<CourseInfo> courseInfos=gson.fromJson(dataStr,new TypeToken<List<CourseInfo>>(){}.getType());
                kebiaoResult.setData(courseInfos);

            }else {// foecrFetch为false
                    kebiaoResult=(KebiaoResult) redisTemplate.opsForValue().get(stu_num);
                }

        }else {

            // 没有缓存，通过mysql
            // mysql中有数据，从数据库中取出，反序列化就行
            if (kebiaoResultMapper.selectOnrByStuNum(stu_num)!=null) {
                kebiaoResult=kebiaoResultMapper.selectOnrByStuNum(stu_num);
                String dataStr= (String) kebiaoResult.getData();
                Gson gson=new Gson();
                List<CourseInfo> courseInfos=gson.fromJson(dataStr,new TypeToken<List<CourseInfo>>(){}.getType());
                kebiaoResult.setData(courseInfos);


            }else {
                // 数据库中没有通过爬

                kebiaoResult=kebiaoService.getKebiao(stu_num);
                String dataStr= (String) kebiaoResult.getData();
                if (!kebiaoResult.getData().equals("[]")) {
                    kebiaoResultMapper.insertOne(kebiaoResult);
                }
                Gson gson=new Gson();
                List<CourseInfo> courseInfos=gson.fromJson(dataStr,new TypeToken<List<CourseInfo>>(){}.getType());
                kebiaoResult.setData(courseInfos);

            }
        }

        // 如果data不为空，增加七天的缓存
        List<CourseInfo> list= (List) kebiaoResult.getData();
        System.out.println(kebiaoResult.getData());
        if (list.size()!=0) {
            kebiaoResult.setCachedTimestamp(System.currentTimeMillis());
            kebiaoResult.setOutOfDateTimestamp(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7);

            redisTemplate.opsForValue().set(stu_num, kebiaoResult);
            redisTemplate.expire(stu_num, 7, TimeUnit.DAYS);
        }

        return kebiaoResult;
    }

    /**
     * 检查stuNum是否包含标点符号
     * @param s
     * @return
     */
    public boolean check(String s) {
        boolean b = false;

        String tmp = s;
        tmp = tmp.replaceAll("\\p{P}", "");
        if (s.length() != tmp.length()) {
            b = true;
        }

        return b;
    }


}
