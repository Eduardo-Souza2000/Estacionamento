package br.com.uniametica.estacionamento.service;

import br.com.uniametica.estacionamento.entity.Condutor;
import br.com.uniametica.estacionamento.entity.Configuracao;
import br.com.uniametica.estacionamento.entity.Movimentacao;
import br.com.uniametica.estacionamento.entity.Veiculo;
import br.com.uniametica.estacionamento.repository.CondutorRepository;
import br.com.uniametica.estacionamento.repository.ConfiguracaoRepository;
import br.com.uniametica.estacionamento.repository.MovimentacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service

public class MovimentacaoService {
    @Autowired
    private MovimentacaoRepository movimentacaoRepository;
    @Autowired
    private ConfiguracaoRepository configuracaoRepository;
    @Autowired
    private CondutorRepository condutorRepository;

    private Condutor condutor;
    private Movimentacao movimentacao;
    private Configuracao configuracao;

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
        }
        if (movimentacao.getCondutor() == null){
            throw new RuntimeException(" Condutor inválido");
        }
        if (movimentacao.getVeiculo() == null) {
            throw new RuntimeException(" Veiculo inválido");
        }
        if (movimentacao.getEntrada() == null) {
            throw new RuntimeException(" Entrada inválido");
        }


        movimentacaoRepository.save(movimentacao);
    }





    @Transactional(rollbackFor = Exception.class)
    public void cadastrar(final Movimentacao movimentacao){

        if (movimentacao.getCondutor() == null){
            throw new RuntimeException(" Condutor inválido");
        }
        if (movimentacao.getVeiculo() == null) {
            throw new RuntimeException(" Veiculo inválido");
        }
        if (movimentacao.getEntrada() == null) {
            throw new RuntimeException(" Entrada inválido");
        }

        movimentacaoRepository.save(movimentacao);
    }


    @Transactional(rollbackFor = Exception.class)
    public void delete( @RequestParam("id") final Long id) {


         Movimentacao movimentacao = this.movimentacaoRepository.findById(id).orElse(null);

        if(movimentacao == null){
            throw new RuntimeException("movimentação nula");
        }

        movimentacao.setAtivo(false);
        movimentacaoRepository.save(movimentacao);


    }


    @Transactional(rollbackFor = Exception.class)
    public String finalizarMovimentacao (@RequestParam("id") Long id, Movimentacao movimentacao){

    if(!movimentacaoRepository.ProcuraId(movimentacao.getId())) {
            throw new RuntimeException("FAVOR INSERIR UM ID VALIDO");
    }

    if(movimentacao.getSaida() == null){
            throw new RuntimeException("FAVOR INSERIR A DATA DE SAIDA");
    }


    int tempoMulta = calculaMulta(configuracaoRepository.getById(Long.valueOf(1)),movimentacao);
    int calculaTempo = calculaTempo(movimentacao);
    int calculatempoCondutor = calculaTempo(movimentacao);

    // if(tempoMulta % 60 != 0)
    //     tempoMulta+=60;



    movimentacao.setTempoTotalhora(calculaTempo);
    movimentacao.setTempoTotalminuto(calculaTempo*60);
    movimentacao.setTempoMultaHora(tempoMulta/60);
    movimentacao.setTempoMultaMinuto(tempoMulta);
    movimentacao.setValorHoraMulta(configuracao.getValorMinutoMulta());
    movimentacao.setValorHora(configuracao.getValorHora());


    calculatempoCondutor =+ calculatempoCondutor;
    movimentacao.getCondutor().setTempototal(calculatempoCondutor);

    if (movimentacao.getCondutor().getTempototal() > 50){
        movimentacao.setTempoDesconto((movimentacao.getCondutor().getTempototal()/50) * 5);
    }



//    BigDecimal valorHora = configuracaoRepository.getReferenceById(id).getValorHora();
//    BigDecimal valorMinutoMulta = configuracaoRepository.getReferenceById(id).getValorMinutoMulta();

//    //movimentacao.setValorTotal(BigDecimal.valueOf(movimentacao.getTempoTotalhora() * valorHora + movimentacao.getTempoMultaMinuto() * valorMinutoMulta));
//    movimentacao.setValorTotal(BigDecimal.valueOf(movimentacao.getTempoTotalhora() + valorHora.intValue()).add(BigDecimal.valueOf(movimentacao.getTempoMultaMinuto() + valorMinutoMulta.intValue())));

    movimentacao.setValorHora(configuracao.getValorHora());

    movimentacaoRepository.save(movimentacao);

    return movimentacao.toString();
}



    private int calculaMulta(final Configuracao configuracao, final Movimentacao movimentacao){


        LocalDateTime entrada = movimentacao.getEntrada();
        LocalDateTime saida = movimentacao.getSaida();
        LocalTime inicioExpediente = configuracao.getInicioExpediente();
        LocalTime fimExpediente = configuracao.getFimExpediente();
        int multa = 0;
        int AnoEntrada = entrada.getYear();
        int saidaAno = saida.getYear();
        int totalDias = 0;

        if(AnoEntrada != saidaAno){
            totalDias += saidaAno - AnoEntrada;
        } else{
            totalDias += saida.getDayOfYear() - entrada.getDayOfYear();
        }
        //if (entrada.toLocalTime().isBefore(inicioExpediente)){
        if (  inicioExpediente.isAfter(entrada.toLocalTime())){
            //multa += ((int) Duration.between(inicioExpediente,saida.toLocalTime()).getSeconds()/60);
            multa += ((int) Duration.between(entrada.toLocalTime(), inicioExpediente).toMinutes());
        }
//        if(saida.toLocalTime().isBefore(fimExpediente))
        if (fimExpediente.isBefore(saida.toLocalTime()))
        {
            //multa += ((int) Duration.between(fimExpediente,saida.toLocalTime()).getSeconds()) / 60 ;
            multa += ((int) Duration.between(fimExpediente, saida.toLocalTime()).toMinutes());

        }
        if (totalDias > 0){
            int diferenca = ((int) Duration.between(inicioExpediente, fimExpediente).toMinutes());//getSeconds()/60);
            multa +=   (totalDias * 24 * 60 ) - (diferenca);
            // multa =   (totalDias * 24 * 60 ); //- (diferenca * totalDias);
            //multa =    (diferenca * totalDias * 60);
            //multa = totalDias;
            //multa = diferenca;
        }



        return multa;
    }

    public int calculaTempo (final Movimentacao movimentacao){
        int tempo=0;
        LocalDateTime tempoEntrada = movimentacao.getEntrada();
        LocalDateTime tempoSaida = movimentacao.getSaida();

        tempo =  (int)  Duration.between(tempoEntrada,tempoSaida).getSeconds()/3600;


        return tempo;

    }




}
