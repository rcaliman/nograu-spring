package nograu.site.bikefit.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import nograu.site.bikefit.model.BikeFit;

public interface BikeFitRepository extends JpaRepository<BikeFit, Long> {

    List<BikeFit> findByEmailOrderByCreatedAtDesc(String email);

    Long countByCreatedAtBetween(LocalDateTime startOfDay, LocalDateTime endOfDay);

    List<BikeFit> findByCreatedAtBetweenOrderByCreatedAtDesc(LocalDateTime startOfDay, LocalDateTime endOfDay);
}
