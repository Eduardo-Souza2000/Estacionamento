package br.com.uniametica.estacionamento.repository;

import br.com.uniametica.estacionamento.entity.Condutor;
import br.com.uniametica.estacionamento.entity.Marca;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CondutorRepository extends JpaRepository <Condutor, Long> {
    List<Condutor> findByAtivoTrue();

}
