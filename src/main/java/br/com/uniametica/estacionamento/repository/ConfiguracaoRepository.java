package br.com.uniametica.estacionamento.repository;

import br.com.uniametica.estacionamento.entity.Configuracao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ConfiguracaoRepository extends JpaRepository<Configuracao, Long> {


    @Query(value = "select exists (select * from configuracoes where id = :id)", nativeQuery = true)
    boolean ProcuraConfiguracaoId(@Param("id") final Long id);

   @Query (value = "select c from Configuracao c order By c.id desc limit 1")
    Configuracao ultimaConfiguracao();


}
