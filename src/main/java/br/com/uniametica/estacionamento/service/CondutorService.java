package br.com.uniametica.estacionamento.service;

import br.com.uniametica.estacionamento.entity.Condutor;
import br.com.uniametica.estacionamento.entity.Modelo;
import br.com.uniametica.estacionamento.entity.Movimentacao;
import br.com.uniametica.estacionamento.entity.Veiculo;
import br.com.uniametica.estacionamento.repository.CondutorRepository;
import br.com.uniametica.estacionamento.repository.MovimentacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class CondutorService {
    @Autowired
    private CondutorRepository condutorRepository;

    @Autowired
    private MovimentacaoRepository movimentacaoRepository;


    private Movimentacao movimentacao;



    public Optional<Condutor> procurarCondutor(Long id){

        if (!condutorRepository.idExistente(id) ){
            throw new RuntimeException("ID inválido - Motivo: Nao Existe no Banco de Dados");
        }else {
            Optional<Condutor> condutor = this.condutorRepository.findById(id);
            return condutor;
        }

    }

    public List<Condutor> procurarLista(){

        List<Condutor> condutor = condutorRepository.findAll();
        return condutor;
    }

    public List<Condutor> procurarAtivo(){

        List<Condutor> condutor = condutorRepository.findByAtivoTrue();
        return condutor;
    }




    @Transactional(rollbackFor = Exception.class)
    public void cadastrar(final Condutor condutor){

        if(condutor.getNome() == null){
            throw new RuntimeException("Nome Nulo");
        } else if (!condutor.getNome().matches("^[a-zA-Z]{2}[a-zA-Z\s]{0,48}$")) {
            throw new RuntimeException("Nome inválido");
        } else if(condutor.getCpf() == null){
            throw new RuntimeException("Cpf inválido");
        } else if (!condutor.getCpf().matches("[0-9]{3}[.][0-9]{3}[.][0-9]{3}[-][0-9]{2}")) {
            throw new RuntimeException(" Número de cpf faltando");
        } else if (condutor.getTelefone() == null) {
            throw new RuntimeException("Telefone inválido");
        } else if(condutor.getCadastro() == null){
            throw new RuntimeException("Data Cadastro Invalido");
        } else if (condutorRepository.nomeExistente(condutor.getNome())) {
            throw new RuntimeException("Nome Repetido");
        } else if (condutorRepository.idExistente(condutor.getId())) {
            throw new RuntimeException("ID Repetido");
        } else if (condutorRepository.telefoneExistente(condutor.getTelefone())) {
            throw new RuntimeException("Telefone Repetido chefe");
        } else if (condutorRepository.cpfExistente(condutor.getCpf())) {
            throw new RuntimeException("CPF Repetido chefe");
        } else{
            condutorRepository.save(condutor);
        }


    }


    @Transactional(rollbackFor = Exception.class)
    public void editarCondutor(@RequestParam("id") final Long id, @RequestBody final  Condutor condutor) {

        final Condutor condutorbanco  = this.condutorRepository.findById(id).orElse(null);

        if (condutorbanco == null || !condutor.getId().equals(condutorbanco.getId())) {
            throw new RuntimeException(" Não foi possivel identificar o registro informado");
        } else if (!condutor.getNome().matches("^[a-zA-Z]{2}[a-zA-Z\s]{0,48}$")){
            throw new RuntimeException(" Nome inválido");
        } else if (!condutor.getCpf().matches("[0-9]{3}[.][0-9]{3}[.][0-9]{3}[-][0-9]{2}")) {
            throw new RuntimeException(" Número de cpf faltando");
        }else if (condutorRepository.nomeExistente(condutor.getNome())) {
            throw new RuntimeException(" Nome Repetido");
        } else if(condutor.getCpf() == null){
            throw new RuntimeException(" CPF inválido");
        } else if (!condutor.getTelefone().matches("^[0-9\s]{2}[0-9]{4,5}[-][0-9]{4}$")) {
            throw new RuntimeException(" Telefone com Número Faltando");
        } else{
            condutorRepository.save(condutor);
        }



    }


    @Transactional(rollbackFor = Exception.class)
    public void delete( @RequestParam("id") final Long id) {

        Condutor condutor = this.condutorRepository.findById(id).orElse(null);


        if(condutorRepository.condutorExistente(condutor.getId())){
            condutor.setAtivo(false);
            condutorRepository.save(condutor);
        }else {
            condutorRepository.delete(condutor);
        }



    }







}
