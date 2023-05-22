package br.com.uniametica.estacionamento.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.jetbrains.annotations.NotNull;

import java.time.LocalTime;

@Entity
//Referencia de tabela
@Table(name = "condutores", schema = "public")
@Audited
@AuditTable(value = "condutor_audit", schema = "audit")
public class Condutor extends AbstractEntity {
    //OS @ CHAMAMOS DE ANNOTATION
    @Getter @Setter
    @Column(name = "nome", nullable = false, length = 100)
    //@NotNull(message = "Nome do condutor não pode ser vazio!")
    //@Size(min= 3, max = 50, message = "Nome possui a quantidade de caracteres permitidos min 3 max 50!")
    //@Pattern(regexp = "^[a-zA-Z\\u00C0-\\u017F´]+\\s+[a-zA-Z\\u00C0-\\u017F´]{0,}$",message = "Nome não possui formato valido! Infome nome e sobrenome!")
    private String nome;
    @Getter @Setter
    @Column(name = "cpf", nullable = false, unique = true,length = 15)
   // @NotNull(message = "CPF não pode ser vazio!")
    //@NotBlank(message = "CPF não pode estar em branco!")
    private String cpf;
    @Getter @Setter
    @Column(name = "telefone", nullable = false, unique = true, length = 17)
   // @Size(min = 11, max = 20, message = "CPF não possui a quantidade de caracteres permitidos!")
    private String telefone;
    @Getter @Setter
    @Column(name = "tempogasto")
    private int tempoPago;
    @Getter @Setter
    @Column(name = "tempototal")
    private int tempototal;

//    @Getter @Setter
//    @Column(name = "tempodesconto")
//    private LocalTime tempoDesconto;

    @Getter @Setter
    @Column(name = "tempodesconto")
    private int tempoDesconto;

}