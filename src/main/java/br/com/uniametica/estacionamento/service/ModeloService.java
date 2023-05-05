package br.com.uniametica.estacionamento.service;

import br.com.uniametica.estacionamento.entity.Modelo;
import br.com.uniametica.estacionamento.entity.Veiculo;
import br.com.uniametica.estacionamento.repository.ModeloRepository;
import br.com.uniametica.estacionamento.repository.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.beans.Transient;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Service

public class ModeloService {
    @Autowired
    private ModeloRepository modeloRepository;
    @Autowired
    private VeiculoRepository veiculoRepository;

    @Transactional(rollbackFor = Exception.class)
    public void cadastrar(@RequestBody final Modelo modelo){

        if(modelo.getNome() == null){
            throw new RuntimeException("Nome inválido");
        }
        else if(modelo.getMarca() == null){
            throw new RuntimeException("Marca inválido");
        }
        if(modelo.getCadastro() == null){
            throw new RuntimeException("Data Cadastro Invalido");
        }
        else{
            modeloRepository.save(modelo);
        }

    }

    @Transactional(rollbackFor = Exception.class)
    public void delete( @RequestParam("id") final Long id) {

            Modelo modelo = this.modeloRepository.findById(id).orElse(null);
            AtomicBoolean var = new AtomicBoolean(false);
            AtomicBoolean exclui = new AtomicBoolean(false);

            final List<Veiculo> veiculo = this.veiculoRepository.findAll();

            veiculo.forEach(i -> {
                if (id == i.getModelo().getId()) {
                    var.set(true);
                } else if (id != i.getModelo().getId() && modelo != null) {
                    exclui.set(true);
                }

            });

            if (var.get() == true) {
                modelo.setAtivo(false);
                modeloRepository.save(modelo);
                throw new RuntimeException("Registro desativado com sucesso!");
            } else if (exclui.get() == true) {
                modeloRepository.delete(modelo);
                throw new RuntimeException("Registro deletado com sucesso");

            } else {
                throw new RuntimeException("Id invalido");
            }


        }

    @Transactional(rollbackFor = Exception.class)
    public void editar(@RequestParam("id") final Long id, @RequestBody final  Modelo modelo) {

            final Modelo modelobanco = this.modeloRepository.findById(id).orElse(null);

            if (modelobanco == null || !modelo.getId().equals(modelobanco.getId())) {
                throw new RuntimeException("Não foi possivel identificar o registro informado");
            } else if (!modelo.getNome().matches("[a-zA-Z]{2,50}")){
                throw new RuntimeException("Nome inválido");
            } else if (modeloRepository.existente(modelo.getNome())) {
                throw new RuntimeException("Nome Repetido");
            } else if(modelo.getMarca() == null){
                throw new RuntimeException("Marca inválido");
            } else{
                modeloRepository.save(modelo);
            }

        }




    }


