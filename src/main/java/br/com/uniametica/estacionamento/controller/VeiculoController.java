package br.com.uniametica.estacionamento.controller;

import br.com.uniametica.estacionamento.entity.Configuracao;
import br.com.uniametica.estacionamento.entity.Movimentacao;
import br.com.uniametica.estacionamento.entity.Veiculo;
import br.com.uniametica.estacionamento.repository.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping (value = "/api/veiculo")
public class VeiculoController {

    @Autowired
    private VeiculoRepository veiculoRepository;

    @GetMapping
    public ResponseEntity<?> findByIdRequest(@RequestParam("id") final Long id){
        final Veiculo veiculo = this.veiculoRepository.findById(id).orElse(null);

        return veiculo == null
                ? ResponseEntity.badRequest().body("nenhum valor encontrado.")
                : ResponseEntity.ok(veiculo);
    }

    @GetMapping ({"/lista"})
    public ResponseEntity<?> Listacompleta(){
        return ResponseEntity.ok(this.veiculoRepository.findAll());
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody final Veiculo veiculo){
        try{
            this.veiculoRepository.save(veiculo);
            return ResponseEntity.ok("Registro Cadastrado com sucesso");
        } catch (Exception e){
            return ResponseEntity.badRequest().body("ERRO" + e.getMessage());
        }

    }


    @PutMapping
    public ResponseEntity<?> editar(
            @RequestParam("id") final Long id,
            @RequestBody final  Veiculo veiculo
    ) {
        try{
            final Veiculo veiculobanco = this.veiculoRepository.findById(id).orElse(null);

            if (veiculobanco == null || !veiculobanco.getId().equals(veiculobanco.getId())){
                throw new RuntimeException("Não foi possivel identificar o registro informado");
            }

            this.veiculoRepository.save(veiculo);
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
