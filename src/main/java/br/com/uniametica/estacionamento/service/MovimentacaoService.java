package br.com.uniametica.estacionamento.service;

import br.com.uniametica.estacionamento.entity.Movimentacao;
import br.com.uniametica.estacionamento.entity.Veiculo;
import br.com.uniametica.estacionamento.repository.MovimentacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Service

public class MovimentacaoService {
    @Autowired
    private MovimentacaoRepository movimentacaoRepository;


    public Optional<Movimentacao> procuraMovimentacao(Long id){

        if (!movimentacaoRepository.ProcuraId(id) ){
            throw new RuntimeException("ID inválido - Motivo: Nao Existe no Banco de Dados");
        }else {
            Optional<Movimentacao> movimentacao = this.movimentacaoRepository.findById(id);
            return movimentacao;
        }

    }


    public List<Movimentacao> procurarLista(){

        List<Movimentacao> movimentacao = movimentacaoRepository.findAll();
        return movimentacao;
    }


    public List<Movimentacao> procurarAtivo(){

        List<Movimentacao> movimentacao = movimentacaoRepository.findByAtivoTrue();
        return movimentacao;
    }



    @Transactional(rollbackFor = Exception.class)
    public void editarMovimentacao(@RequestParam("id") final Long id, @RequestBody final  Movimentacao movimentacao) {

        final Movimentacao  movimantacaobanco = this.movimentacaoRepository.findById(id).orElse(null);

        if (movimantacaobanco == null || !movimentacao.getId().equals(movimantacaobanco.getId())) {
            throw new RuntimeException("Não foi possivel identificar o registro informado");
        } else if (movimentacao.getCondutor() == null){
            throw new RuntimeException(" Condutor inválido");
        } else if (movimentacao.getVeiculo() == null) {
            throw new RuntimeException(" Veiculo inválido");
        } else if (movimentacao.getEntrada() == null) {
            throw new RuntimeException(" Entrada inválido");
        } else{
            movimentacaoRepository.save(movimentacao);
        }

    }


    @Transactional(rollbackFor = Exception.class)
    public void cadastrar(final Movimentacao movimentacao){

        if (movimentacao.getCondutor() == null){
            throw new RuntimeException(" Condutor inválido");
        } else if (movimentacao.getVeiculo() == null) {
            throw new RuntimeException(" Veiculo inválido");
        } else if (movimentacao.getEntrada() == null) {
            throw new RuntimeException(" Entrada inválido");
        } else{
            movimentacaoRepository.save(movimentacao);
        }

    }



    @Transactional(rollbackFor = Exception.class)
    public void delete( @RequestParam("id") final Long id) {


         Movimentacao movimentacao = this.movimentacaoRepository.findById(id).orElse(null);

        if(movimentacao == null){
            throw new RuntimeException("movimentação nula");

        }else {
            movimentacao.setAtivo(false);
            movimentacaoRepository.save(movimentacao);
        }

    }

}
