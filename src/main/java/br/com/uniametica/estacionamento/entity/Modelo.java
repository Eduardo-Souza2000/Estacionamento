package br.com.uniametica.estacionamento.entity;

import java.time.LocalDateTime;

public class Modelo extends AbstractEntity{
    private String Nome;

    private Marca marca;


    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public Modelo(int id, LocalDateTime cadastro, LocalDateTime edicao, boolean ativo) {
        super(id, cadastro, edicao, ativo);
    }


}
