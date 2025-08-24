package nograu.site.pcd.controller.web;

import java.io.IOException;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import nograu.site.pcd.dto.EmprestimoRequest;
import nograu.site.pcd.mapper.BancoMapper;
import nograu.site.pcd.model.Banco;
import nograu.site.pcd.model.Emprestimo;
import nograu.site.pcd.service.BancoService;
import nograu.site.pcd.service.EmprestimoService;
import nograu.site.pcd.service.FileDownloadService;

@Controller
@RequestMapping({"/pcd", "/pcd/"})
public class EmprestimoControllerWeb {

    private EmprestimoService emprestimoService;
    private BancoService bancoService;
    private MessageSource messageSource;
    private FileDownloadService fileDownloadService;

    EmprestimoControllerWeb(EmprestimoService emprestimoService, BancoService bancoService,
            MessageSource messageSource, FileDownloadService fileDownloadService) {
        this.emprestimoService = emprestimoService;
        this.bancoService = bancoService;
        this.messageSource = messageSource;
        this.fileDownloadService = fileDownloadService;
    }

    @ModelAttribute("emprestimo")
    public Emprestimo emprestimo() {
        return new Emprestimo();
    }

    @ModelAttribute("banco")
    public Banco banco() {
        return new Banco();
    }

    @GetMapping
    public String index(ModelMap model) throws IOException, InterruptedException {
        fileDownloadService.atualizaBancos();
        model.addAttribute("contador", emprestimoService.contaCalculos());
        model.addAttribute("dataArquivo", bancoService.dataPrimeiroRegistro());
        return "pcd/index";
    }

    @PostMapping("/processar-calculo")
    public String processarCalculo(EmprestimoRequest request, RedirectAttributes attr)
            throws IOException, InterruptedException {

        String mensagemBancoNaoEncontrado = messageSource.getMessage("pcd.banco-nao-encontrado", null,
                LocaleContextHolder.getLocale());

        try {
            var emprestimoCalculado = emprestimoService.calcularEmprestimo(request);

            fileDownloadService.atualizaBancos();
            if (bancoService.buscaBanco(emprestimoCalculado.getCodigoBanco()) == null) {
                attr.addFlashAttribute("erroBanco", mensagemBancoNaoEncontrado);
            }
            var emprestimoSalvo = emprestimoService.gravar(emprestimoCalculado);
            attr.addFlashAttribute("emprestimo", emprestimoService.emprestimoToDto(emprestimoSalvo));
            return "redirect:/pcd/resultado";
            
        } catch (Exception e) {
            attr.addFlashAttribute("erro", e.getMessage());
            attr.addFlashAttribute("emprestimo", request);
        }
        return "redirect:/pcd";
    }

    @GetMapping("/resultado")
    public String exibirCalculo() {
        return "pcd/resultado";
    }

    @GetMapping("/calculos-anteriores")
    public String calculosAnteriores(ModelMap model) {
        var emprestimos = emprestimoService.listar();
        model.addAttribute("emprestimos", emprestimos);
        return "pcd/anteriores";
    }

    @PostMapping("/buscar-banco")
    public String buscarBanco(String numeroCodigo, ModelMap model) throws IOException, InterruptedException {

        String mensagemBancoNaoEncontrado = messageSource.getMessage("pcd.banco-nao-encontrado", null,
                LocaleContextHolder.getLocale());

        fileDownloadService.atualizaBancos();
        var banco = bancoService.buscaBanco(numeroCodigo);
        if (banco == null) {
            model.addAttribute("erro", mensagemBancoNaoEncontrado);
        } else {
            model.addAttribute("banco", BancoMapper.INSTANCE.toBancoResponse(banco));
        }
        return "pcd/banco";
    }
}
