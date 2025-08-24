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
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tb_bikefit")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class BikeFit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cavalo", nullable = false)
    @NotNull(message = "{bikefit.NotNull.message}")
    @Min(value = 3, message = "{bikefit.MinMax.message}")
    @Max(value = 250, message = "{bikefit.MinMax.message}")
    private Double cavalo;

    @Column(name = "esterno", nullable = false)
    @NotNull(message = "{bikefit.NotNull.mensagem}")
    @Min(value = 3, message = "{bikefit.MinMax.message}")
    @Max(value = 250, message = "{bikefit.MinMax.message}")
    private Double esterno;

    @Column(name = "braco", nullable = false)
    @NotNull(message = "{bikefit.NotNull.message}")
    @Min(value = 4, message = "{bikefit.MinMax.message}")
    @Max(value = 200, message = "{bikefit.MinMax.message}")
    private Double braco;

    @Column(name = "tronco", nullable = true)
    private Double tronco;

    @Column(name = "email", nullable = false)
    @NotBlank(message = "{bikefit.NotBlank.email.message}")
    @Email(message = "{bikefit.Email.message}")
    private String email;

    @Column(name = "quadro_speed", nullable = true)
    private Double quadroSpeed;

    @Column(name = "quadro_mtb", nullable = true)
    private Double quadroMtb;

    @Column(name = "altura_selim", nullable = true)
    private Double alturaSelim;

    @Column(name = "top_tube_efetivo", nullable = true)
    private Double topTubeEfetivo;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = true, updatable = true)
    private LocalDateTime updatedAt;
    
}
