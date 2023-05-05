package br.com.uniametica.estacionamento.controller;

import br.com.uniametica.estacionamento.entity.Condutor;
import br.com.uniametica.estacionamento.entity.Modelo;
import br.com.uniametica.estacionamento.entity.Movimentacao;
import br.com.uniametica.estacionamento.entity.Veiculo;
import br.com.uniametica.estacionamento.repository.ModeloRepository;
import br.com.uniametica.estacionamento.repository.VeiculoRepository;
import br.com.uniametica.estacionamento.service.ModeloService;
import jakarta.persistence.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.apache.logging.log4j.util.LambdaUtil.getMessage;

@Controller
@RequestMapping (value = "/api/modelo")
public class ModeloController {

    //DUAS FORMAS DE FAZER O AUTORWIRED

    @Autowired
    private ModeloService modeloService;



    private Modelo modelo;

    @GetMapping
    public ResponseEntity<?> findByIdRequest(@RequestParam("id") final Long id){

        try{
            return ResponseEntity.ok(modeloService.procurar(id));
        } catch (Exception e){
            return ResponseEntity.badRequest().body("ERRO " + e.getMessage());
        }

    }


    @GetMapping ({"/lista"})
    public ResponseEntity<?> Listacompleta(){
        return ResponseEntity.ok(modeloService.procurarLista());
    }


    @GetMapping({"/ativo"})
    public ResponseEntity<?> getAtivos(){
        return ResponseEntity.ok(modeloService.procurarAtivo());
    }



   @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody final Modelo modelo){
       try{
           this.modeloService.cadastrarModelo(modelo);
           return ResponseEntity.ok("Registro Cadastrado com sucesso");
       } catch (Exception e){
           return ResponseEntity.badRequest().body("ERRO " + e.getMessage());
       }

   }



    @PutMapping
    public ResponseEntity<?> editar(
            @RequestParam("id") final Long id,
            @RequestBody final  Modelo modelo
    ) {
       try{
           this.modeloService.editar(id,modelo);
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
            this.modeloService.delete(id);
            return ResponseEntity.ok("Registro Cadastrado com sucesso");

        } catch (DataIntegrityViolationException e){
            return ResponseEntity.internalServerError().body("Error" + e.getCause().getCause().getMessage());
        }

    }













}
