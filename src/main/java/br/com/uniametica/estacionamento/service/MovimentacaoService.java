package br.com.uniametica.estacionamento.service;

import br.com.uniametica.estacionamento.entity.Configuracao;
import br.com.uniametica.estacionamento.entity.Movimentacao;
import br.com.uniametica.estacionamento.entity.Veiculo;
import br.com.uniametica.estacionamento.repository.ConfiguracaoRepository;
import br.com.uniametica.estacionamento.repository.MovimentacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service

public class MovimentacaoService {
    @Autowired
    private MovimentacaoRepository movimentacaoRepository;
    @Autowired
    private ConfiguracaoRepository configuracaoRepository;


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
    public void editarMovimentacao(final Long id, final  Movimentacao movimentacao) {

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



       int tempoMulta = calculaMulta(configuracaoRepository.getById(Long.valueOf(1)),movimentacao);
       int calculaTempo = calculaTempo(movimentacao);

        if(tempoMulta % 60 != 0)
            tempoMulta+=60;


        movimentacao.setTempoMultaHora(tempoMulta/60);
        movimentacao.setTempoMultaMinuto(tempoMulta);

        movimentacao.setTempoTotalhora(calculaTempo/60);
        movimentacao.setTempoTotalminuto(calculaTempo);




        movimentacaoRepository.save(movimentacao);



    }

    private int calculaMulta(final Configuracao configuracao, final Movimentacao movimentacao){

        LocalDateTime entrada = movimentacao.getEntrada();
        LocalDateTime saida = movimentacao.getSaida();
        LocalTime inicioExpediente = configuracao.getInicioExpediente();
        LocalTime fimExpediente = configuracao.getFimExpediente();
        int minuto = 0;

        if (inicioExpediente.isAfter(entrada.toLocalTime())){
             minuto = ((int) Duration.between(entrada.toLocalTime(),inicioExpediente).getSeconds()) /60;
        }
        if (fimExpediente.isBefore(saida.toLocalTime())){
            minuto += ((int) Duration.between(fimExpediente,saida.toLocalTime()).getSeconds()) / 60;
        }

        return minuto;
    }

    private int calculaTempo (final Movimentacao movimentacao){
        int tempo=0;
        LocalDateTime tempoEntrada = movimentacao.getEntrada();
        LocalDateTime tempoSaida = movimentacao.getSaida();

        tempo += ((int) Duration.between(tempoEntrada.toLocalTime(),tempoSaida.toLocalTime()).getSeconds() / 60);
        //int entrada = Duration.ofSeconds((Long.valueOf(tempoEntrada.toEpochSecond())));

        return tempo;

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
