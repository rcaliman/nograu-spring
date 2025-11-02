package nograu.site.horas.controller.web;

import java.time.LocalTime;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import nograu.site.horas.model.Horas;
import nograu.site.horas.service.HorasService;

@Controller
@RequestMapping({ "/horas", "/horas/" })
public class HorasControllerWeb {

    private HorasService horasService;
    private MessageSource messageSource;

    HorasControllerWeb(HorasService horasService, MessageSource messageSource) {
        this.horasService = horasService;
        this.messageSource = messageSource;
    }

    @ModelAttribute("horas")
    public Horas horas() {
        var hora = new Horas();
        hora.setCargaHoraria(LocalTime.of(8, 0, 0));
        return hora;
    }

    @GetMapping
    public String index(ModelMap model) {
        // Meta tags SEO
        model.addAttribute("metaDescription",
                messageSource.getMessage("meta.description.horas", null, LocaleContextHolder.getLocale()));
        model.addAttribute("metaKeywords",
                messageSource.getMessage("meta.keywords.horas", null, LocaleContextHolder.getLocale()));
        model.addAttribute("metaOgTitle",
                messageSource.getMessage("meta.og.title.horas", null, LocaleContextHolder.getLocale()));
        model.addAttribute("metaOgDescription",
                messageSource.getMessage("meta.og.description.horas", null, LocaleContextHolder.getLocale()));
        model.addAttribute("metaTwitterTitle",
                messageSource.getMessage("meta.og.title.horas", null, LocaleContextHolder.getLocale()));
        model.addAttribute("metaTwitterDescription",
                messageSource.getMessage("meta.og.description.horas", null, LocaleContextHolder.getLocale()));
        model.addAttribute("canonicalUrl", "https://www.nograu.com.br/horas");

        return "horas/index";
    }

    @PostMapping("/calcular")
    public String calculaHoras(@Valid Horas horas, BindingResult bindingResult, RedirectAttributes attr) {
        if (bindingResult.hasErrors()) {
            return "redirect:/horas";
        }
        var horaSaida = horasService.calculaSaida(horas);
        attr.addFlashAttribute("horaSaida", horaSaida);
        return "redirect:/horas";
    }

}
