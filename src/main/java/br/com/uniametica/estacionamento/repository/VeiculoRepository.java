package br.com.uniametica.estacionamento.repository;

import br.com.uniametica.estacionamento.entity.Condutor;
import br.com.uniametica.estacionamento.entity.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VeiculoRepository extends JpaRepository <Veiculo, Long> {
    List<Veiculo> findByAtivoTrue();

}
