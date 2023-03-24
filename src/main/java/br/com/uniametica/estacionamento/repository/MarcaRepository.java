package br.com.uniametica.estacionamento.repository;

import br.com.uniametica.estacionamento.entity.Marca;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarcaRepository extends JpaRepository<Marca,Long> {
}
