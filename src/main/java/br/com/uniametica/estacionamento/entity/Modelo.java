package br.com.uniametica.estacionamento.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class Modelo extends AbstractEntity{
    @Getter @Setter
    private String Nome;
    @Getter @Setter
    private Marca marca;






}
