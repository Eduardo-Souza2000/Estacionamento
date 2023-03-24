package br.com.uniametica.estacionamento.repository;

import br.com.uniametica.estacionamento.entity.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VeiculoRepository extends JpaRepository <Veiculo, Long> {
}
