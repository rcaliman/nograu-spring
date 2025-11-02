package nograu.site.bikefit.controller.web;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import nograu.site.bikefit.model.Mural;
import nograu.site.bikefit.service.MuralService;

@Controller
@RequestMapping("/mural")
public class MuralControllerWeb {

    private MuralService muralService;
    private MessageSource messageSource;

    MuralControllerWeb(MuralService muralService, MessageSource messageSource) {
        this.muralService = muralService;
        this.messageSource = messageSource;
    }

    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute("mural") Mural mural, BindingResult result, ModelMap model) {

        if (result.hasErrors()) {
            Page<Mural> paginaDeMensagens = muralService.listar(0);
            model.addAttribute("paginaDeMensagens", paginaDeMensagens);
            return "mural/index";
        }

        muralService.salvar(mural);
        return "redirect:/mural";
    }

    @GetMapping({ "/", "" })
    public String index(
            ModelMap model,
            @RequestParam(name = "page", defaultValue = "0", required = false) int page) {

        Page<Mural> paginaDeMensagens = muralService.listar(page);

        model.addAttribute("paginaDeMensagens", paginaDeMensagens);
        model.addAttribute("mural", new Mural());

        // Meta tags SEO
        model.addAttribute("metaDescription",
                messageSource.getMessage("meta.description.mural", null, LocaleContextHolder.getLocale()));
        model.addAttribute("metaKeywords",
                messageSource.getMessage("meta.keywords.mural", null, LocaleContextHolder.getLocale()));
        model.addAttribute("metaOgTitle",
                messageSource.getMessage("meta.og.title.mural", null, LocaleContextHolder.getLocale()));
        model.addAttribute("metaOgDescription",
                messageSource.getMessage("meta.og.description.mural", null, LocaleContextHolder.getLocale()));
        model.addAttribute("metaTwitterTitle",
                messageSource.getMessage("meta.og.title.mural", null, LocaleContextHolder.getLocale()));
        model.addAttribute("metaTwitterDescription",
                messageSource.getMessage("meta.og.description.mural", null, LocaleContextHolder.getLocale()));
        model.addAttribute("canonicalUrl", "https://www.nograu.com.br/mural");

        return "mural/index";
    }
}
