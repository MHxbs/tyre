package team.redrock.tyre.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import team.redrock.tyre.exception.StuidValidException;
import team.redrock.tyre.service.EmptyRoomService;
import team.redrock.tyre.service.GradeService;
import team.redrock.tyre.service.NewsService;
import team.redrock.tyre.service.VerifyService;
import team.redrock.tyre.util.NormalUtils;
import team.redrock.tyre.util.response.EmptyResponse;
import team.redrock.tyre.util.response.GradeResponse;
import team.redrock.tyre.util.response.NewsContentResponse;
import team.redrock.tyre.util.response.NewslistResponse;


@Controller
public class SpierController {

    @Autowired
    private GradeService gradeService;

    @Autowired
    private EmptyRoomService emptyRoomService;
    @Autowired
    private NewsService newsService;
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


    @ResponseBody
    @PostMapping("/roomEmpty")
    public EmptyResponse getEmpty(String weekdayNum, String sectionNum, String buildNum, String week, @RequestParam(defaultValue = "false") String ForceFetch)throws StuidValidException {
        NormalUtils normalUtils = new NormalUtils();
            EmptyResponse result = new EmptyResponse();
            EmptyResponse response = new EmptyResponse();
//            response = emptyRoomService.selectEmpty(Integer.parseInt(weekdayNum),Integer.parseInt(sectionNum),Integer.parseInt(week));
        String section = normalUtils.getSection(sectionNum,response);

        if(!buildNum.equals("null")){
             result = emptyRoomService.getRoom(weekdayNum,section,week,buildNum);

        }
        if(ForceFetch.equals("true")){
            emptyRoomService.deleteCahce();
        }
        if(result.getStatus() == -1){
            throw new StuidValidException(451,"inner error");
        }
        return result;
    }

    @ResponseBody
    @PostMapping("/NewsContent")
    public NewsContentResponse getcontent(Long id, @RequestParam(defaultValue = "false") String ForceFetch)throws StuidValidException{
    NewsContentResponse response = new NewsContentResponse();

    response = newsService.getContentFromDb(id);

    if(ForceFetch.equals("true")){
        newsService.deleteNewslist();
    }
    if(id == null){
        response.setStatus(-20);
    }
        if(response.getStatus() == -20){
            throw new StuidValidException(451,"Invaild param: id");
        }
    return response;
    }

    @ResponseBody
    @PostMapping("/NewsList")
    public NewslistResponse getList(@RequestParam(defaultValue = "0")int page, @RequestParam(defaultValue = "false") String ForceFetch){
        NewslistResponse response = new NewslistResponse();

            response = newsService.getListFromDB(page);
        if(ForceFetch.equals("true")){
            newsService.deleteNewslist();
        }


        return response;
    }


}
