package br.com.uniametica.estacionamento.repository;

import br.com.uniametica.estacionamento.entity.Configuracao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ConfiguracaoRepository extends JpaRepository<Configuracao, Long> {

    @Query(value = "select exists (select * from configuracoes where id = :id)", nativeQuery = true)
    boolean ProcuraId(@Param("id") final Long id);

}
