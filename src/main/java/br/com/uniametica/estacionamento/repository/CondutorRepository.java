package br.com.uniametica.estacionamento.repository;

import br.com.uniametica.estacionamento.entity.Condutor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CondutorRepository extends JpaRepository <Condutor, Long> {
}
