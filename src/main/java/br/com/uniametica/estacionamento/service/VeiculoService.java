package br.com.uniametica.estacionamento.service;

import br.com.uniametica.estacionamento.entity.Marca;
import br.com.uniametica.estacionamento.entity.Veiculo;
import br.com.uniametica.estacionamento.repository.MarcaRepository;
import br.com.uniametica.estacionamento.repository.ModeloRepository;
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
    @Autowired
    private ModeloRepository modeloRepository;
    @Autowired
    private MarcaRepository marcaRepository;


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
    public void editarVeiculo(@RequestParam("id")  Long id, @RequestBody   Veiculo veiculo) {

        final Veiculo veiculobanco = this.veiculoRepository.findById(id).orElse(null);

        if (veiculobanco == null || !veiculo.getId().equals(veiculobanco.getId())) {
            throw new RuntimeException(" Não foi possivel identificar o registro informado");
        }
        if (veiculo.getPlaca() == null){
            throw new RuntimeException(" Favor Inserir uma PLaca Valida, pois e nula");
        }
        if (!veiculo.getPlaca().matches("[a-zA-Z]{3}-[0-9]{4}|[a-zA-Z]{3}[0-9]{4}" +
                "|[a-zA-Z]{3}[0-9][a-zA-Z][0-9]{2}|[a-zA-Z]{3}-[0-9][a-zA-Z][0-9]{2}")) {
            throw new RuntimeException(" Placa Invalida");
        }
        if (veiculo.getModelo() == null){
            throw new RuntimeException(" Favor Inserir modelo Valido, pois e nula");
        }
        if (veiculo.getCor() == null) {
            throw new RuntimeException(" Favor Inserir Cor Valida, pois e nula");
        }
        if (veiculo.getTipo() == null) {
            throw new RuntimeException(" Favor Inserir Tipo valido, pois e nula");
        }
        if (veiculo.getAno() == null) {
            throw new RuntimeException(" Favor Inserir Ano válido, pois e nula");
        }
        if(veiculo.getAtualizacao() == null) {
            throw new RuntimeException(" DATA DE ATUALIZAÇAO nula");
        }
        if (veiculo.getCadastro() == null){
            throw new RuntimeException(" DATA DE cadastro nula");
        }

        veiculoRepository.save(veiculo);
    }


    @Transactional(rollbackFor = Exception.class)
    public void cadastrar(final Veiculo veiculo){

        if (veiculo.getPlaca() == null){
            throw new RuntimeException("Placa Nula");
        }
        if (!veiculo.getPlaca().matches("[a-zA-Z]{3}-[0-9]{4}|[a-zA-Z]{3}[0-9]{4}" +
                "|[a-zA-Z]{3}[0-9][a-zA-Z][0-9]{2}|[a-zA-Z]{3}-[0-9][a-zA-Z][0-9]{2}")) {
            throw new RuntimeException(" Placa Invalida");
        }
        if (veiculoRepository.ProcuraPlaca(veiculo.getPlaca())){
            throw new RuntimeException("Placa ja existe no banco");
        }
        if (veiculo.getModelo() == null){
            throw new RuntimeException("modelo inválido");
        }
        if(veiculo.getModelo().getMarca() == null){
            throw new RuntimeException("Marca inválido");
        }
        if (!modeloRepository.modeloExistente(veiculo.getModelo().getId())){
            throw new RuntimeException("Modelo Nao existe no Banco de Dados");
        }
        if (!marcaRepository.marcaIdExistentes(veiculo.getModelo().getMarca().getId())){
            throw new RuntimeException("Marca Nao existe no Banco de Dados");
        }
        if (veiculo.getCor() == null) {
            throw new RuntimeException("Cor inválido");
        }
        if (veiculo.getTipo() == null) {
            throw new RuntimeException("Tipo inválido");
        }
        if (veiculo.getAno() == null) {
            throw new RuntimeException("Ano inválido");
        }

        veiculoRepository.save(veiculo);


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
