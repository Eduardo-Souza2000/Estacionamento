package br.com.uniametica.estacionamento.repository;
import br.com.uniametica.estacionamento.entity.Movimentacao;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MovimentacaoRepository extends JpaRepository<Movimentacao,Long> {
}
