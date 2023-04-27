package br.com.uniametica.estacionamento.controller;

import br.com.uniametica.estacionamento.entity.Configuracao;
import br.com.uniametica.estacionamento.entity.Marca;
import br.com.uniametica.estacionamento.entity.Movimentacao;
import br.com.uniametica.estacionamento.repository.ConfiguracaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping (value = "/api/Configuracao")
public class ConfiguracaoController {

    @Autowired
    private ConfiguracaoRepository configuracaoRepository;

    @GetMapping
    public ResponseEntity<?> findByIdRequest(@RequestParam("id") final Long id){
        final Configuracao configuracao = this.configuracaoRepository.findById(id).orElse(null);

        return configuracao == null
                ? ResponseEntity.badRequest().body("nenhum valor encontrado.")
                : ResponseEntity.ok(configuracao);
    }



    @PostMapping

    public ResponseEntity<?> cadastrar(@RequestBody final Configuracao configuracao){
        try{
            this.configuracaoRepository.save(configuracao);
            return ResponseEntity.ok("Registro Cadastrado com sucesso");
        } catch (Exception e){
            return ResponseEntity.badRequest().body("ERRO" + e.getMessage());
        }

    }


    @PutMapping
    public ResponseEntity<?> editar(
            @RequestParam("id") final Long id,
            @RequestBody final  Configuracao configuracao
    ) {
        try{
            final Configuracao configuracaobanco  = this.configuracaoRepository.findById(id).orElse(null);

            if (configuracaobanco == null || !configuracaobanco.getId().equals(configuracaobanco.getId())){
                throw new RuntimeException("Não foi possivel identificar o registro informado");
            }

            this.configuracaoRepository.save(configuracao);
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
