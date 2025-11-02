package nograu.site.bikefit.controller.web;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({ "/sobre", "/sobre/", "/about", "/about/" })
public class SobreControllerWeb {

    private MessageSource messageSource;

    SobreControllerWeb(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @GetMapping
    public String index(ModelMap model) {
        // Meta tags SEO
        model.addAttribute("metaDescription",
                messageSource.getMessage("meta.description.sobre", null, LocaleContextHolder.getLocale()));
        model.addAttribute("metaKeywords",
                messageSource.getMessage("meta.keywords.sobre", null, LocaleContextHolder.getLocale()));
        model.addAttribute("metaOgTitle",
                messageSource.getMessage("meta.og.title.sobre", null, LocaleContextHolder.getLocale()));
        model.addAttribute("metaOgDescription",
                messageSource.getMessage("meta.og.description.sobre", null, LocaleContextHolder.getLocale()));
        model.addAttribute("metaTwitterTitle",
                messageSource.getMessage("meta.og.title.sobre", null, LocaleContextHolder.getLocale()));
        model.addAttribute("metaTwitterDescription",
                messageSource.getMessage("meta.og.description.sobre", null, LocaleContextHolder.getLocale()));
        model.addAttribute("canonicalUrl", "https://www.nograu.com.br/sobre");

        return "sobre/index";
    }
}
