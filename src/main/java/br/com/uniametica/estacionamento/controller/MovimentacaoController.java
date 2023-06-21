package br.com.uniametica.estacionamento.controller;

import br.com.uniametica.estacionamento.entity.Movimentacao;
import br.com.uniametica.estacionamento.service.MovimentacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin
@RequestMapping (value = "/api/movimentacao")
public class MovimentacaoController {
    @Autowired
    private MovimentacaoService  movimentacaoService;

    @GetMapping
    public ResponseEntity<?> findByIdRequest(@RequestParam("id") final Long id){

        try{
            return ResponseEntity.ok(movimentacaoService.procuraMovimentacao(id));
        } catch (Exception e){
            return ResponseEntity.badRequest().body("ERRO " + e.getMessage());
        }

    }


    @GetMapping ({"/lista"})
    public ResponseEntity<?> Listacompleta(){
        return ResponseEntity.ok(movimentacaoService.procurarLista());
    }


    @GetMapping({"/ativo"})
    public ResponseEntity<?> getAtivos(){
        return ResponseEntity.ok(movimentacaoService.procurarAtivo());
    }




    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody final Movimentacao movimentacao){
        try{
            this.movimentacaoService.cadastrar(movimentacao);
            return ResponseEntity.ok("Registro Cadastrado com sucesso");
        } catch (Exception e){
            return ResponseEntity.badRequest().body("ERRO " + e.getMessage());
        }

    }



    @PutMapping
    public ResponseEntity<?> editar(
            @RequestParam("id") final Long id,
            @RequestBody final  Movimentacao movimentacao
    ) {
        try{
            this.movimentacaoService.editarMovimentacao(id,movimentacao);
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
            this.movimentacaoService.delete(id);
            return ResponseEntity.ok("Registro Desativado");
        } catch (DataIntegrityViolationException e){
            return ResponseEntity.internalServerError().body("Error" + e.getCause().getCause().getMessage());
        }

    }


    @PutMapping(value = "/finalizar")
    public ResponseEntity<?> finalizar(@RequestParam("id")final Long id,@RequestBody final  Movimentacao movimentacao){
        try{
            return ResponseEntity.ok(movimentacaoService.finalizarMovimentacao(id,movimentacao));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Error " + e.getMessage());
        }
    }


}






