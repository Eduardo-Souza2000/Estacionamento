package br.com.uniametica.estacionamento.controller;

import br.com.uniametica.estacionamento.entity.Condutor;
import br.com.uniametica.estacionamento.entity.Modelo;
import br.com.uniametica.estacionamento.entity.Movimentacao;
import br.com.uniametica.estacionamento.entity.Veiculo;
import br.com.uniametica.estacionamento.repository.CondutorRepository;
import br.com.uniametica.estacionamento.repository.MovimentacaoRepository;
import br.com.uniametica.estacionamento.service.CondutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Controller
@CrossOrigin
@RequestMapping (value = "/api/condutor")
public class CondutorController {
    @Autowired
    private CondutorService condutorService;


    @GetMapping
    public ResponseEntity<?> findByIdRequest(@RequestParam("id") final Long id){

        try{
            return ResponseEntity.ok(condutorService.procurarCondutor(id));
        } catch (Exception e){
            return ResponseEntity.badRequest().body("ERRO " + e.getMessage());
        }

    }


    @GetMapping ({"/lista"})
    public ResponseEntity<?> Listacompleta(){
        return ResponseEntity.ok(condutorService.procurarLista());
    }


    @GetMapping({"/ativo"})
    public ResponseEntity<?> getAtivos(){
        return ResponseEntity.ok(condutorService.procurarAtivo());
    }




    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody final Condutor condutor){
        try{
            this.condutorService.cadastrar(condutor);
            return ResponseEntity.ok("Registro Cadastrado com sucesso");
        } catch (Exception e){
            return ResponseEntity.badRequest().body("ERRO " + e.getMessage());
        }

    }



    @PutMapping
    public ResponseEntity<?> editar(
            @RequestParam("id") final Long id,
            @RequestBody final  Condutor condutor
    ) {
        try{
            this.condutorService.editarCondutor(id,condutor);
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
            this.condutorService.delete(id);
            return ResponseEntity.ok("Registro Desativado");
        } catch (DataIntegrityViolationException e){
            return ResponseEntity.internalServerError().body("Error" + e.getCause().getCause().getMessage());
        }

    }



}
