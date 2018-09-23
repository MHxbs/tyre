package team.redrock.tyre.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import team.redrock.tyre.exception.StuidValidException;
import team.redrock.tyre.service.GradeService;
import team.redrock.tyre.service.VerifyService;
import team.redrock.tyre.domain.response.GradeResponse;


@Controller
public class GradeController {

    @Autowired
    private GradeService gradeService;


    @Autowired
    private VerifyService verifyService;



    @ResponseBody
    @PostMapping("/grade")
    public GradeResponse getGrade(String stuNum, String idNum, @RequestParam(defaultValue = "false") String ForceFetch)throws StuidValidException {
            GradeResponse response = new GradeResponse();
            response = verifyService.getGrade(stuNum,idNum);

            if(ForceFetch.equals("true")){
                gradeService.deleteGradeCache();
            }
            if(response.getStatus() == 415 ){
                throw new StuidValidException(451,"check failed");
            }
        return response;
    }








}
