package polaromatic.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.ModelAndView

@Controller
class PolaromaticController {

    @RequestMapping("/")
    def home() {
        new ModelAndView('home')
    }
}