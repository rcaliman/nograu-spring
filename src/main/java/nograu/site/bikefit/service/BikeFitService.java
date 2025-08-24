package nograu.site.bikefit.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import nograu.site.bikefit.model.BikeFit;
import nograu.site.bikefit.repository.BikeFitRepository;
import nograu.site.bikefit.util.BikeFitUtil;

@Service
public class BikeFitService {

    private BikeFitRepository bikeFitRepository;
    private BikeFitUtil bikeFitUtil;

    BikeFitService(BikeFitRepository bikeFitRepository, BikeFitUtil bikeFitUtil) {
        this.bikeFitRepository = bikeFitRepository;
        this.bikeFitUtil = bikeFitUtil;
    }

    @Transactional
    public BikeFit salvar(BikeFit bikeFit) {
        bikeFit.setTronco(bikeFitUtil.tronco(bikeFit));
        bikeFit.setQuadroSpeed(bikeFitUtil.quadroSpeed(bikeFit));
        bikeFit.setQuadroMtb(bikeFitUtil.quadroMtb(bikeFit));
        bikeFit.setAlturaSelim(bikeFitUtil.alturaSelim(bikeFit));
        bikeFit.setTopTubeEfetivo(bikeFitUtil.topTubeEfetivo(bikeFit));
        return bikeFitRepository.save(bikeFit);
    }

    public BikeFit consultar(Long id) {
        return bikeFitRepository.findById(id).orElse(null);
    }

    public List<BikeFit> buscarPorEmail(String email) {
        return bikeFitRepository.findByEmailOrderByCreatedAtDesc(email);
    }

    public Long quantidadeCalculosHoje() {
        var inicioPeriodo = LocalDate.now().atStartOfDay();
        var finalPeriodo = LocalDate.now().plusDays(1).atStartOfDay();
        return bikeFitRepository.countByCreatedAtBetween(inicioPeriodo, finalPeriodo);
    }

}
