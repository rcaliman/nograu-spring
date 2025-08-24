package nograu.site.bikefit.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeControllerWeb {


    @GetMapping
    public String home(ModelMap model) {
        return "redirect:/bikefit";
    }
}
