package br.com.uniametica.estacionamento.entity;

import java.time.LocalDateTime;


public abstract class Principal {
    private int id;
    private LocalDateTime cadastro;
    private LocalDateTime edicao;
    private boolean ativo;

    public Principal(int id, LocalDateTime cadastro, LocalDateTime edicao, boolean ativo) {
        this.id = id;
        this.cadastro = cadastro;
        this.edicao = edicao;
        this.ativo = ativo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getCadastro() {
        return cadastro;
    }

    public void setCadastro(LocalDateTime cadastro) {
        this.cadastro = cadastro;
    }

    public LocalDateTime getEdicao() {
        return edicao;
    }

    public void setEdicao(LocalDateTime edicao) {
        this.edicao = edicao;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}
