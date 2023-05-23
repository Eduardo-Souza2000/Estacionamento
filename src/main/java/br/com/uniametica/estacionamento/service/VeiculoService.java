package br.com.uniametica.estacionamento.service;

import br.com.uniametica.estacionamento.entity.Marca;
import br.com.uniametica.estacionamento.entity.Modelo;
import br.com.uniametica.estacionamento.entity.Movimentacao;
import br.com.uniametica.estacionamento.entity.Veiculo;
import br.com.uniametica.estacionamento.repository.MarcaRepository;
import br.com.uniametica.estacionamento.repository.ModeloRepository;
import br.com.uniametica.estacionamento.repository.MovimentacaoRepository;
import br.com.uniametica.estacionamento.repository.VeiculoRepository;
import jakarta.validation.constraints.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
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
    @Autowired
    private MovimentacaoRepository movimentacaoRepository;




    public Optional<Veiculo> procurarVeiculo(long id){

        if (!veiculoRepository.ProcuraId(id) ){
            throw new RuntimeException("ID inválido - Motivo: Nao Existe no Banco de Dados");
        } else {
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

        if (id == null) {
            throw new RuntimeException(" Não foi possivel identificar o registro informado");
        } else if (!veiculoRepository.ProcuraId(id)) {
            throw new RuntimeException("Não foi possivel identificar o registro informado pois o ID não confere");
        } else if (veiculo.getPlaca() == null){
            throw new RuntimeException(" Favor Inserir uma PLaca Valida, pois e nula");
        }else if (!veiculo.getPlaca().matches("[a-zA-Z]{3}-[0-9]{4}|[a-zA-Z]{3}[0-9]{4}" +
                "|[a-zA-Z]{3}[0-9][a-zA-Z][0-9]{2}|[a-zA-Z]{3}-[0-9][a-zA-Z][0-9]{2}")) {
            throw new RuntimeException(" Placa Invalida");
        } else if (veiculoRepository.ProcuraPlaca(veiculo.getPlaca())) {

            if(!veiculoRepository.placaexistenteNoVeiculo(veiculo.getPlaca()).equals(veiculo.getId())){
                throw new RuntimeException(" Placa não corresponde ao Carro atualizado");
            }

        }else if (veiculo.getModelo() == null){
            throw new RuntimeException(" Favor Inserir modelo Valido, pois e nulo");
        }else if (veiculo.getCor() == null) {
            throw new RuntimeException(" Favor Inserir Cor Valida, pois e nula");
        }else if (veiculo.getTipo() == null) {
            throw new RuntimeException(" Favor Inserir Tipo valido, pois e nula");
        }else if (veiculo.getAno() == null || veiculo.getAno() < 1900 || veiculo.getAno() > (LocalDate.now().getYear()+1) ) {
            throw new RuntimeException(" Favor Inserir Ano válido, pois e nula");
        }else {
            veiculoRepository.save(veiculo);
        }
    }


    @Transactional(rollbackFor = Exception.class)
    public void cadastrar(final Veiculo veiculo){



        if (veiculo.getPlaca() == null){
            throw new RuntimeException("Placa Nula");
        }else if (!veiculo.getPlaca().matches("[a-zA-Z]{3}-[0-9]{4}|[a-zA-Z]{3}[0-9]{4}" +
                "|[a-zA-Z]{3}[0-9][a-zA-Z][0-9]{2}|[a-zA-Z]{3}-[0-9][a-zA-Z][0-9]{2}")) {
            throw new RuntimeException(" Placa Invalida");
        }else if (veiculoRepository.ProcuraPlaca(veiculo.getPlaca())){
            throw new RuntimeException("Placa ja existe no banco");
        }else if (veiculo.getModelo() == null){
            throw new RuntimeException("modelo inválido");
        }else if (!modeloRepository.ProcuraId(veiculo.getModelo().getId())){
            throw new RuntimeException("Modelo Nao existe no Banco de Dados");
        }else if (veiculo.getCor() == null) {
            throw new RuntimeException("Cor inválido");
        } else if(!modeloRepository.getById(veiculo.getModelo().getId()).isAtivo()){
            throw new RuntimeException("Modelo não está ativo no banco de dados");
        }else if (veiculo.getTipo() == null) {
            throw new RuntimeException("Tipo inválido");
        } else if (veiculo.getAno() == null || veiculo.getAno() < 1900 || veiculo.getAno() > (LocalDate.now().getYear()+1)) {
            throw new RuntimeException("Ano inválido");
        }else {
            veiculoRepository.save(veiculo);
        }

    }



    @Transactional(rollbackFor = Exception.class)
    public void delete( @RequestParam("id") final Long id) {

        Movimentacao movimentacao = movimentacaoRepository.findById(id).orElse(null);

        Veiculo veiculo = this.veiculoRepository.findById(id).orElse(null);

        if (id == null){
            throw new RuntimeException(" Id do Veiculo Invalido");
        } else if (!veiculoRepository.ProcuraId(id)){
            throw new RuntimeException(" Veiculo Não existe no banco");
        } else if (veiculoRepository.veiculoExistente(movimentacao.getVeiculo().getId()) && movimentacao.getSaida() == null ) {
            throw new RuntimeException(" Veiculo não pode ser deletado, pois esta estacionado dentro do estacionamento.  movimentaçao nº " + movimentacao.getId());
        } else if(veiculoRepository.veiculoExistente(id)){
            veiculo.setAtivo(false);
            veiculoRepository.save(veiculo);
        }else {
            veiculoRepository.delete(veiculo);
        }



    }






}
