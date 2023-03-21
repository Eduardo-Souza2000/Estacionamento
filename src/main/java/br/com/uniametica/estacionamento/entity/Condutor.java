package br.com.uniametica.estacionamento.entity;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class Condutor extends AbstractEntity {
    private String nome;

    private String cpf;

    private String telefone;

    private LocalTime tempoPago;

    private LocalTime tempoDesconto;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public LocalTime getTempoPago() {
        return tempoPago;
    }

    public void setTempoPago(LocalTime tempoPago) {
        this.tempoPago = tempoPago;
    }

    public LocalTime getTempoDesconto() {
        return tempoDesconto;
    }

    public void setTempoDesconto(LocalTime tempoDesconto) {
        this.tempoDesconto = tempoDesconto;
    }

    public Condutor(int id, LocalDateTime cadastro, LocalDateTime edicao, boolean ativo) {
        super(id, cadastro, edicao, ativo);
    }
}
