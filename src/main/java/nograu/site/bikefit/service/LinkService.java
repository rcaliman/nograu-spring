package nograu.site.bikefit.service;

import java.util.List;

import org.springframework.stereotype.Service;

import nograu.site.bikefit.model.Link;
import nograu.site.bikefit.repository.LinkRepository;

@Service
public class LinkService {
    
    private LinkRepository linkRepository;

    LinkService(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    public void salvar(Link link) {
        linkRepository.save(link);
    }

    public List<Link> links() {
        return linkRepository.findAllByOrderByDescricao();
    }

}
