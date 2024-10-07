import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositório para gerenciar operações de persistência de clientes.
 * Extende a interface JpaRepository para fornecer funcionalidades básicas
 * de CRUD e consultas personalizadas.
 */

public interface ClienteRepository  extends JpaRepository<Cliente, Long> {

    /**
     * Busca um cliente pelo número da conta.
     *
     * @param numeroConta o número da conta do cliente a ser buscado
     * @return o cliente correspondente ao número da conta, ou null se não encontrado
     */

    Cliente findByNumeroConta(String numeroConta);
}
