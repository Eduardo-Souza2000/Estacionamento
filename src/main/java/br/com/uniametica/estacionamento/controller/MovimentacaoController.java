package br.com.uniametica.estacionamento.controller;

import br.com.uniametica.estacionamento.entity.Configuracao;
import br.com.uniametica.estacionamento.entity.Modelo;
import br.com.uniametica.estacionamento.entity.Movimentacao;
import br.com.uniametica.estacionamento.repository.MovimentacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping (value = "/api/movimentacao")
public class MovimentacaoController {
    @Autowired
    private MovimentacaoRepository movimentacaoRepository;
    @GetMapping
    public ResponseEntity<?> findByIdRequest(@RequestParam("id") final Long id){
        final Movimentacao movimentacao = this.movimentacaoRepository.findById(id).orElse(null);

        return movimentacao == null
                ? ResponseEntity.badRequest().body("nenhum valor encontrado.")
                : ResponseEntity.ok(movimentacao);
    }

    @GetMapping ({"/lista"})
    public ResponseEntity<?> Listacompleta(){
        return ResponseEntity.ok(this.movimentacaoRepository.findAll());
    }

/*
    @GetMapping ({"/ativo"})
    public ResponseEntity<?> ListaAtivo(@PathVariable boolean ativo){

        ResponseEntity<?> movimentacao = movimentacaoRepository.findByativo(true);
        return ResponseEntity.ok(this.movimentacaoRepository.findByativo( true));

    }
*/


    @PostMapping

    public ResponseEntity<?> cadastrar(@RequestBody final Movimentacao movimentacao){
        try{
            this.movimentacaoRepository.save(movimentacao);
            return ResponseEntity.ok("Registro Cadastrado com sucesso");
        } catch (Exception e){
            return ResponseEntity.badRequest().body("ERRO" + e.getMessage());
        }

    }

    @PutMapping
    public ResponseEntity<?> editar(
            @RequestParam("id") final Long id,
            @RequestBody final  Movimentacao movimentacao
    ) {
        try{
            final Movimentacao movimentacaobanco = this.movimentacaoRepository.findById(id).orElse(null);

            if (movimentacaobanco == null || !movimentacao.getId().equals(movimentacaobanco.getId())){
                throw new RuntimeException("Não foi possivel identificar o registro informado");
            }

            this.movimentacaoRepository.save(movimentacao);
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
    @DeleteMapping("/movimentacao/{id}")
    public ResponseEntity<?> deleteMovimentacao (@PathVariable Long Id){

        Optional<Movimentacao> movimentacaoAtivo = movimentacaoRepository.findById(Id);

            try{
                final Movimentacao deleteMovimentacao = this.movimentacaoRepository.findById(Id).orElse(null);

                if (deleteMovimentacao == null || !deleteMovimentacao.getId().equals(deleteMovimentacao.getId())){
                    throw new RuntimeException("Não foi possivel identificar o registro informado");
                }

                this.movimentacaoRepository.delete();
                return ResponseEntity.ok("Registro Atualizado com sucesso");

            }
            catch (DataIntegrityViolationException e){
                return ResponseEntity.internalServerError().body("Error" + e.getCause().getCause().getMessage());
            }
            catch (RuntimeException e){
                return ResponseEntity.internalServerError().body("ERROR" + e.getMessage());
            }
*/

}






