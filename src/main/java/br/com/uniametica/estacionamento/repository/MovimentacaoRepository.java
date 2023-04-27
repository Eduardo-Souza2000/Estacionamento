package br.com.uniametica.estacionamento.repository;
import br.com.uniametica.estacionamento.entity.Condutor;
import br.com.uniametica.estacionamento.entity.Movimentacao;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovimentacaoRepository extends JpaRepository<Movimentacao,Long> {
    List<Movimentacao> findByAtivoTrue();

}
