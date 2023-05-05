package br.com.uniametica.estacionamento.repository;

import br.com.uniametica.estacionamento.entity.Condutor;
import br.com.uniametica.estacionamento.entity.Marca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CondutorRepository extends JpaRepository <Condutor, Long> {
    List<Condutor> findByAtivoTrue();
    @Query(value = "select exists (select * from modelos where nome = :nome)", nativeQuery = true)
    boolean nomeExistente(@Param("nome") final String nome);


    @Query(value = "select exists (select * from modelos where id = :id)", nativeQuery = true)
    boolean idExistente(@Param("id") final Long id);




}
