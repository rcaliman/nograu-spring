package nograu.site.bikefit.controller.api;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import nograu.site.bikefit.dto.BikeFitDtoRequest;
import nograu.site.bikefit.model.BikeFit;
import nograu.site.bikefit.service.AiService;
import nograu.site.bikefit.service.BikeFitService;
import nograu.site.mapper.BikeFitMapper;

@RestController
@RequestMapping("/api/bikefit")
public class BikeFitControllerApi {

    private BikeFitService bikeFitService;
    private AiService aiService;

    BikeFitControllerApi(BikeFitService bikeFitService, AiService aiService) {
        this.bikeFitService = bikeFitService;
        this.aiService = aiService;
    }

    @PostMapping
    public ResponseEntity<BikeFit> salvar(@RequestBody BikeFitDtoRequest bikeFitDtoRequest) {

        BikeFit bikeFit = bikeFitService.salvar(BikeFitMapper.INSTANCE.toBikefit(bikeFitDtoRequest));

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(bikeFit.getId())
                        .toUri();
        return ResponseEntity.created(uri).body(bikeFit);
    }

    @GetMapping
    public ResponseEntity<List<BikeFit>> buscarPorEmail(@RequestParam("email") String email) {
        List<BikeFit> bikefits = bikeFitService.buscarPorEmail(email);
        return ResponseEntity.ok().body(bikefits);
    }

    @GetMapping("/totalDia")
    public ResponseEntity<Long> totalCalculosHoje() {
        return ResponseEntity.ok().body(bikeFitService.quantidadeCalculosHoje());
    }

    @GetMapping("/calculosDoDia")
    public ResponseEntity<List<BikeFit>> calculosDoDia() {
        return ResponseEntity.ok().body(bikeFitService.calculosDoDia());
    }

    @PostMapping("/teste")
    public String teste(@RequestBody String questao) {
        var x = aiService.getAnswer(questao);
        return x;
    }

}
