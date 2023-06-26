package br.com.uniametica.estacionamento.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;


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
    @Column(name = "hora")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalDateTime horaAtual;

    @Getter @Setter
    @Column(name = "entrada", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime entrada;

    @Getter @Setter
    @Column(name = "saida")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime saida;

    @Getter @Setter
    @Column (name = "tempo_total_hora")
    private Integer tempoTotalhora;

    @Getter @Setter
    @Column (name = "tempo_total_minuto")
    private int tempoTotalminuto;


    @Getter @Setter
    @Column(name = "tempodesconto")
    private int tempoDesconto;

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
    @Column(name = "valorminutomulta")
    private BigDecimal valorHoraMulta;

    @Override
    public String toString(){

        return
                ("Bem Vindo Ao Estacionamento do Semestre" + "\n") +
                ("Hora Atual: " + getHoraAtual() + "\n") +
                ("--------------------------------------------") +
                ("\n" + "*********  DADOS DO CLIENTE  ************************************" + "\n") +
                ("Condutor: " + getCondutor().getNome() + "\n") +
                ("Veiculo:" + "\n" + "PLACA - " + getVeiculo().getPlaca() +  "\n" +"COR - "+ getVeiculo().getCor()  + "\n" +"TIPO: " + getVeiculo().getTipo() + "\n" +
                "Ano: " + getVeiculo().getAno() + "\n" + "Modelo: " + getVeiculo().getModelo().getNome() + "\n" + "Marca: " + getVeiculo().getModelo().getMarca().getNome() + "\n") +
                ("Hora da Entrada: " + getEntrada() + "\n") +
                ("Hora Saida: " + getSaida() + "\n") +
                ("Tempo total do condutor em suas movimentaçoes: " + getCondutor().getTempototal() + "Horas" +"\n")+
                ("Tempo do Condutor Desconto: " + getCondutor().getTempoDesconto() + "Horas" + "\n")+
                ("Tempo Acumulado para o proximo desconto: " + getCondutor().getTempoPago() + "Horas" + "\n") +
                ("-----------------------------------------------------------------") +
                ("\n"+ "*********  TEMPO NO ESTABELECIMENTO  ************************************"+"\n") +
                ("Tempo Total estacionado: HORAS:" + getTempoTotalhora()+ " Horas" +  "\n" + "Tempo Total estacionado:" + getTempoTotalminuto() + " Minutos"+ "\n") +
                ("Tempo Multa  por Tempo Excedente: HORAS: " + getTempoMultaHora() +  "\n"+"Tempo Excepente:"  + getTempoMultaMinuto() + " Minutos" +"\n") +
                ("Tempo De Desconto: " + getTempoDesconto() + " Horas" +"\n") +
                ("-----------------------------------------------------------------") +
                ("\n" + "*********  FINANCEIRO  ************************************" + "\n") +
                ("Valor Por Hora: R$ " + getValorHora() + "\n") +
                ("Valor por Minuto Multa R$ " + getValorHoraMulta() + "\n") +
                ("Valor Desconto: R$ " + "-" + getValorDesconto() + "\n") +
                ("Valor a pagar da Multa R$ " + getValorMulta() + "\n") +
                ("Valor a pagar por tempo Estacionado dentro do Horário (sem multa) R$ " + (getTempoTotalhora().intValue() - getTempoMultaHora() ) * getValorHora().intValue() + "\n") +
                ("Valor Total A pagar  R$ " + getValorTotal() + "\n");

    }
}


