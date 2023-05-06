package br.com.uniametica.estacionamento.repository;

import br.com.uniametica.estacionamento.entity.Condutor;
import br.com.uniametica.estacionamento.entity.Modelo;
import org.springframework.boot.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;
@Repository

public interface ModeloRepository extends JpaRepository <Modelo, Long> {
    List<Modelo> findByAtivoTrue();

    @Query(value = "select exists (select * from modelos where nome = :nome)", nativeQuery = true)
    boolean existente(@Param("nome") final String nome);

    @Query(value = "select exists (select * from modelos where id = :id)", nativeQuery = true)
    boolean ProcuraId(@Param("id") final Long id);

    @Query(value = "select exists (select * from veiculos where modelo = :id)", nativeQuery = true)
    boolean modeloExistente(@Param("id") final Long id);

/*
    public List<Modelo> findByNome(final String nome);

   //QUERI USANDO HQL
    @Query("form Modelo where nome like :nome")
    public List<Modelo> findByLike(@Param("nome") final String nome);

    //QUERI USANDO QUERY NATIVA
    @Query(value = "SELECT * frOm Modelo where nome like :nome", nativeQuery = true)
    public List<Modelo> findByLikeNative(@Param("nome") final String nome);
*/
}
