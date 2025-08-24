package nograu.site.bikefit.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import nograu.site.bikefit.model.Mural;
import nograu.site.bikefit.repository.MuralRepository;

@Service
public class MuralService {

    private MuralRepository muralRepository;

    MuralService(MuralRepository muralRepository) {
        this.muralRepository = muralRepository;
    }
    
    @Transactional
    public void salvar(Mural mural) {
        muralRepository.save(mural);
    }

    public Page<Mural> listar(int pageNum) {
        Pageable pageable = PageRequest.of(pageNum, 10, Sort.by("createdAt").descending());
        return muralRepository.findAll(pageable);
    }

}
