package readinglist;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
public class HomeController {

    @RequestMapping(value = "/", method = GET)
    public String home(){
        return "home";
    }

//    @RequestMapping(value="/activiti",method = RequestMethod.GET)
//    public String newModel(HttpServletResponse resp) throws IOException {
//        return "newActivitiModel";
//    }
}
