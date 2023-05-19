package br.com.uniametica.estacionamento.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

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
    private String nome;
    @Getter @Setter
    @Column(name = "cpf", nullable = false, unique = true,length = 15)
    private String cpf;
    @Getter @Setter
    @Column(name = "telefone", nullable = false, unique = true, length = 17)
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