package br.com.uniametica.estacionamento.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalTime;

@Entity
//Referencia de tabela
@Table(name = "configuracoes", schema = "public")

public class Configuracao  extends AbstractEntity{
    @Getter @Setter
    @Column(name = "valor-hora", nullable = false)
    private  BigDecimal valorHora;
    @Getter @Setter
    @Column(name = "valor-minuto-multa", nullable = false)
    private  BigDecimal valorMinutoMulta;
    @Getter @Setter
    @Column(name = "inicio-expediente", nullable = false)
    private LocalTime inicioExpediente;
    @Getter @Setter
    @Column(name = "fim-expediente", nullable = false)
    private LocalTime fimExpediente;
    @Getter @Setter
    @Column(name = "tempo-para-desconto", nullable = false)
    private LocalTime tempoParaDesconto;
    @Getter @Setter
    @Column(name = "tempo-de-desconto", nullable = false)
    private LocalTime tempoDeDesconto;
    @Getter @Setter
    @Column(name = "vgerar-desconto", nullable = false)
    private boolean gerarDesconto;
    @Getter @Setter
    @Column(name = "vagas-moto", nullable = false)
    private int vagasMoto;
    @Getter @Setter
    @Column(name = "vagas-carro", nullable = false)
    private int vagasCarro;
    @Getter @Setter
    @Column(name = "vagas-van", nullable = false)
    private int vagasVan;

}
