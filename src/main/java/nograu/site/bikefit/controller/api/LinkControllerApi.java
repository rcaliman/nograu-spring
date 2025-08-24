package nograu.site.bikefit.controller.api;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import nograu.site.bikefit.model.Link;
import nograu.site.bikefit.service.LinkService;

@RestController
@RequestMapping("/api/links")
public class LinkControllerApi {
    
    private LinkService linkService;

    LinkControllerApi(LinkService linkService) {
        this.linkService = linkService;
    }

    @PostMapping
    public ResponseEntity<Link> salvar(@RequestBody Link link) {
        linkService.salvar(link);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(link.getId())
                    .toUri();
        return ResponseEntity.created(uri).body(link);
    }

    @GetMapping
    public ResponseEntity<List<Link>> links() {
        return ResponseEntity.ok().body(linkService.links());
    }
}
