package nograu.site.bikefit.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import nograu.site.bikefit.service.LinkService;

@Controller
@RequestMapping("/links")
public class LinkControllerWeb {

    private LinkService linkService;
    
    LinkControllerWeb(LinkService linkService) {
        this.linkService = linkService;
    }

    @GetMapping
    public String index(ModelMap model) {
        model.addAttribute("links", linkService.links());
        return "link/index";
    }
}
