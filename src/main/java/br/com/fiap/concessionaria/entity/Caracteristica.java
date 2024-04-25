package br.com.fiap.concessionaria.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "TB_CARACTERISTICA", uniqueConstraints = {
        @UniqueConstraint(name = "UK_CARACTERISTICA_NOME_VEICULO", columnNames = {"nome", "VEICULO"})
})
public class Caracteristica {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_CARACTERISICA")
    @SequenceGenerator(name = "SQ_CARACTERISICA", sequenceName = "SQ_CARACTERISICA", allocationSize = 1)
    @Column(name = "ID_CARACTERISTICA")
    private Long id;

    @Column(length = 30)
    private String nome;

    @Column(length = 20)
    private String descricao;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(
            name = "VEICULO",
            referencedColumnName = "ID_VEICULO",
            foreignKey = @ForeignKey(name = "FK_CARACTERISTICA_VEICULO")
    )
    private Veiculo veiculo;
}