package nograu.site.bikefit.controller.web;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
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
import nograu.site.bikefit.dto.BikeFitDtoRequest;
import nograu.site.bikefit.model.BikeFit;
import nograu.site.bikefit.service.AiService;
import nograu.site.bikefit.service.BikeFitService;
import nograu.site.bikefit.service.MarkdownService;
import nograu.site.mapper.BikeFitMapper;

@Controller
@RequestMapping("/bikefit")
public class BikeFitControllerWeb {

    private BikeFitService bikeFitService;
    private MessageSource messageSource;
    private AiService aiService;
    private MarkdownService markdownService;

    BikeFitControllerWeb(BikeFitService bikeFitService, MessageSource messageSource, AiService aiService,
            MarkdownService markdownService) {
        this.bikeFitService = bikeFitService;
        this.messageSource = messageSource;
        this.aiService = aiService;
        this.markdownService = markdownService;
    }

    @GetMapping
    public String index(ModelMap model) {
        model.addAttribute("quantidadeCalculosHoje", bikeFitService.quantidadeCalculosHoje());
        model.addAttribute("bikefit", new BikeFit());
        return "bikefit/index";
    }

    @PostMapping("/processar-bikefit")
    public String resultado(@Valid @ModelAttribute("bikefit") BikeFitDtoRequest bikeFitDtoRequest, BindingResult result,
            RedirectAttributes redirectAttributes, ModelMap model) {

        String confiraSeusDados = messageSource.getMessage("form.confira-seus-dados", null,
                LocaleContextHolder.getLocale());

        if (result.hasErrors()) {
            return "bikefit/index";
        }

        try {
            BikeFit bikefitSalvo = bikeFitService.salvar(BikeFitMapper.INSTANCE.toBikefit(bikeFitDtoRequest));
            redirectAttributes.addFlashAttribute("bikefit", bikefitSalvo);
            if (bikeFitDtoRequest.usarIa() != null) {
                redirectAttributes.addFlashAttribute("respostaAi", getRespostaAi(bikefitSalvo));
            }
        } catch (Exception e) {
            System.err.println("Erro no salvamento: " + e.getMessage());
            model.addAttribute("erro", confiraSeusDados);
            return "bikefit/index";
        }

        return "redirect:/bikefit/exibir-resultado";

    }

    private String getRespostaAi(BikeFit bikefitSalvo) {

        String textoPergunta = messageSource.getMessage("ia.pergunta", null,
                LocaleContextHolder.getLocale());
        String textoCavalo = messageSource.getMessage("ia.cavalo", null,
                LocaleContextHolder.getLocale());
        String textoEsterno = messageSource.getMessage("ia.esterno", null,
                LocaleContextHolder.getLocale());
        String textoBraco = messageSource.getMessage("ia.braco", null,
                LocaleContextHolder.getLocale());
        String textoTronco = messageSource.getMessage("ia.tronco", null,
                LocaleContextHolder.getLocale());
        String textoRoad = messageSource.getMessage("ia.road", null,
                LocaleContextHolder.getLocale());
        String textoMtb = messageSource.getMessage("ia.mtb", null,
                LocaleContextHolder.getLocale());
        String textoSelim = messageSource.getMessage("ia.selim", null,
                LocaleContextHolder.getLocale());
        String textoTopTube = messageSource.getMessage("ia.toptube", null,
                LocaleContextHolder.getLocale());

        String montaPergunta = """
                    %s:
                    %s = %s cm, %s = %s cm, %s = %s cm, %s = %s cm,
                    %s = %s cm, %s = %s in, %s = %s cm, %s = %s cm

                """;
        String pergunta = montaPergunta.formatted(
                textoPergunta,
                textoCavalo,
                bikefitSalvo.getCavalo(),
                textoEsterno,
                bikefitSalvo.getEsterno(),
                textoBraco,
                bikefitSalvo.getBraco(),
                textoTronco,
                bikefitSalvo.getTronco(),
                textoRoad,
                bikefitSalvo.getQuadroSpeed(),
                textoMtb,
                bikefitSalvo.getQuadroMtb(),
                textoSelim,
                bikefitSalvo.getAlturaSelim(),
                textoTopTube,
                bikefitSalvo.getTopTubeEfetivo());

        String markdownResponse = aiService.getAnswer(pergunta);

        String rawHtml = markdownService.convertMarkdownToHtml(markdownResponse);

        String resposta = markdownService.sanitizeHtml(rawHtml);

        return resposta;
    }

    @GetMapping("/exibir-resultado")
    public String exibirResultado() {
        return "bikefit/resultado";
    }

    @GetMapping("/anteriores")
    public String anteriores(@RequestParam(name = "email", required = false) String email, ModelMap model) {
        var bikefits = bikeFitService.buscarPorEmail(email);

        model.addAttribute("bikefits", bikefits);
        model.addAttribute("email", email);

        return "bikefit/anteriores";
    }

    @GetMapping("/calculosdodia")
    public String calculosDoDia(ModelMap model) {
        var bikefits = bikeFitService.calculosDoDia();
        model.addAttribute("bikefits", bikefits);
        return "bikefit/calculosdodia";
    }

}
