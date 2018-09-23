package team.redrock.tyre.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import team.redrock.tyre.domain.EmptyRoom;
import team.redrock.tyre.service.EmptyRoomService;
import team.redrock.tyre.util.NormalUtils;
import team.redrock.tyre.util.analyzer.EmptyAnalyzer;
import team.redrock.tyre.util.response.EmptyResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@CacheConfig(cacheNames = "empty")
public class EmptyRoomServiceImp implements EmptyRoomService {
    @Value("${jwzx.emptyroom}")
    private String emptyUrl;

    @Autowired
    private EmptyAnalyzer emptyAnalyzer;
    @Autowired
    RedisTemplate<String,EmptyRoom> emptyRoomRedisTemplate;



    public EmptyResponse getRoom(String weekdayNum, String sectionNum, String week, String buildNum){
        NormalUtils normalUtils = new NormalUtils();
        EmptyResponse emptyResponse = new EmptyResponse();
//        List<String> result = new ArrayList<>();
        EmptyRoom result = new EmptyRoom();
        List<String> str =null;

if(normalUtils.isInteger(week)||normalUtils.isInteger(weekdayNum)||normalUtils.isInteger(week)){
    if(Integer.parseInt(week)>0||Integer.parseInt(week)<8||Integer.parseInt(week)<0||Integer.parseInt(sectionNum)<0||Integer.parseInt(sectionNum)>5) {
        String key = week + "-" + weekdayNum + "-" + sectionNum;
       Boolean isExist =  emptyRoomRedisTemplate.opsForHash().hasKey("EmptyRoom", key);


        if (!isExist) {
            synchronized (this) {

                if (!emptyRoomRedisTemplate.opsForHash().hasKey("EmptyRoom", key)) {
                     str = selectEmpty(weekdayNum, sectionNum, week,emptyResponse);

                } else {
                    result = (EmptyRoom) emptyRoomRedisTemplate.opsForHash().get("EmptyRoom", key);
                    str = result.getData();

                }
            }
        } else {
            result = (EmptyRoom) emptyRoomRedisTemplate.opsForHash().get("EmptyRoom", key);
            str = result.getData();


        }

//        System.out.println("result:"+str);
        List<String> results = normalUtils.selectBuild(buildNum,str);
        emptyResponse.setStatus(200);
        emptyResponse.setInfo("success");
        emptyResponse.setVersion("1.0.0");
        emptyResponse.setBuildNum(buildNum);
        emptyResponse.setWeekdayNum(weekdayNum);
        emptyResponse.setData(results);


    }else{
        emptyResponse.setStatus(-1);
    }

    }else{
    emptyResponse.setStatus(-1);
    }


    return emptyResponse;
}


    @Override
    public List<String> selectEmpty(String weekdayNum,String sectionNum,String week,EmptyResponse emptyResponse){
        EmptyRoom er = new EmptyRoom();
        List<String> results = new ArrayList<>();

        String url = emptyUrl+"zc="+week+"&xq="+weekdayNum+"&sd="+sectionNum;

            Connection con = Jsoup.connect(url);

            String falhname = week+"-"+weekdayNum+"-"+sectionNum;

            try {
                results = emptyAnalyzer.getEmptyRoomt(con.timeout(5000).get(),emptyResponse);
//                System.out.println("从接口得到的result"+results);
                er.setData(results);
            } catch (IOException e) {
                e.printStackTrace();
            }
            emptyRoomRedisTemplate.opsForHash().put("EmptyRoom",falhname, er);
                emptyRoomRedisTemplate.expire("EmptyRoom",1, TimeUnit.HOURS);


        return results;
    }

    public List<String> getRooms(String weekdayNum,String sectionNum,String week,String buildNum){
        if(emptyUrl == null){
            emptyUrl = "http://jwzx.cqupt.edu.cn/jssq/jssqEmptyRoom.php?";
        }
        String url = emptyUrl+"zc="+week+"&xq="+weekdayNum+"&sd="+sectionNum;
//        System.out.println(url);
        Connection con = Jsoup.connect(url);
        EmptyResponse emptyResponse = new EmptyResponse();
        List<String> results = new ArrayList<>();
        String falhname = week+"-"+weekdayNum+"-"+sectionNum;


        try {
            results = emptyAnalyzer.getEmptyRoomt(con.timeout(12000).ignoreHttpErrors(true).get(),emptyResponse);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return results;
    }

    public void deleteCahce(){
        emptyRoomRedisTemplate.delete("EmptyRoom");
        log.info("清除缓存");
    }
}
