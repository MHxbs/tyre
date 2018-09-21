package team.redrock.tyre.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import team.redrock.tyre.service.NewsService;
import team.redrock.tyre.util.response.NewslistResponse;


@Controller
public class NewsListController {
    @Autowired
    private NewsService newsService;

    @ResponseBody
    @PostMapping("/jwNewsList")
    public NewslistResponse getList(@RequestParam(defaultValue = "0")int page, @RequestParam(defaultValue = "false") String ForceFetch){
        NewslistResponse response = new NewslistResponse();

        response = newsService.getListFromDB(page);
        if(ForceFetch.equals("true")){
            newsService.deleteNewslist();
        }


        return response;
    }
}
