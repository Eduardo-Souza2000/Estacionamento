package br.com.uniametica.estacionamento.controller;

import br.com.uniametica.estacionamento.entity.Condutor;
import br.com.uniametica.estacionamento.entity.Movimentacao;
import br.com.uniametica.estacionamento.entity.Veiculo;
import br.com.uniametica.estacionamento.repository.MovimentacaoRepository;
import br.com.uniametica.estacionamento.repository.VeiculoRepository;
import br.com.uniametica.estacionamento.service.VeiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Controller
@RequestMapping (value = "/api/veiculo")
public class VeiculoController {

    @Autowired
    private VeiculoService veiculoService;

    @GetMapping
    public ResponseEntity<?> findByIdRequest(@RequestParam("id") final Long id){

        try{
            return ResponseEntity.ok(veiculoService.procurarVeiculo(id));
        } catch (Exception e){
            return ResponseEntity.badRequest().body("ERRO " + e.getMessage());
        }

    }


    @GetMapping ({"/lista"})
    public ResponseEntity<?> Listacompleta(){
        return ResponseEntity.ok(veiculoService.procurarLista());
    }


    @GetMapping({"/ativo"})
    public ResponseEntity<?> getAtivos(){
        return ResponseEntity.ok(veiculoService.procurarAtivo());
    }




    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody final Veiculo veiculo){
        try{
            this.veiculoService.cadastrar(veiculo);
            return ResponseEntity.ok("Registro Cadastrado com sucesso");
        } catch (Exception e){
            return ResponseEntity.badRequest().body("ERRO " + e.getMessage());
        }

    }



    @PutMapping
    public ResponseEntity<?> editar(
            @RequestParam("id") final Long id,
            @RequestBody final  Veiculo veiculo
    ) {
        try{
            this.veiculoService.editarVeiculo(id,veiculo);
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
            this.veiculoService.delete(id);
            return ResponseEntity.ok("Registro Desativado");
        } catch (DataIntegrityViolationException e){
            return ResponseEntity.internalServerError().body("Error" + e.getCause().getCause().getMessage());
        }

    }
}
