package br.com.uniametica.estacionamento.service;

import br.com.uniametica.estacionamento.entity.Configuracao;
import br.com.uniametica.estacionamento.entity.Movimentacao;
import br.com.uniametica.estacionamento.repository.ConfiguracaoRepository;
import jakarta.persistence.Access;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Service

public class ConfiguracaoService {
    @Autowired
    private ConfiguracaoRepository configuracaoRepository;

    public Optional<Configuracao> procuraconfiguracao(Long id){

        if (!configuracaoRepository.ProcuraId(id) ){
            throw new RuntimeException("ID inválido - Motivo: Nao Existe no Banco de Dados");
        }else {
            Optional<Configuracao> configuracao = this.configuracaoRepository.findById(id);
            return configuracao;
        }

    }

    @Transactional(rollbackFor = Exception.class)
    public void editarMovimentacao(@RequestParam("id") final Long id, @RequestBody final  Configuracao configuracao) {

        final Configuracao configuracaobanco = this.configuracaoRepository.findById(id).orElse(null);
        if (configuracaobanco == null || !configuracao.getId().equals(configuracaobanco.getId())) {
            throw new RuntimeException("Não foi possivel identificar o registro informado");
        } else if (configuracao.getFimExpediente() == null){
            throw new RuntimeException(" Fim do expediente inválido");
        } else if (configuracao.getInicioExpediente() == null) {
            throw new RuntimeException(" Inicio do expediente inválido");
        } else if (configuracao.getTempoDeDesconto() == null){
            throw new RuntimeException(" Tempo de desconto inválido");
        } else if (configuracao.getValorHora() == null) {
            throw new RuntimeException(" Valor da Hora inválido");
        } else if (configuracao.getTempoParaDesconto() == null){
            throw new RuntimeException(" Tempo para desconto inválido");
        } else if (configuracao.getValorMinutoMulta() == null) {
            throw new RuntimeException(" Valor minuto Multa inválido");
        } else {
            configuracaoRepository.save(configuracao);
        }


    }






    @Transactional(rollbackFor = Exception.class)
    public void cadastrar(final Configuracao configuracao){

        if (configuracao.getFimExpediente() == null){
            throw new RuntimeException(" Fim do expediente inválido");
        } else if (configuracao.getInicioExpediente() == null) {
            throw new RuntimeException(" Inicio do expediente inválido");
        } else if (configuracao.getTempoDeDesconto() == null){
            throw new RuntimeException(" Tempo de desconto inválido");
        } else if (configuracao.getValorHora() == null) {
            throw new RuntimeException(" Valor da Hora inválido");
        } else if (configuracao.getTempoParaDesconto() == null){
            throw new RuntimeException(" Tempo para desconto inválido");
        } else if (configuracao.getValorMinutoMulta() == null) {
            throw new RuntimeException(" Valor minuto Multa inválido");
        } else {
            configuracaoRepository.save(configuracao);
        }


    }





    }
