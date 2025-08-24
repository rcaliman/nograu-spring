package nograu.site.horas.controller.web;

import java.time.LocalTime;

import org.springframework.stereotype.Controller;
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
@RequestMapping({"/horas", "/horas/"})
public class HorasControllerWeb {

    private HorasService horasService;

    HorasControllerWeb(HorasService horasService) {
        this.horasService = horasService;
    }
    
    @ModelAttribute("horas")
    public Horas horas() {
        var hora = new Horas();
        hora.setCargaHoraria(LocalTime.of(8, 0, 0));
        return hora;
    }


    @GetMapping
    public String index() {
        return "horas/index";
    }

    @PostMapping("/calcular")
    public String calculaHoras(@Valid Horas horas,  BindingResult bindingResult, RedirectAttributes attr) {
        if(bindingResult.hasErrors()) {
            return "redirect:/horas";
        }
        var horaSaida = horasService.calculaSaida(horas);
        attr.addFlashAttribute("horaSaida", horaSaida);
        return "redirect:/horas";
    }

    
}
