package nograu.site.bikefit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import nograu.site.bikefit.model.Link;

public interface LinkRepository extends JpaRepository<Link, Long>{
    List<Link> findAllByOrderByDescricao();
}
