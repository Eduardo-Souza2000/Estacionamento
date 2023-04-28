package br.com.uniametica.estacionamento.controller;

import br.com.uniametica.estacionamento.entity.Condutor;
import br.com.uniametica.estacionamento.entity.Modelo;
import br.com.uniametica.estacionamento.entity.Movimentacao;
import br.com.uniametica.estacionamento.entity.Veiculo;
import br.com.uniametica.estacionamento.repository.CondutorRepository;
import br.com.uniametica.estacionamento.repository.MovimentacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Controller
@RequestMapping (value = "/api/condutor")
public class CondutorController {
    @Autowired
    private CondutorRepository condutorRepository;
    @Autowired
    private MovimentacaoRepository movimentacaoRepository;

    @GetMapping
    public ResponseEntity<?> findByIdRequest(@RequestParam("id") final Long id){
        final Condutor condutor = this.condutorRepository.findById(id).orElse(null);

        return condutor == null
                ? ResponseEntity.badRequest().body("nenhum valor encontrado.")
                : ResponseEntity.ok(condutor);
    }

    @GetMapping ({"/lista"})
    public ResponseEntity<?> Listacompleta(){

        return ResponseEntity.ok(this.condutorRepository.findAll());
    }

    @GetMapping({"/ativo"})
    public ResponseEntity<?> getAtivos(){
        return ResponseEntity.ok(this.condutorRepository.findByAtivoTrue());
    }


    @PostMapping

    public ResponseEntity<?> cadastrar(@RequestBody final Condutor condutor){
        try{
            this.condutorRepository.save(condutor);
            return ResponseEntity.ok("Registro Cadastrado com sucesso");
        } catch (Exception e){
            return ResponseEntity.badRequest().body("ERRO" + e.getMessage());
        }

    }

    @PutMapping
    public ResponseEntity<?> editar(
            @RequestParam("id") final Long id,
            @RequestBody final  Condutor condutor
    ) {
        try{
            final Condutor condutorbanco = this.condutorRepository.findById(id).orElse(null);

            if (condutorbanco == null || !condutor.getId().equals(condutorbanco.getId())){
                throw new RuntimeException("Não foi possivel identificar o registro informado");
            }

            this.condutorRepository.save(condutor);
            return ResponseEntity.ok("Registro Atualizado com sucesso");

        }
        catch (DataIntegrityViolationException e){
            return ResponseEntity.internalServerError().body("Error" + e.getCause().getCause().getMessage());
        }
        catch (RuntimeException e){
            return ResponseEntity.internalServerError().body("ERROR" + e.getMessage());
        }
    }


    @DeleteMapping
    public ResponseEntity<?> delete( @RequestParam("id") final Long id){
        try {
            Condutor condutor = this.condutorRepository.findById(id).orElse(null);
            AtomicBoolean var = new AtomicBoolean(false);
            AtomicBoolean exclui = new AtomicBoolean(false);

            final List<Movimentacao> movimentacao = this.movimentacaoRepository.findAll();
            movimentacao.forEach(i -> {
                if (id == i.getCondutor().getId()) {
                    var.set(true);
                } else if  (id != i.getCondutor().getId() && condutor != null){
                    exclui.set(true);
                }

            });

            if(var.get() == true){
                condutor.setAtivo(false);
                condutorRepository.save(condutor);
                return ResponseEntity.ok("Registro desativado com sucesso!");
            } else if (exclui.get() == true) {
                condutorRepository.delete(condutor);
                return ResponseEntity.ok("Registro deletado com sucesso");

            } else {
                return ResponseEntity.badRequest().body("Id invalido");
            }


        } catch (DataIntegrityViolationException e){
            return ResponseEntity.internalServerError().body("Error" + e.getCause().getCause().getMessage());

        }

    }






}
