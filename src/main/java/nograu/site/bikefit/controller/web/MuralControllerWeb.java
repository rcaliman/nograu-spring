package nograu.site.bikefit.controller.web;

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

    MuralControllerWeb(MuralService muralService) {
        this.muralService = muralService;
    }
    
    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute("mural") Mural mural, BindingResult result, ModelMap model) {

        if(result.hasErrors()) {
            Page<Mural> paginaDeMensagens = muralService.listar(0);
            model.addAttribute("paginaDeMensagens", paginaDeMensagens);
            return "mural/index";
        }

        muralService.salvar(mural);
        return "redirect:/mural";
    }

    @GetMapping
    public String index(
        ModelMap model,
        @RequestParam(name = "page", defaultValue = "0", required=false) int page) {
        
        Page<Mural> paginaDeMensagens = muralService.listar(page);

        model.addAttribute("paginaDeMensagens", paginaDeMensagens);
        model.addAttribute("mural", new Mural());

        return "mural/index";
    }
}
