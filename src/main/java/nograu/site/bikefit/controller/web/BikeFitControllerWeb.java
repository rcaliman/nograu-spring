package nograu.site.bikefit.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import nograu.site.bikefit.model.BikeFit;
import nograu.site.bikefit.service.BikeFitService;

@Controller
@RequestMapping("/bikefit")
public class BikeFitControllerWeb {

    private BikeFitService bikeFitService;

    BikeFitControllerWeb(BikeFitService bikeFitService) {
        this.bikeFitService = bikeFitService;
    }
    
    @GetMapping
    public String index(ModelMap model) {
        model.addAttribute("quantidadeCalculosHoje", bikeFitService.quantidadeCalculosHoje());
        model.addAttribute("bikefit", new BikeFit());
        return "bikefit/index";
    }

    @PostMapping("/processar-bikefit")
    public String resultado(@Valid @ModelAttribute("bikefit") BikeFit bikeFit, BindingResult result, RedirectAttributes redirectAttributes) {

        if(result.hasErrors()) {
            return "bikefit/index";
        }

        BikeFit bikefitSalvo = bikeFitService.salvar(bikeFit);
        redirectAttributes.addFlashAttribute("bikefit", bikefitSalvo);

        return "redirect:/bikefit/exibir-resultado";

    }

    @GetMapping("/exibir-resultado")
    public String exibirResultado() {
        return "bikefit/resultado";
    }

    @GetMapping("/anteriores")
    public String anteriores(@RequestParam(name="email", required= false) String email, ModelMap model) {
        var bikefits = bikeFitService.buscarPorEmail(email);

        model.addAttribute("bikefits", bikefits);
        model.addAttribute("email", email);

        return "bikefit/anteriores";
    }
    
}
