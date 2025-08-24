package nograu.site.pcd.controller.api;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import nograu.site.pcd.dto.EmprestimoRequest;
import nograu.site.pcd.dto.EmprestimoResponse;
import nograu.site.pcd.model.Emprestimo;
import nograu.site.pcd.service.EmprestimoService;

@RestController
@RequestMapping("/api/pcd")
public class EmprestimoControllerApi {

    private EmprestimoService emprestimoService;

    EmprestimoControllerApi(EmprestimoService emprestimoService) {
        this.emprestimoService = emprestimoService;
    }

    @PostMapping("/calcular")
    public ResponseEntity<EmprestimoResponse> calcular(@RequestBody EmprestimoRequest request)
            throws IOException, InterruptedException {
        Emprestimo emprestimoCalculado = emprestimoService.calcularEmprestimo(request);

        Emprestimo emprestimoSalvo = emprestimoService.gravar(emprestimoCalculado);
        return ResponseEntity.ok().body(
                emprestimoService.emprestimoToDto(emprestimoSalvo));
    }

    @GetMapping("/listar")
    public ResponseEntity<List<EmprestimoResponse>> listar() {
        return ResponseEntity.ok().body(emprestimoService.listar());
    }

    @GetMapping("/conta_calculos")
    public Long contaCalculos() {
        return emprestimoService.contaCalculos();
    }
}
