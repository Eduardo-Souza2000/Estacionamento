package br.com.uniametica.estacionamento.controller;

import br.com.uniametica.estacionamento.entity.Modelo;
import br.com.uniametica.estacionamento.repository.ModeloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping (value = "/api/modelo")
public class ModeloController {

    //DUAS FORMAS DE FAZER O AUTORWIRED

    @Autowired
    private ModeloRepository modeloRepository;

/*

    public ModeloController(ModeloRepository modeloRepository) {
        this.modeloRepository = modeloRepository;
    }



   @GetMapping("/{id}")
    public ResponseEntity<Modelo> findByIdPath(@PathVariable("id") final Long id){
        return ResponseEntity.ok(this.modeloRepository.findById(id).orElse(null));
    }

*/


    @GetMapping
    public ResponseEntity<?> findByIdRequest(@RequestParam("id") final Long id){
        final Modelo modelo = this.modeloRepository.findById(id).orElse(null);

        return modelo == null
                ? ResponseEntity.badRequest().body("nenhum valor encontrado.")
                : ResponseEntity.ok(modelo);
    }

    @GetMapping ({"/lista"})
    public ResponseEntity<?> Listacompleta(){
        return ResponseEntity.ok(this.modeloRepository.findAll());
    }


   @PostMapping

    public ResponseEntity<?> cadastrar(@RequestBody final Modelo modelo){
       try{
           this.modeloRepository.save(modelo);
           return ResponseEntity.ok("Registro Cadastrado com sucesso");
       } catch (Exception e){
           return ResponseEntity.badRequest().body("ERRO" + e.getMessage());
       }

   }



    @PutMapping
    public ResponseEntity<?> editar(
            @RequestParam("id") final Long id,
            @RequestBody final  Modelo modelo
    ) {
       try{
           final Modelo modelobanco = this.modeloRepository.findById(id).orElse(null);

           if (modelobanco == null || !modelo.getId().equals(modelobanco.getId())){
               throw new RuntimeException("Não foi possivel identificar o registro informado");
           }

           this.modeloRepository.save(modelo);
           return ResponseEntity.ok("Registro Atualizado com sucesso");

       }
       catch (DataIntegrityViolationException e){
           return ResponseEntity.internalServerError().body("Error" + e.getCause().getCause().getMessage());
       }
       catch (RuntimeException e){
           return ResponseEntity.internalServerError().body("ERROR" + e.getMessage());
       }
    }


/*
    @DeleteMapping
    public ResponseEntity<?> delete(@RequestParam("id") final Long id)

    {
        try{
            final Modelo modelobanco = this.modeloRepository.findById(id).orElse(null);

            if (modelobanco == null || !modelo.getId().equals(modelobanco.getId())){
                throw new RuntimeException("Não foi possivel identificar o registro informado");
            }

            this.modeloRepository.save(modelo);
            return ResponseEntity.ok("Registro Atualizado com sucesso");

        }
        catch (DataIntegrityViolationException e){
            return ResponseEntity.internalServerError().body("Error" + e.getCause().getCause().getMessage());
        }
        catch (RuntimeException e){
            return ResponseEntity.internalServerError().body("ERROR" + e.getMessage());
        }
    }*/
}
