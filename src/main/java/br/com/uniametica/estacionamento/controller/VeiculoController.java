package br.com.uniametica.estacionamento.controller;

import br.com.uniametica.estacionamento.entity.Movimentacao;
import br.com.uniametica.estacionamento.entity.Veiculo;
import br.com.uniametica.estacionamento.repository.MovimentacaoRepository;
import br.com.uniametica.estacionamento.repository.VeiculoRepository;
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
    private VeiculoRepository veiculoRepository;
    @Autowired
    private MovimentacaoRepository movimentacaoRepository;

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

    @GetMapping({"/ativo"})
    public ResponseEntity<?> getAtivos(){
        return ResponseEntity.ok(this.veiculoRepository.findByAtivoTrue());
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



    @DeleteMapping
    public ResponseEntity<?> delete( @RequestParam("id") final Long id){
        try {
            Veiculo veiculo = this.veiculoRepository.findById(id).orElse(null);
            AtomicBoolean var = new AtomicBoolean(false);
            AtomicBoolean exclui = new AtomicBoolean(false);

            final List<Movimentacao> movimentacao = this.movimentacaoRepository.findAll();
                    movimentacao.forEach(i -> {
                        if (id == i.getVeiculo().getId()) {
                            var.set(true);
                        } else if  (id != i.getVeiculo().getId() && veiculo != null){
                            exclui.set(true);
                        }

                    });

                    if(var.get() == true){
                        veiculo.setAtivo(false);
                        veiculoRepository.save(veiculo);
                        return ResponseEntity.ok("Registro desativado com sucesso!");
                    } else if (exclui.get() == true) {
                        veiculoRepository.delete(veiculo);
                        return ResponseEntity.ok("Registro deletado com sucesso");

                    } else {
                        return ResponseEntity.badRequest().body("Id invalido");
                    }


        } catch (DataIntegrityViolationException e){
            return ResponseEntity.internalServerError().body("Error" + e.getCause().getCause().getMessage());

        }

    }
}
