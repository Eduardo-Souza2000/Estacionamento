package br.com.uniametica.estacionamento.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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
    @Column (name = "veiculo", nullable = false, unique = true)
    private Veiculo veiculo;

    @Getter @Setter
    @Column(name = "condutor", nullable = false)
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
