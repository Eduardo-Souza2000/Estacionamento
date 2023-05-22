package br.com.uniametica.estacionamento.service;

import br.com.uniametica.estacionamento.entity.Condutor;
import br.com.uniametica.estacionamento.entity.Configuracao;
import br.com.uniametica.estacionamento.entity.Movimentacao;
import br.com.uniametica.estacionamento.entity.Veiculo;
import br.com.uniametica.estacionamento.repository.*;
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
import java.time.format.DateTimeFormatter;
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

    private Movimentacao movimentacao;
    private Configuracao configuracao;
    private Condutor condutor;
    @Autowired
    private MarcaRepository marcaRepository;
    @Autowired
    private VeiculoRepository veiculoRepository;
    @Autowired
    private ModeloRepository modeloRepository;

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

        if(!movimentacaoRepository.ProcuraId(movimentacao.getId())) {
            throw new RuntimeException("FAVOR INSERIR UMA MOVIMENTAÇÃO VÁLIDA");
        }else if(movimentacao.getSaida() == null){
            throw new RuntimeException("FAVOR INSERIR A DATA DE SAIDA");
        }else if (movimentacao.getSaida() != null && movimentacao.getEntrada().isAfter(movimentacao.getSaida())){
            throw new RuntimeException(" A entrada deve ser antes da saida");
        }else if (movimentacao.getCondutor() == null){
            throw new RuntimeException("Condutor Nulo");
        }else if(!veiculoRepository.ProcuraId(movimentacao.getVeiculo().getId())){
            throw new RuntimeException("Veiculo Não Existe No Banco de Dados");
        }else if (veiculoRepository.veiculoExistente(movimentacao.getVeiculo().getId()) && movimentacao.getSaida() == null){
            throw new RuntimeException("Veiculo já está estacionado.");
        } else if (!veiculoRepository.getById(movimentacao.getVeiculo().getId()).isAtivo()) {
            throw new RuntimeException("Veiculo inativo");
        }else if(!modeloRepository.modeloExistente(movimentacao.getVeiculo().getModelo().getId())){
            throw new RuntimeException("Modelo Não Existe No Banco de Dados");
        }else if (!marcaRepository.marcaIdExistentes(movimentacao.getVeiculo().getModelo().getMarca().getId())){
            throw new RuntimeException("Marca Não Existe No Banco de Dados");
        } else if (marcaRepository.NomeMarcaExistente(String.valueOf(movimentacao.getVeiculo().getModelo().getMarca().getNome().matches("[a-zA-Z]{2,50}")))){
            throw new RuntimeException("Nome da Marca Invalido");
        } else if(!condutorRepository.idExistente(movimentacao.getCondutor().getId())){
            throw new RuntimeException("Condutor Não Existe No Banco de Dados");
        } else if (!condutorRepository.getById(movimentacao.getCondutor().getId()).isAtivo()) {
            throw new RuntimeException("Condutor inativo.");
        } else{
            movimentacaoRepository.save(movimentacao);
        }

    }





    @Transactional(rollbackFor = Exception.class)
    public void cadastrar(final Movimentacao movimentacao){

        if (movimentacao.getCondutor() == null){
            throw new RuntimeException("Condutor Nulo");
        }else if(!veiculoRepository.ProcuraId(movimentacao.getVeiculo().getId())){
            throw new RuntimeException("Veiculo Não Existe No Banco de Dados");
        }else if (veiculoRepository.veiculoExistente(movimentacao.getVeiculo().getId()) && movimentacao.getSaida() == null){
            throw new RuntimeException("Veiculo já está estacionado.");
        } else if (!veiculoRepository.getById(movimentacao.getVeiculo().getId()).isAtivo()) {
            throw new RuntimeException("Veiculo inativo");
        }else if(!modeloRepository.modeloExistente(movimentacao.getVeiculo().getModelo().getId())){
            throw new RuntimeException("Modelo Não Existe No Banco de Dados");
        }else if (!marcaRepository.marcaIdExistentes(movimentacao.getVeiculo().getModelo().getMarca().getId())){
            throw new RuntimeException("Marca Não Existe No Banco de Dados");
        }  else if(!condutorRepository.idExistente(movimentacao.getCondutor().getId())){
            throw new RuntimeException("Condutor Não Existe No Banco de Dados");
        } else if (!condutorRepository.getById(movimentacao.getCondutor().getId()).isAtivo()) {
            throw new RuntimeException("Condutor inativo.");
        } else if (movimentacao.getEntrada() == null) {
            throw new RuntimeException("Data de Entrada Nula.");
        } else {
            movimentacaoRepository.save(movimentacao);
        }
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

    Configuracao objConfiguracao = movimentacaoRepository.obterConfiguracao();


    //Condutor objCondutor = movimentacaoRepository.obterCondutor();

    //CHEGAGENS
    if(!movimentacaoRepository.ProcuraId(movimentacao.getId())) {
            throw new RuntimeException("FAVOR INSERIR UMA MOVIMENTAÇÃO VÁLIDA");
    }else if(movimentacao.getSaida() == null){
            throw new RuntimeException("FAVOR INSERIR A DATA DE SAIDA");
    }else if (movimentacao.getSaida() != null && movimentacao.getEntrada().isAfter(movimentacao.getSaida())){
        throw new RuntimeException(" A entrada deve ser antes da saida");
    }else if (movimentacao.getCondutor() == null){
        throw new RuntimeException("Condutor Nulo");
    }else if(!veiculoRepository.ProcuraId(movimentacao.getVeiculo().getId())){
        throw new RuntimeException("Veiculo Não Existe No Banco de Dados");
    }else if (veiculoRepository.veiculoExistente(movimentacao.getVeiculo().getId()) && movimentacao.getSaida() == null){
        throw new RuntimeException("Veiculo já está estacionado.");
    } else if (!veiculoRepository.getById(movimentacao.getVeiculo().getId()).isAtivo()) {
        throw new RuntimeException("Veiculo inativo");
    }else if(!modeloRepository.modeloExistente(movimentacao.getVeiculo().getModelo().getId())){
        throw new RuntimeException("Modelo Não Existe No Banco de Dados");
    }else if (!marcaRepository.marcaIdExistentes(movimentacao.getVeiculo().getModelo().getMarca().getId())){
        throw new RuntimeException("Marca Não Existe No Banco de Dados");
    } else if (marcaRepository.NomeMarcaExistente(String.valueOf(movimentacao.getVeiculo().getModelo().getMarca().getNome().matches("[a-zA-Z]{2,50}")))){
        throw new RuntimeException("Nome da Marca Invalido");
    } else if(!condutorRepository.idExistente(movimentacao.getCondutor().getId())){
        throw new RuntimeException("Condutor Não Existe No Banco de Dados");
    } else if (!condutorRepository.getById(movimentacao.getCondutor().getId()).isAtivo()) {
        throw new RuntimeException("Condutor inativo.");
    }else {


        //TEMPO DENTRO DO ESTACIONAMENTO

        int tempoMulta = calculaMulta(configuracaoRepository.getById(Long.valueOf(1)), movimentacao);
        int calculaTempo = calculaTempo(movimentacao);
        int calculatempoCondutor = calculaTempo(movimentacao);
        LocalDateTime horaAtual = LocalDateTime.now();



        movimentacao.setTempoTotalhora(calculaTempo);
        movimentacao.setTempoTotalminuto(calculaTempo * 60);
        movimentacao.setTempoMultaHora(tempoMulta / 60);
        movimentacao.setTempoMultaMinuto(tempoMulta);


        movimentacao.setValorHoraMulta(objConfiguracao.getValorMinutoMulta());
        movimentacao.setValorHora(objConfiguracao.getValorHora());
        movimentacao.setHoraAtual(horaAtual);


        movimentacao.setValorMulta(BigDecimal.valueOf((tempoMulta * objConfiguracao.getValorMinutoMulta().intValue())));

        //(calculaTempo * objConfiguracao.getValorHora().intValue())

//    calculatempoCondutor =+ calculatempoCondutor;
//    movimentacao.getCondutor().setTempototal(calculatempoCondutor);
//
//   objCondutor.setTempototal(calculatempoCondutor);


//    BigDecimal valorHora = configuracaoRepository.getReferenceById(id).getValorHora();
//    BigDecimal valorMinutoMulta = configuracaoRepository.getReferenceById(id).getValorMinutoMulta();

//    //movimentacao.setValorTotal(BigDecimal.valueOf(movimentacao.getTempoTotalhora() * valorHora + movimentacao.getTempoMultaMinuto() * valorMinutoMulta));
//    movimentacao.setValorTotal(BigDecimal.valueOf(movimentacao.getTempoTotalhora() + valorHora.intValue()).add(BigDecimal.valueOf(movimentacao.getTempoMultaMinuto() + valorMinutoMulta.intValue())));


                                            //TEMPO DO CONDUTOR DO ESTACIONAMENTO

        //CONVERTE UM VALOR LONG EM INT
        int condutorExistente = Math.toIntExact(movimentacao.getCondutor().getId());

        //PESQUISA O NUMERO DO ID CONFORME FOI PASSADO ANTERIORMENTE
        Condutor condutorBanco = condutorRepository.getById((long) condutorExistente);

        //CALCULO DO TEMPO ELE PUXA O TEMPO TOTAL DO BANCO + CALCULO DO RETORNO DE CALCULA TEMPO CONDUTOR
        int tempoNovo = condutorBanco.getTempototal() + calculatempoCondutor;

        //CALCULO DO TEMPO ELE PUXA O TEMPO TOTAL DO BANCO + CALCULO DO RETORNO DE CALCULA TEMPO CONDUTOR
        int tempoNovoPago = condutorBanco.getTempoPago() + calculatempoCondutor;

        condutorBanco.setTempototal(tempoNovo);
        condutorBanco.setTempoPago(tempoNovoPago);
        movimentacao.getCondutor().setTempototal(condutorBanco.getTempototal());


                                                    //TEMPO DE DESCONTO

        if(condutorBanco.getTempoDesconto() >= 5) {
            condutorBanco.setTempoDesconto(0);
            movimentacao.getCondutor().setTempoDesconto(condutorBanco.getTempoDesconto());
        }

        if (condutorBanco.getTempoPago() > 50) {
            //CALCULA O VALOR DO DESCONTO
            condutorBanco.setTempoDesconto((condutorBanco.getTempoPago() / 50 * 5) + condutorBanco.getTempoDesconto());

            //CALCULA O TANTO QUE SOBROU DO CONDUTOR PARA DEIXAR ARMAZENADO PARA AS PROXIMAS 50 HORAS PARA DESCONTO
            condutorBanco.setTempoPago(condutorBanco.getTempoPago() % 50);
        }


        movimentacao.setValorDesconto(BigDecimal.valueOf(condutorBanco.getTempoDesconto() * objConfiguracao.getValorHora().intValue()));
        movimentacao.getCondutor().setTempoDesconto(condutorBanco.getTempoDesconto());
        movimentacao.getCondutor().setTempoPago(condutorBanco.getTempoPago());
        movimentacao.setTempoDesconto(condutorBanco.getTempoDesconto());

        movimentacao.setValorTotal(BigDecimal.valueOf(( calculaTempo - (tempoMulta/60)) * objConfiguracao.getValorHora().intValue() + (tempoMulta * objConfiguracao.getValorMinutoMulta().intValue()) - (movimentacao.getTempoDesconto() * objConfiguracao.getValorHora().intValue())));




    condutorRepository.save(condutorBanco);

    movimentacaoRepository.save(movimentacao);


    }

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


public int calculaTempoComDesconto(Movimentacao movimentacao, Configuracao configuracao){

    LocalDateTime entrada = movimentacao.getEntrada();
    LocalDateTime saida = movimentacao.getSaida();
    LocalTime inicioExpediente = configuracao.getInicioExpediente();
    LocalTime fimExpediente = configuracao.getFimExpediente();
    int tempoDesconto = 0;
    int AnoEntrada = entrada.getYear();
    int saidaAno = saida.getYear();
    int totalDias = 0;

    if(AnoEntrada != saidaAno){
        totalDias += saidaAno - AnoEntrada;
    } else{
        totalDias += saida.getDayOfYear() - entrada.getDayOfYear();
    }
    if (  inicioExpediente.isAfter(entrada.toLocalTime())){
        tempoDesconto += ((int) Duration.between(entrada.toLocalTime(), inicioExpediente).toMinutes());
    }
    if (fimExpediente.isBefore(saida.toLocalTime()))
    {
        tempoDesconto += ((int) Duration.between(fimExpediente, saida.toLocalTime()).toMinutes());

    }
    if (totalDias > 0){
        int diferenca = ((int) Duration.between(inicioExpediente, fimExpediente).toMinutes());//getSeconds()/60);
        tempoDesconto +=   (totalDias * 24 * 60 ) - (diferenca + movimentacao.getCondutor().getTempoDesconto());

    }


    return tempoDesconto;
}

}
