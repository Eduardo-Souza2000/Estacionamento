package br.com.uniametica.estacionamento.controller;

import br.com.uniametica.estacionamento.entity.Condutor;
import br.com.uniametica.estacionamento.entity.Modelo;
import br.com.uniametica.estacionamento.entity.Movimentacao;
import br.com.uniametica.estacionamento.repository.CondutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping (value = "/api/condutor")
public class CondutorController {
    @Autowired
    private CondutorRepository condutorRepository;

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







}
