package nograu.site.pcd.controller.api;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import nograu.site.pcd.dto.BancoResponse;
import nograu.site.pcd.model.Banco;
import nograu.site.pcd.service.BancoService;

@RestController
@RequestMapping("/api/banco")
public class BancoControllerApi {

    private BancoService bancoService;

    BancoControllerApi(BancoService bancoService) {
        this.bancoService = bancoService;
    }

    @GetMapping("/salva_bancos")
    public void salvaBancos() throws FileNotFoundException, IOException {
        bancoService.salvaBancosNovos();
    }       

    @DeleteMapping("/apaga_bancos")
    public void apagaBancos() {
        bancoService.apagaBancosAntigos();
    }

    @GetMapping
    public ResponseEntity<BancoResponse> buscaPorNumeroCodigo(@RequestParam(name = "codigo") String codigo) throws IOException, InterruptedException {
        Banco banco =  bancoService.buscaBanco(codigo);

        if(banco == null) {
            return ResponseEntity.notFound().build();
        }

        BancoResponse bancoDto = new BancoResponse(banco.getIspb(),
                                        banco.getNomeReduzido(),
                                        banco.getNumeroCodigo(),
                                        banco.getParticipaCompe(),
                                        banco.getAcessoPrincipal(),
                                        banco.getNomeExtenso(),
                                        banco.getNumeroCodigo());
                                        
        return ResponseEntity.ok().body(bancoDto);
    }
}