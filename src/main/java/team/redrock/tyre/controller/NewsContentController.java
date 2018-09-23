package team.redrock.tyre.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import team.redrock.tyre.exception.StuidValidException;
import team.redrock.tyre.service.NewsService;
import team.redrock.tyre.domain.response.NewsContentResponse;

@Controller
public class NewsContentController {

    @Autowired
    private NewsService newsService;

    @ResponseBody
    @PostMapping("/jwNewsContent")
    public NewsContentResponse getcontent(Long id, @RequestParam(defaultValue = "false") String ForceFetch)throws StuidValidException {
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
}
