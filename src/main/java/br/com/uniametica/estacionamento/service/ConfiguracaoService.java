package br.com.uniametica.estacionamento.service;

import br.com.uniametica.estacionamento.entity.*;
import br.com.uniametica.estacionamento.repository.ConfiguracaoRepository;
import jakarta.persistence.Access;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Service

public class ConfiguracaoService {
    @Autowired
    private ConfiguracaoRepository configuracaoRepository;



    public Optional<Configuracao> procuraconfiguracao(Long id){

        if (!configuracaoRepository.ProcuraConfiguracaoId(id) ){
            throw new RuntimeException("ID inválido - Motivo: Nao Existe no Banco de Dados");
        }else {
            Optional<Configuracao> configuracao = this.configuracaoRepository.findById(id);
            return configuracao;
        }

    }

    public List<Configuracao> procurarLista(){

        List<Configuracao> configuracao = configuracaoRepository.findAll();
        return configuracao;
    }



    @Transactional(rollbackFor = Exception.class)
    public void editarMovimentacao(@RequestParam("id")  Long id, @RequestBody  Configuracao configuracao) {

        final Configuracao configuracaobanco = this.configuracaoRepository.findById(id).orElse(null);
        if (configuracaobanco == null || !configuracao.getId().equals(configuracaobanco.getId())) {
            throw new RuntimeException("Não foi possivel identificar o registro informado");
        }
        if (configuracao.getId() == null){
            throw new RuntimeException(" ID Nulo");
        }
        if (configuracao.getFimExpediente() == null){
            throw new RuntimeException(" Fim do expediente inválido");
        }
        if (configuracao.getInicioExpediente() == null) {
            throw new RuntimeException(" Inicio do expediente inválido");
        }
        if (configuracao.getTempoDeDesconto() == null){
            throw new RuntimeException(" Tempo de desconto inválido");
        }
        if (configuracao.getValorHora() == null) {
            throw new RuntimeException(" Valor da Hora inválido");
        }
        if (configuracao.getTempoParaDesconto() == null){
            throw new RuntimeException(" Tempo para desconto inválido");
        }
        if (configuracao.getValorMinutoMulta() == null) {
            throw new RuntimeException(" Valor minuto Multa inválido");
        }

        configuracaoRepository.save(configuracao);

    }






    @Transactional(rollbackFor = Exception.class)
    public void cadastrar( Configuracao configuracao){

        if (configuracao.getFimExpediente() == null){
            throw new RuntimeException(" Fim do expediente inválido");
        }
        if (configuracao.getInicioExpediente() == null) {
            throw new RuntimeException(" Inicio do expediente inválido");
        }
        if (configuracao.getTempoDeDesconto() == null){
            throw new RuntimeException(" Tempo de desconto inválido");
        }
        if (configuracao.getValorHora() == null) {
            throw new RuntimeException(" Valor da Hora inválido");
        }
        if (configuracao.getTempoParaDesconto() == null){
            throw new RuntimeException(" Tempo para desconto inválido");
        }
        if (configuracao.getValorMinutoMulta() == null) {
            throw new RuntimeException(" Valor minuto Multa inválido");
        }

        configuracaoRepository.save(configuracao);
    }





    }
