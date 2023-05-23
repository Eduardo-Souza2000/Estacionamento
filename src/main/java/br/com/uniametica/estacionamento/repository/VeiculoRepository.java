package br.com.uniametica.estacionamento.repository;

import br.com.uniametica.estacionamento.entity.Condutor;
import br.com.uniametica.estacionamento.entity.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VeiculoRepository extends JpaRepository <Veiculo, Long> {
    List<Veiculo> findByAtivoTrue();



    @Query(value = "select exists (select * from movimentacoes where veiculo = :id)", nativeQuery = true)
    boolean veiculoExistente(@Param("id") final Long id);


    @Query(value = "select exists (select * from veiculos where id = :id)", nativeQuery = true)
    boolean ProcuraId(@Param("id") final Long id);

    @Query(value = "select exists (select * from veiculos where placa = :placa)", nativeQuery = true)
    boolean ProcuraPlaca(@Param("placa") final String placa);

    @Query(value = "select veiculo.id from Veiculo veiculo where veiculo.placa = :placa")
    Long placaexistenteNoVeiculo(@Param("placa") String placa);


}
