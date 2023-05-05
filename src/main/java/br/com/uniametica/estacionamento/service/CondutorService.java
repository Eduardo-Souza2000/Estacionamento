package br.com.uniametica.estacionamento.service;

import br.com.uniametica.estacionamento.entity.Condutor;
import br.com.uniametica.estacionamento.entity.Modelo;
import br.com.uniametica.estacionamento.repository.CondutorRepository;
import br.com.uniametica.estacionamento.repository.MovimentacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class CondutorService {/*
    @Autowired
    private CondutorRepository condutorRepository;

    @Autowired
    private MovimentacaoRepository movimentacaoRepository;



    @Transactional(rollbackFor = Exception.class)
    public void cadastrar(@RequestBody final Condutor condutor){

        if(condutor.getNome() == null){
            throw new RuntimeException("Nome inválido");
        }
        else if(condutor.getCpf() == null){
            throw new RuntimeException("Marca inválido");
        }
        if(condutor.getCadastro() == null){
            throw new RuntimeException("Data Cadastro Invalido");
        }
        else{
            condutorRepository.save(condutor);
        }

    }
*/

}
