import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Repositório para gerenciar operações de persistência de clientes.
 */

public interface TransferenciaRepository extends JpaRepository<Transferencia, Long> {
    List<Transferencia> findByNumeroContaOrigemOrNumeroContaDestinoOrderByDataTransferenciaDesc(String numeroContaOrigem,
                                                                                                String numeroContaDestino);
}
