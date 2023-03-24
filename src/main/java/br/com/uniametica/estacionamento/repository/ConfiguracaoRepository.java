package br.com.uniametica.estacionamento.repository;

import br.com.uniametica.estacionamento.entity.Configuracao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfiguracaoRepository extends JpaRepository<Configuracao, Long> {
}
