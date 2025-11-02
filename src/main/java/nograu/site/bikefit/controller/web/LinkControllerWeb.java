package nograu.site.bikefit.controller.web;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import nograu.site.bikefit.service.LinkService;

@Controller
@RequestMapping("/links")
public class LinkControllerWeb {

    private LinkService linkService;
    private MessageSource messageSource;

    LinkControllerWeb(LinkService linkService, MessageSource messageSource) {
        this.linkService = linkService;
        this.messageSource = messageSource;
    }

    @GetMapping
    public String index(ModelMap model) {
        model.addAttribute("links", linkService.links());

        // Meta tags SEO
        model.addAttribute("metaDescription",
                messageSource.getMessage("meta.description.links", null, LocaleContextHolder.getLocale()));
        model.addAttribute("metaKeywords",
                messageSource.getMessage("meta.keywords.links", null, LocaleContextHolder.getLocale()));
        model.addAttribute("metaOgTitle",
                messageSource.getMessage("meta.og.title.links", null, LocaleContextHolder.getLocale()));
        model.addAttribute("metaOgDescription",
                messageSource.getMessage("meta.og.description.links", null, LocaleContextHolder.getLocale()));
        model.addAttribute("metaTwitterTitle",
                messageSource.getMessage("meta.og.title.links", null, LocaleContextHolder.getLocale()));
        model.addAttribute("metaTwitterDescription",
                messageSource.getMessage("meta.og.description.links", null, LocaleContextHolder.getLocale()));
        model.addAttribute("canonicalUrl", "https://www.nograu.com.br/links");

        return "link/index";
    }
}
