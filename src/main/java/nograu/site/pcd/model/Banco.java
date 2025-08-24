package nograu.site.pcd.model;

import java.time.LocalDate;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tb_banco")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Banco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ispb", nullable = false, unique = true)
    private String ispb;

    @Column(name = "nome_reduzido", nullable = false)
    private String nomeReduzido;

    @Column(name = "numero_codigo", nullable = true, unique = false)
    private String numeroCodigo;

    @Column(name = "participa_compe", nullable = false)
    private String participaCompe;

    @Column(name = "acesso_principal", nullable = false)
    private String acessoPrincipal;

    @Column(name = "nome_extenso", nullable = false)
    private String nomeExtenso;

    @Column(name = "inicio_operacao", nullable = false)
    private String inicioOperacao;

    @Column(name = "created_at", nullable = false)
    @CreatedDate
    private LocalDate createdAt;

}
