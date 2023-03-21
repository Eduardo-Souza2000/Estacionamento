package br.com.uniametica.estacionamento.entity;

import java.time.LocalDateTime;


public abstract class AbstractEntity {
    private Long id;
    private LocalDateTime cadastro;
    private LocalDateTime atualizacao;
    private boolean ativo;



}
