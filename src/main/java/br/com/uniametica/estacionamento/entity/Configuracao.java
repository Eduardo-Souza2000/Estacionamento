package br.com.uniametica.estacionamento.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @Column(name = "valorhora", nullable = false)
    private  BigDecimal valorHora;
    @Getter @Setter
    @Column(name = "valorminutomulta", nullable = false)
    private  BigDecimal valorMinutoMulta;
    @Getter @Setter
    @Column(name = "inicioexpediente", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime inicioExpediente;
    @Getter @Setter
    @Column(name = "fimexpediente", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime fimExpediente;
    @Getter @Setter
    @Column(name = "tempoparadesconto", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime tempoParaDesconto;
    @Getter @Setter
    @Column(name = "tempodedesconto", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    private LocalTime tempoDeDesconto;
    @Getter @Setter
    @Column(name = "gerardesconto", nullable = false)
    private boolean gerarDesconto;
    @Getter @Setter
    @Column(name = "vagasmoto", nullable = false)
    private int vagasMoto;
    @Getter @Setter
    @Column(name = "vagascarro", nullable = false)
    private int vagasCarro;
    @Getter @Setter
    @Column(name = "vagasvan", nullable = false)
    private int vagasVan;

}
