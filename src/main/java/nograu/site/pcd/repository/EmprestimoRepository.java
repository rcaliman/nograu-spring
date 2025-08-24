package nograu.site.pcd.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import nograu.site.pcd.model.Emprestimo;
import java.util.List;
import java.time.LocalDate;


public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {
    List<Emprestimo> findByCreatedAtOrderByIdDesc(LocalDate createdAt);
    Long countByCreatedAt(LocalDate createdAt);
}
