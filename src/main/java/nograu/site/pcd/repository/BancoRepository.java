package nograu.site.pcd.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import nograu.site.pcd.model.Banco;

public interface BancoRepository extends JpaRepository<Banco, Long> {
    Banco findByNumeroCodigo(String numeroCodigo);
    Banco findFirstByOrderByIdAsc();
}
