package br.com.uniametica.estacionamento.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
//Referencia de tabela
@Table(name = "movimentacao", schema = "public")
public class Movimentacao extends AbstractEntity{
    @Getter @Setter
    @JoinColumn (name = "veiculo", nullable = false, unique = true)
    @ManyToOne
    private Veiculo veiculo;

    @Getter @Setter
    @JoinColumn (name = "condutor", nullable = false)
    @ManyToOne
    private Condutor condutor;

    @Getter @Setter
    @Column(name = "entrada", nullable = false)
    private LocalDateTime entrada;

    @Getter @Setter
    @Column(name = "saida")
    private LocalDateTime saida;

    @Getter @Setter
    @Column(name = "tempo")
    private LocalTime tempo;

    @Getter @Setter
    @Column(name = "tempo-desconto")
    private LocalTime tempoDesconto;

    @Getter @Setter
    @Column(name = "tempo-multa")
    private LocalTime tempoMulta;

    @Getter @Setter
    @Column(name = "valor-desconto")
    private BigDecimal valorDesconto;

    @Getter @Setter
    @Column(name = "valor-multa")
    private BigDecimal valorMulta;

    @Getter @Setter
    @Column(name = "valor-total")
    private BigDecimal valorTotal;

    @Getter @Setter
    @Column(name = "valor-hora")
    private BigDecimal valorHora;

    @Getter @Setter
    @Column(name = "valor-hora-multa")
    private BigDecimal valorHoraMulta;



}
