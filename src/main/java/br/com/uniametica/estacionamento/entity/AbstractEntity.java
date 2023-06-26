package br.com.uniametica.estacionamento.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

//Metodo de superclass
@MappedSuperclass
public abstract class AbstractEntity {

    //ANNOTATIONS
    //Colocamos o @Id para ser o primary key
    //NO ID NÃO SE COLOCA O @SETTER POIS NAO PODE SER ALTERADO.
    //@Column -> forma de como veremos no banco de dados.
    @Id
    @Getter
    @Column(name = "id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Getter @Setter
    @Column(name = "dtCadastro", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime cadastro;

    @Getter @Setter
    @Column(name = "dtAtualizacao")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime atualizacao;

    @Getter @Setter
    @Column(name = "ativo", nullable = false)
    private boolean ativo;

    //ANTES DE REALIZAR A PERSISTENCIA ELE VAI EXECUTAR ESSE METODO, ISSO QUE SIGNIFICA.
    //SIGNIFICA ANTES DE REALIZAR O PROCEDIMENTO VAI REALIZAR ESSE METODO.
    @PrePersist
    private void prePersist(){
        this.cadastro = LocalDateTime.now();
        this.ativo = true;
    }


    @PreUpdate
    private void preUpdate(){
        this.atualizacao = LocalDateTime.now();
    }




}
