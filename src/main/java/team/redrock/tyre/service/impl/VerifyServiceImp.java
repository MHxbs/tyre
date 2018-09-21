package team.redrock.tyre.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import team.redrock.tyre.service.GradeService;
import team.redrock.tyre.service.VerifyService;
import team.redrock.tyre.util.VerifyUtil;
import team.redrock.tyre.util.response.GradeResponse;

@Service
public class VerifyServiceImp implements VerifyService {
    @Value("${jwzx.verify}")
    private String verUrl;

    VerifyUtil verifyUtil = new VerifyUtil();
    @Autowired
    private GradeService gradeService;

    public GradeResponse getGrade(String stu_name, String id_num){
//        List<GradeInfo> result = new ArrayList<>();
        GradeResponse response = new GradeResponse();
        if (verifyUtil.verifyIdentity(verUrl,stu_name,id_num)){
            return gradeService.getGradeInfoFromDB(stu_name,id_num);
        }else{
            response.setStatus(415);
            return response;
        }
    }

}
