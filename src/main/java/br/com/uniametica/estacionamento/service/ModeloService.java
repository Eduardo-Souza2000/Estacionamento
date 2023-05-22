package br.com.uniametica.estacionamento.service;

import br.com.uniametica.estacionamento.entity.Condutor;
import br.com.uniametica.estacionamento.entity.Modelo;
import br.com.uniametica.estacionamento.entity.Veiculo;
import br.com.uniametica.estacionamento.repository.ModeloRepository;
import br.com.uniametica.estacionamento.repository.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@Service

public class ModeloService {
    @Autowired
    private ModeloRepository modeloRepository;



    public Optional<Modelo> procurar(Long id){

        if (!modeloRepository.ProcuraId(id) ){
            throw new RuntimeException("ID inválido - Motivo: Nao Existe no Banco de Dados");
        }else {
            Optional<Modelo> modelo = this.modeloRepository.findById(id);
            return modelo;
        }

    }

    public List<Modelo> procurarLista(){

        List<Modelo> modelo = modeloRepository.findAll();
        return modelo;
    }

    public List<Modelo> procurarAtivo(){

        List<Modelo> modelo = modeloRepository.findByAtivoTrue();
        return modelo;
    }



    @Transactional(rollbackFor = Exception.class)
    public void cadastrarModelo(final Modelo modelo){


        if (!modelo.getNome().matches("^[a-zA-Z0-1]{1}[a-zA-Z0-1\s]{0,48}$")){
            throw new RuntimeException(" Nome inválido favor verificar como escreveu o nome se esta correto");
        }
         if(modelo.getMarca() == null){
            throw new RuntimeException("Marca inválido");
        }
         if (modeloRepository.existente(modelo.getNome())){
             throw new RuntimeException("Nome Repetido");
         }


        modeloRepository.save(modelo);

    }


    @Transactional(rollbackFor = Exception.class)
    public void delete( @RequestParam("id") final Long id) {


        Modelo modelo = this.modeloRepository.findById(id).orElse(null);


        if(modeloRepository.modeloExistente(modelo.getId())){
            modelo.setAtivo(false);
            modeloRepository.save(modelo);
        }else {
            modeloRepository.delete(modelo);
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


