package br.com.uniametica.estacionamento.controller;

import br.com.uniametica.estacionamento.entity.*;
import br.com.uniametica.estacionamento.repository.CondutorRepository;
import br.com.uniametica.estacionamento.repository.MarcaRepository;

import br.com.uniametica.estacionamento.repository.ModeloRepository;
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
    private MarcaRepository marcaRepository;
    @Autowired
    private ModeloRepository modeloRepository;

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

    @DeleteMapping
    public ResponseEntity<?> delete( @RequestParam("id") final Long id){
        try {
            Marca marca = this.marcaRepository.findById(id).orElse(null);
            AtomicBoolean var = new AtomicBoolean(false);
            AtomicBoolean exclui = new AtomicBoolean(false);

            final List<Modelo> modelo = this.modeloRepository.findAll();

            modelo.forEach(i -> {
                if (id == i.getMarca().getId()) {
                    var.set(true);
                } else if  (id != i.getMarca().getId() && marca != null){
                    exclui.set(true);
                }

            });

            if(var.get() == true){
                marca.setAtivo(false);
                marcaRepository.save(marca);
                return ResponseEntity.ok("Registro desativado com sucesso!");
            } else if (exclui.get() == true) {
                marcaRepository.delete(marca);
                return ResponseEntity.ok("Registro deletado com sucesso");

            } else {
                return ResponseEntity.badRequest().body("Id invalido");
            }


        } catch (DataIntegrityViolationException e){
            return ResponseEntity.internalServerError().body("Error" + e.getCause().getCause().getMessage());

        }

    }




}
