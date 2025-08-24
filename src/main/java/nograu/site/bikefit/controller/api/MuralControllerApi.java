package nograu.site.bikefit.controller.api;

import java.net.URI;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import nograu.site.bikefit.model.Mural;
import nograu.site.bikefit.service.MuralService;

@RestController
@RequestMapping("/api/mural")
public class MuralControllerApi {

    private MuralService muralService;

    MuralControllerApi(MuralService muralService) {
        this.muralService = muralService;
    }
    
    @PostMapping
    public ResponseEntity<Mural> salvar(@RequestBody Mural mural) {

        muralService.salvar(mural);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(mural.getId())
                    .toUri();
        return ResponseEntity.created(uri).body(mural);
    }

    @GetMapping
    public ResponseEntity<Page<Mural>> mensagens(@RequestParam(name = "page", defaultValue = "0", required = false) int page) {
        Page<Mural> paginaDeMensagens = muralService.listar(page);
        return ResponseEntity.ok().body(paginaDeMensagens);
    }

}
