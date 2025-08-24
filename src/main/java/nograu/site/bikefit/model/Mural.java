package nograu.site.bikefit.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import nograu.site.annotation.NoLinks;

@Table(name = "tb_mural")
@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Mural {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false)
    @NotBlank(message = "{mural.NotBlank.nome}")
    @NoLinks(message = "{mural.NoLinks.message}")
    private String nome;

    @Column(name = "email", nullable = true)
    private String email;

    @Column(name = "mensagem", nullable = false)
    @NotBlank(message = "{mural.NotBlank.message}")
    @NoLinks(message = "{mural.NoLinks.message}")
    private String mensagem;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = true, updatable = true)
    private LocalDateTime updatedAt;

}
