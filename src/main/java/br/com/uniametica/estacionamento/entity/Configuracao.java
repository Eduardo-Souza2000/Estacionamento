package br.com.uniametica.estacionamento.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalTime;

@Entity
//Referencia de tabela
@Table(name = "configuracao", schema = "public")

public class Configuracao  extends AbstractEntity{
    @Getter @Setter
    private  BigDecimal valorHora;
    @Getter @Setter
    private  BigDecimal valorMinutoMulta;
    @Getter @Setter
    private LocalTime inicioExpediente;
    @Getter @Setter
    private LocalTime fimExpediente;
    @Getter @Setter
    private LocalTime tempoParaDesconto;
    @Getter @Setter
    private LocalTime tempoDeDesconto;
    @Getter @Setter
    private boolean gerarDesconto;
    @Getter @Setter
    private int vagasMoto;
    @Getter @Setter
    private int vagasCarro;
    @Getter @Setter
    private int vagasVan;

}
