package br.com.uniametica.estacionamento.repository;
import br.com.uniametica.estacionamento.entity.Condutor;
import br.com.uniametica.estacionamento.entity.Configuracao;
import br.com.uniametica.estacionamento.entity.Movimentacao;

import br.com.uniametica.estacionamento.entity.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MovimentacaoRepository extends JpaRepository<Movimentacao,Long> {
    List<Movimentacao> findByAtivoTrue();


    @Query(value = "select exists (select * from movimentacoes where id = :id)", nativeQuery = true)
    boolean ProcuraId(@Param("id") final Long id);



    @Query(value = "SELECT c FROM Configuracao c")
    Configuracao obterConfiguracao();



//    @Query(value = "SELECT * FROM condutores where id = :id")
//    Condutor obterCondutor();

}
