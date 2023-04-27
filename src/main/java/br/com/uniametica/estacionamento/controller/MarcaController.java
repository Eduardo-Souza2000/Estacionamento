package br.com.uniametica.estacionamento.controller;

import br.com.uniametica.estacionamento.entity.Condutor;
import br.com.uniametica.estacionamento.entity.Marca;
import br.com.uniametica.estacionamento.entity.Movimentacao;
import br.com.uniametica.estacionamento.repository.CondutorRepository;
import br.com.uniametica.estacionamento.repository.MarcaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping (value = "/api/marca")
public class MarcaController {
    @Autowired
    private MarcaRepository marcaRepository;

    @GetMapping
    public ResponseEntity<?> findByIdRequest(@RequestParam("id") final Long id){
        final Marca marca = this.marcaRepository.findById(id).orElse(null);

        return marca == null
                ? ResponseEntity.badRequest().body("nenhum valor encontrado.")
                : ResponseEntity.ok(marca);
    }

    @GetMapping({"/ativo"})
    public ResponseEntity<?> getAtivos(){
        return ResponseEntity.ok(this.marcaRepository.findByAtivoTrue());
    }

    @GetMapping ({"/lista"})
    public ResponseEntity<?> Listacompleta(){

        return ResponseEntity.ok(this.marcaRepository.findAll());
    }




    @PostMapping

    public ResponseEntity<?> cadastrar(@RequestBody final Marca marca ){
        try{
            this.marcaRepository.save(marca);
            return ResponseEntity.ok("Registro Cadastrado com sucesso");
        } catch (Exception e){
            return ResponseEntity.badRequest().body("ERRO" + e.getMessage());
        }

    }


    @PutMapping
    public ResponseEntity<?> editar(
            @RequestParam("id") final Long id,
            @RequestBody final Marca marca
    ) {
        try{
            final Marca marcabanco = this.marcaRepository.findById(id).orElse(null);

            if (marcabanco == null || !marca.getId().equals(marcabanco.getId())){
                throw new RuntimeException("Não foi possivel identificar o registro informado");
            }

            this.marcaRepository.save(marca);
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
