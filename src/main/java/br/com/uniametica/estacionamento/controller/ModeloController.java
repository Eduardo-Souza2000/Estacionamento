package br.com.uniametica.estacionamento.controller;

import br.com.uniametica.estacionamento.entity.Modelo;
import br.com.uniametica.estacionamento.entity.Movimentacao;
import br.com.uniametica.estacionamento.entity.Veiculo;
import br.com.uniametica.estacionamento.repository.ModeloRepository;
import br.com.uniametica.estacionamento.repository.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Controller
@RequestMapping (value = "/api/modelo")
public class ModeloController {

    //DUAS FORMAS DE FAZER O AUTORWIRED

    @Autowired
    private ModeloRepository modeloRepository;
    @Autowired
    private VeiculoRepository veiculoRepository;



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


    @GetMapping({"/ativo"})
    public ResponseEntity<?> getAtivos(){
        return ResponseEntity.ok(this.modeloRepository.findByAtivoTrue());
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




    @DeleteMapping
    public ResponseEntity<?> delete( @RequestParam("id") final Long id){
        try {
            Modelo modelo = this.modeloRepository.findById(id).orElse(null);
            AtomicBoolean var = new AtomicBoolean(false);
            AtomicBoolean exclui = new AtomicBoolean(false);

            final List<Veiculo> veiculo = this.veiculoRepository.findAll();

            veiculo.forEach(i -> {
                if (id == i.getModelo().getId()) {
                    var.set(true);
                } else if  (id != i.getModelo().getId() && modelo != null){
                    exclui.set(true);
                }

            });

            if(var.get() == true){
                modelo.setAtivo(false);
                modeloRepository.save(modelo);
                return ResponseEntity.ok("Registro desativado com sucesso!");
            } else if (exclui.get() == true) {
                modeloRepository.delete(modelo);
                return ResponseEntity.ok("Registro deletado com sucesso");

            } else {
                return ResponseEntity.badRequest().body("Id invalido");
            }


        } catch (DataIntegrityViolationException e){
            return ResponseEntity.internalServerError().body("Error" + e.getCause().getCause().getMessage());

        }

    }

}
