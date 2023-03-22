package br.com.uniametica.estacionamento.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
//Referencia de tabela
@Table(name = "veiculos", schema = "public")
public class Veiculo extends AbstractEntity {
    @Getter @Setter
    @Column(name = "placa", nullable = false, unique = true, length = 15)
    private String placa;
    @Getter @Setter
    @Column(name = "modelo", nullable = false)
    private Modelo modelo;
    @Getter @Setter
    @Column(name = "cor", length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private Cores cor;

    //USAMOS ANOTATION (ENUMERATED) PARA DEFINIR O TIPO QUE QUEREMOS COLOCAR OU MELHOS APARECER NO BANCO DE DADOS
    @Getter @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", length = 20, nullable = false)
    private Tipo tipo;

    @Getter @Setter
    @Column(name = "ano", nullable = false, length = 10)
    private int ano;

}