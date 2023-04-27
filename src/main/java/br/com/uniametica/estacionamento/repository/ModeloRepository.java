package br.com.uniametica.estacionamento.repository;

import br.com.uniametica.estacionamento.entity.Condutor;
import br.com.uniametica.estacionamento.entity.Modelo;
import org.springframework.boot.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ModeloRepository extends JpaRepository <Modelo, Long> {
    List<Modelo> findByAtivoTrue();

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
