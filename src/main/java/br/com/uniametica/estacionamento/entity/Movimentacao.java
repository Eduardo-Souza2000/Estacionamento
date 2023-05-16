package br.com.uniametica.estacionamento.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
//Referencia de tabela
@Table(name = "movimentacoes", schema = "public")
public class Movimentacao extends AbstractEntity{
    @Getter @Setter
    @JoinColumn (name = "veiculo", nullable = false)
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
    @Column (name = "tempo_total_hora")
    private Integer tempoTotalhora;

    @Getter @Setter
    @Column (name = "tempo_total_minuto")
    private Integer tempoTotalminuto;



    @Getter @Setter
    @Column(name = "tempodesconto")
    private LocalTime tempoDesconto;

    @Getter @Setter
    @Column(name = "tempomultaMinuto")
    private int tempoMultaMinuto;

    @Getter @Setter
    @Column(name = "tempomultaHora")
    private int tempoMultaHora;

    @Getter @Setter
    @Column(name = "valordesconto")
    private BigDecimal valorDesconto;

    @Getter @Setter
    @Column(name = "valormulta")
    private BigDecimal valorMulta;

    @Getter @Setter
    @Column(name = "valortotal")
    private BigDecimal valorTotal;

    @Getter @Setter
    @Column(name = "valorhora")
    private BigDecimal valorHora;

    @Getter @Setter
    @Column(name = "valorhoramulta")
    private BigDecimal valorHoraMulta;

    @Override
    public String toString(){

        return
                ("Bem Vindo Ao Estacionamento do Semestre" + "\n") +
                ("-----------------------------------------------------------------") +
                ("*********  DADOS DO CLIENTE  ************************************") +
                ("Condutor: " + getCondutor() + "\n") +
                ("Nome do Veiculo: " + this.getVeiculo() + "\n") +
                ("Hora da Entrada: " + getEntrada() + "\n") +
                ("Hora Saida: " + getSaida() + "\n") +
                ("-----------------------------------------------------------------") +
                ("*********  TEMPO NO ESTABELECIMENTO  ************************************") +
                ("Tempo Total estacionado: " + getTempoTotalhora() + ":" + getTempoTotalminuto() + "\n") +
                ("Tempo Multa  por Tempo Excepente: " + getTempoMultaHora() + ":"  + getTempoMultaMinuto() + "\n") +
                ("Tempo De Desconto: " + getTempoDesconto() + "\n") +
                ("-----------------------------------------------------------------") +
                ("*********  FINANCEIRO  ************************************") +
                ( "Valor Por Hora: R$ " + getValorHora() + "\n") +
                ( "Valor por Hora Multa R$ " + getValorHoraMulta() + "\n") +
                ("Valor Desconto: R$ " + getValorDesconto() + "\n") +
                ("Valor da Multa R$ " + getValorMulta() + "\n") +
                ("Valor Total A pagar R$ " + getValorTotal() + "\n");

    }




}
