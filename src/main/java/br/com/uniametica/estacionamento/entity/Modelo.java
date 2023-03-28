package br.com.uniametica.estacionamento.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Entity
//Referencia de tabela
@Table(name = "modelos", schema = "public")
public class Modelo extends AbstractEntity{
    @Getter @Setter
    @Column(name = "nome", nullable = false, unique = true, length = 50)
    private String Nome;
    @Getter @Setter
    @JoinColumn(name = "marca", nullable = false)
    @ManyToOne
    private Marca marca;

}
