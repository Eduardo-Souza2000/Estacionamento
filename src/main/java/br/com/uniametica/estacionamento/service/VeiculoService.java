package br.com.uniametica.estacionamento.service;

import br.com.uniametica.estacionamento.entity.Marca;
import br.com.uniametica.estacionamento.entity.Veiculo;
import br.com.uniametica.estacionamento.repository.VeiculoRepository;
import jakarta.validation.constraints.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Service

public class VeiculoService {
    @Autowired
    VeiculoRepository veiculoRepository;


    public Optional<Veiculo> procurarVeiculo(Long id){

        if (!veiculoRepository.ProcuraId(id) ){
            throw new RuntimeException("ID inválido - Motivo: Nao Existe no Banco de Dados");
        }else {
            Optional<Veiculo> veiculo = this.veiculoRepository.findById(id);
            return veiculo;
        }

    }

    public List<Veiculo> procurarLista(){

        List<Veiculo> veiculo = veiculoRepository.findAll();
        return veiculo;
    }


    public List<Veiculo> procurarAtivo(){

        List<Veiculo> veiculo = veiculoRepository.findByAtivoTrue();
        return veiculo;
    }


    @Transactional(rollbackFor = Exception.class)
    public void editarVeiculo(@RequestParam("id") final Long id, @RequestBody final  Veiculo veiculo) {

        final Veiculo veiculobanco = this.veiculoRepository.findById(id).orElse(null);

        if (veiculobanco == null || !veiculo.getId().equals(veiculobanco.getId())) {
            throw new RuntimeException("Não foi possivel identificar o registro informado");
        } else if (veiculo.getPlaca() == null){
            throw new RuntimeException("Placa inválido");
        } else if (veiculo.getModelo() == null){
            throw new RuntimeException("modelo inválido");
        } else if (veiculo.getCor() == null) {
            throw new RuntimeException("Cor inválido");
        } else if (veiculo.getTipo() == null) {
            throw new RuntimeException("Tipo inválido");
        }else if (veiculo.getAno() == null) {
            throw new RuntimeException("Ano inválido");
        }else{
            veiculoRepository.save(veiculo);
        }

    }


    @Transactional(rollbackFor = Exception.class)
    public void cadastrar(final Veiculo veiculo){

        if (veiculo.getPlaca() == null){
            throw new RuntimeException("Placa inválido");
        } else if (veiculo.getModelo() == null){
            throw new RuntimeException("modelo inválido");
        } else if (veiculo.getCor() == null) {
            throw new RuntimeException("Cor inválido");
        } else if (veiculo.getTipo() == null) {
            throw new RuntimeException("Tipo inválido");
        }else if (veiculo.getAno() == null) {
            throw new RuntimeException("Ano inválido");
        }else{
            veiculoRepository.save(veiculo);
        }

    }



    @Transactional(rollbackFor = Exception.class)
    public void delete( @RequestParam("id") final Long id) {


        Veiculo veiculo = this.veiculoRepository.findById(id).orElse(null);

        if(veiculoRepository.veiculoIdExistentes(veiculo.getId())){
            veiculo.setAtivo(false);
            veiculoRepository.save(veiculo);
        }else {
            veiculoRepository.delete(veiculo);
        }



    }






}
