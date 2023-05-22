package br.com.uniametica.estacionamento.repository;

import br.com.uniametica.estacionamento.entity.Condutor;
import br.com.uniametica.estacionamento.entity.Marca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MarcaRepository extends JpaRepository<Marca,Long> {
    List<Marca> findByAtivoTrue();



//    @Query(value = "select exists (select * from marcas where nome = :nome)", nativeQuery = true)
//    boolean NomeMarcaExistente(@Param("nome") final String nome);

//    @Query(value = "select exists (select * from modelos where marca = :id)", nativeQuery = true)
//    boolean marcaIdExistentes(@Param("id") final Long id);

//    @Query(value = "select exists (select * from marcas where id = :id)", nativeQuery = true)
//    boolean idExistente(@Param("id") final Long id);


    @Query(value = "select count(*)>0 from Marca marca where marca.nome = :nome")
    boolean NomeMarcaExistente(@Param("nome") String nome);

    @Query(value = "select count(*)>0 from Modelo modelo where modelo.marca.id = :id")
    boolean marcaIdExistentes(@Param("id") Long id);

    @Query(value = "select count(*)>0 from Marca marca where marca.id = :id")
    boolean idExistente(@Param("id") Long id);

}
