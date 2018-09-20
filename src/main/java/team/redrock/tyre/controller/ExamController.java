package team.redrock.tyre.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import team.redrock.tyre.domain.ExamResult;
import team.redrock.tyre.exception.StuidValidException;
import team.redrock.tyre.service.ExamService;

import java.util.concurrent.TimeUnit;

/**
 * 考试和补考的两个接口
 */
@RestController
public class ExamController {

    @Autowired
    private ExamService examService;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    /**
     * 考试信息的接口
     * @param stuNum
     * @return
     * @throws StuidValidException
     */
    @PostMapping(value = "/examSchedule")
    public ExamResult examSchedule(@RequestParam(name = "stuNum",required = false,defaultValue = "-1") String stuNum) throws StuidValidException {

        if (stuNum.equals("-1")){
            throw new StuidValidException(415,"check failed");
        }
        // 存redis是用学号+exam
        String redisStuNum=stuNum+"exam";

        if (redisTemplate.opsForValue().get(redisStuNum)!=null){
            return (ExamResult) redisTemplate.opsForValue().get(redisStuNum);
        }
        else {
            ExamResult examResult = examService.getExamResult(stuNum);
            redisTemplate.opsForValue().set(redisStuNum, examResult);
            redisTemplate.expire(redisStuNum,10, TimeUnit.MINUTES);
            return examResult;
        }

    }

    /**
     * 补考的接口
     * @param stuNum
     * @return
     * @throws StuidValidException
     */
    @PostMapping(value = "/examReexam")
    public ExamResult reexam(@RequestParam(name = "stuNum",required = false,defaultValue = "-1") String stuNum) throws StuidValidException {

        if (stuNum.equals("-1")){
            throw new StuidValidException(415,"check failed");
        }
        String redisStuNum=stuNum+"Reexam";
        if (redisTemplate.opsForValue().get(redisStuNum)!=null){
            System.out.println("redis111");
            return (ExamResult) redisTemplate.opsForValue().get(redisStuNum);
        }
        else {
            ExamResult examResult = examService.getReexam(stuNum);
            redisTemplate.opsForValue().set(redisStuNum, examResult);
            redisTemplate.expire(redisStuNum,10, TimeUnit.MINUTES);
            return examResult;
        }

    }
}
