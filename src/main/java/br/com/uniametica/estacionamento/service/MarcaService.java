package br.com.uniametica.estacionamento.service;

import br.com.uniametica.estacionamento.entity.Marca;
import br.com.uniametica.estacionamento.entity.Modelo;
import br.com.uniametica.estacionamento.repository.MarcaRepository;
import br.com.uniametica.estacionamento.repository.ModeloRepository;
import br.com.uniametica.estacionamento.repository.VeiculoRepository;
import jakarta.persistence.EntityNotFoundException;
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
    private Modelo modelo;
    @Autowired
    private ModeloRepository modeloRepository;
    @Autowired
    private VeiculoRepository veiculoRepository;

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
    public void editar(@RequestParam("id")  Long id, @RequestBody  Marca marca) {

        final Marca marcabanco = this.marcaRepository.findById(id).orElseThrow(()-> {
            throw new EntityNotFoundException("Nao foi encontrado o ID no Banco");
        });
        if (!marca.getId().equals(id)){
            throw new RuntimeException("Não foi possivel identificar o registro informado pois o ID não confere");
        } else if (!marcaRepository.idExistente(id)) {
            throw new RuntimeException(" Id da Marca Não existe");
        }else if (!marca.getNome().matches("[a-zA-Z]{2,50}")){
            throw new RuntimeException("Nome inválido");
        }else if (marcaRepository.NomeMarcaExistente(marca.getNome())) {
            throw new RuntimeException("Marca já existe");
        }else {
            marcaRepository.save(marca);
        }

    }

    @Transactional(rollbackFor = Exception.class)
    public void cadastrarMarca( Marca marca){

        if(marca.getNome() == null) {
            throw new RuntimeException("Nome Nulo");
        }else if (!marca.getNome().matches("[a-zA-Z]{2,50}")){
            throw new RuntimeException("Nome inválido");
        }else if (marcaRepository.NomeMarcaExistente(marca.getNome())) {
            throw new RuntimeException("Marca já existe");
        }else {
            marcaRepository.save(marca);
        }

    }



    @Transactional(rollbackFor = Exception.class)
    public void delete( @RequestParam("id")  Long id) {

        Marca marca = this.marcaRepository.findById(id).orElse(null);

        if(id == null){
            throw new RuntimeException(" Id da Marca Invalido");
        } else if (!marcaRepository.idExistente(id)) {
            throw new RuntimeException(" Marca nao existe no banco");
        } else if(marcaRepository.marcaIdExistentes(id)){
            marca.setAtivo(false);
            marcaRepository.save(marca);
        }else {
            marcaRepository.delete(marca);
        }



    }








}
