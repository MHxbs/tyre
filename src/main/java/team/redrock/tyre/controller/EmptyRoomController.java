package team.redrock.tyre.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import team.redrock.tyre.exception.StuidValidException;
import team.redrock.tyre.service.EmptyRoomService;
import team.redrock.tyre.util.NormalUtils;
import team.redrock.tyre.util.response.EmptyResponse;

@Controller
public class EmptyRoomController {


    @Autowired
    private EmptyRoomService emptyRoomService;
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
}
