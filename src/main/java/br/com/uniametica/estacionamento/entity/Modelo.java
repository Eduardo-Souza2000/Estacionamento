package br.com.uniametica.estacionamento.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Entity
//Referencia de tabela
@Table(name = "modelo", schema = "public")
public class Modelo extends AbstractEntity{
    @Getter @Setter
    @Column(name = "nome", nullable = false, unique = true, length = 50)
    private String Nome;
    @Getter @Setter
    @Column(name = "marca", nullable = false)
    private Marca marca;

}
