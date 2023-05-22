package br.com.uniametica.estacionamento.controller;

import br.com.uniametica.estacionamento.entity.*;
import br.com.uniametica.estacionamento.repository.CondutorRepository;
import br.com.uniametica.estacionamento.repository.MarcaRepository;

import br.com.uniametica.estacionamento.repository.ModeloRepository;
import br.com.uniametica.estacionamento.service.MarcaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Controller
@RequestMapping (value = "/api/marca")
public class MarcaController {
    @Autowired
    private MarcaService marcaService;


    @GetMapping
    public ResponseEntity<?> findByIdRequest(@RequestParam("id") final Long id){

        try{
            return ResponseEntity.ok(marcaService.procurar(id));
        } catch (Exception e){
            return ResponseEntity.badRequest().body("ERRO " + e.getMessage());
        }

    }


    @GetMapping ({"/lista"})
    public ResponseEntity<?> Listacompleta(){
        return ResponseEntity.ok(marcaService.procurarLista());
    }


    @GetMapping({"/ativo"})
    public ResponseEntity<?> getAtivos(){
        return ResponseEntity.ok(marcaService.procurarAtivo());
    }



    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody final Marca marca){
        try{
            this.marcaService.cadastrarMarca(marca);
            return ResponseEntity.ok("Registro Cadastrado com sucesso");
        } catch (Exception e){
            return ResponseEntity.badRequest().body("ERRO " + e.getMessage());
        }

    }



    @PutMapping
    public ResponseEntity<?> editar(
            @RequestParam("id") final Long id,
            @RequestBody final  Marca marca
    ) {
        try{
            this.marcaService.editar(id,marca);
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
            this.marcaService.delete(id);
            return ResponseEntity.ok("Registro Alterado com sucesso");

        } catch (RuntimeException e){
            return ResponseEntity.internalServerError().body("ERROR" + e.getMessage());
        }

    }



}
