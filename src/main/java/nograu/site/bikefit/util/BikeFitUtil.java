package nograu.site.bikefit.util;

import org.springframework.stereotype.Component;

import nograu.site.bikefit.model.BikeFit;

@Component
public class BikeFitUtil {

    public Double tronco(BikeFit bikefit) {
        return bikefit.getEsterno() - bikefit.getCavalo();
    }

    public Double quadroSpeed(BikeFit bikeFit) {
        return bikeFit.getCavalo() * 0.67;
    }

    public Double quadroMtb(BikeFit bikeFit) {
        return (bikeFit.getCavalo() * 0.67 - 10) * 0.393700787;
    }

    public Double alturaSelim(BikeFit bikeFit) {
        return bikeFit.getCavalo() * 0.883;
    }

    public Double topTubeEfetivo(BikeFit bikeFit) {
        return ((bikeFit.getEsterno() - bikeFit.getCavalo() + bikeFit.getBraco()) / 2) + 4;
    }
    
}
