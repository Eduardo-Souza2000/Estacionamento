package br.com.uniametica.estacionamento.repository;

import br.com.uniametica.estacionamento.entity.Condutor;
import br.com.uniametica.estacionamento.entity.Marca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CondutorRepository extends JpaRepository <Condutor, Long> {
    List<Condutor> findByAtivoTrue();


    @Query(value = "select exists (select * from condutores where nome = :nome)", nativeQuery = true)
    boolean nomeExistente(@Param("nome") final String nome);

    @Query(value = "select exists (select * from movimentacoes where condutor = :id)", nativeQuery = true)
    boolean condutorExistente(@Param("id") final Long id);



    @Query(value = "select exists (select * from condutores where id = :id)", nativeQuery = true)
    boolean idExistente(@Param("id") final Long id);

    @Query(value = "select exists (select * from condutores where telefone = :telefone)", nativeQuery = true)
    boolean telefoneExistente(@Param("telefone") final String telefone);

    @Query(value = "select exists (select * from condutores where cpf = :cpf)", nativeQuery = true)
    boolean cpfExistente(@Param("cpf") final String cpf);

    @Query(value = "select condutor.id from Condutor condutor where condutor.cpf = :cpf")
    Long cpfExistentenoCondutor(@Param("cpf") String cpf);

}
