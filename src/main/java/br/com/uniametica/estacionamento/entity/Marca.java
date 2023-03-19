package br.com.uniametica.estacionamento.entity;

import java.time.LocalDateTime;

public class Marca extends Principal{
    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Marca(int id, LocalDateTime cadastro, LocalDateTime edicao, boolean ativo) {
        super(id, cadastro, edicao, ativo);
    }


}
