package br.com.uniametica.estacionamento.service;

import br.com.uniametica.estacionamento.entity.Marca;
import br.com.uniametica.estacionamento.entity.Modelo;
import br.com.uniametica.estacionamento.repository.MarcaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Service

public class MarcaService {
    @Autowired
    private MarcaRepository marcaRepository;

    public Optional<Marca> procurar(Long id){

        if (!marcaRepository.idExistente(id) ){
            throw new RuntimeException("ID inválido - Motivo: Nao Existe no Banco de Dados");
        }else {
            Optional<Marca> marca = this.marcaRepository.findById(id);
            return marca;
        }

    }

    public List<Marca> procurarLista(){

        List<Marca> marca = marcaRepository.findAll();
        return marca;
    }

    public List<Marca> procurarAtivo(){

        List<Marca> marca = marcaRepository.findByAtivoTrue();
        return marca;
    }


    @Transactional(rollbackFor = Exception.class)
    public void editar(@RequestParam("id") final Long id, @RequestBody final  Marca marca) {

        final Marca marcabanco = this.marcaRepository.findById(id).orElse(null);

        if (marcabanco == null || !marca.getId().equals(marcabanco.getId())) {
            throw new RuntimeException("Não foi possivel identificar o registro informado");
        } else if (!marca.getNome().matches("[a-zA-Z]{2,50}")){
            throw new RuntimeException("Nome inválido");
        } else if (marcaRepository.NomeMarcaExistente(marca.getNome())) {
            throw new RuntimeException("Nome Repetido");
        } else{
            marcaRepository.save(marca);
        }

    }

    @Transactional(rollbackFor = Exception.class)
    public void cadastrarMarca(final Marca marca){

        if(marca.getNome() == null) {
            throw new RuntimeException("Nome inválido");
        } else if (marcaRepository.marcaIdExistentes(marca.getId())) {
            throw new RuntimeException(" Id da Marca já existe");
        }else if (marcaRepository.NomeMarcaExistente(marca.getNome())) {
            throw new RuntimeException(" Nome da Marca já existe");
        } else{
            marcaRepository.save(marca);
        }

    }



    @Transactional(rollbackFor = Exception.class)
    public void delete( @RequestParam("id") final Long id) {


        Marca marca = this.marcaRepository.findById(id).orElse(null);

        if(marcaRepository.marcaIdExistentes(marca.getId())){
            marca.setAtivo(false);
            marcaRepository.save(marca);
        }else {
            marcaRepository.delete(marca);
        }



    }








}
