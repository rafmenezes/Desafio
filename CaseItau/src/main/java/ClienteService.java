import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Serviço responsável por gerenciar as operações relacionadas aos clientes.
 * Inclui métodos para cadastrar e buscar clientes.
 */

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    private Transferencia transferencia;

    private TransferenciaService transferenciaService;

    /**
     * Cadastra um novo cliente.
     *
     * @param cliente o cliente a ser cadastrado
     * @return o cliente cadastrado
     * @throws IllegalArgumentException se o número da conta já estiver cadastrado
     */

    public Cliente cadastrarCliente(Cliente cliente) {
        if (clienteRepository.findByNumeroConta(cliente.getNumeroConta())!= null) {
            throw new IllegalArgumentException("Número de conta já cadastrado.");
        }
        return clienteRepository.save(cliente);
    }

    /**
     * Lista todos os clientes cadastrados.
     *
     * @return uma lista de clientes
     */

    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    /**
     * Busca um cliente pelo número da conta.
     *
     * @param numeroConta o número da conta do cliente
     * @return o cliente encontrado, ou null se não existir
     */

    public Cliente buscarPorNumeroConta(String numeroConta) {
        return clienteRepository.findByNumeroConta(numeroConta);
    }

    /**
     * Realiza uma transferência entre duas contas.
     *
     * @param numeroContaOrigem o número da conta de origem
     * @param numeroContaDestino o número da conta de destino
     * @param valor o valor a ser transferido
     * @throws IllegalArgumentException se o valor da transferência não for válido ou se uma das contas não existir
     */

    @Transactional
    public void realizarTransferencia(String numeroContaOrigem, String numeroContaDestino, double valor) {

        transferencia.setNumeroContaOrigem(numeroContaOrigem);
        transferencia.setNumeroContaDestino(numeroContaDestino);
        transferencia.setValor(valor);
        transferencia.setDataTransferencia(LocalDateTime.now());

        try {
            if (valor <= 0 || valor > 100) {
                transferencia.setSucesso(false);
                throw new IllegalArgumentException("O valor da transferência deve ser maior que R$ 0,00 e no máximo R$ 100,00.");
            }

            Cliente contaOrigem = clienteRepository.findByNumeroConta(numeroContaOrigem);
            Cliente contaDestino = clienteRepository.findByNumeroConta(numeroContaDestino);

            if (contaOrigem == null || contaDestino == null) {
                transferencia.setSucesso(false);
                throw new IllegalArgumentException("Uma ou ambas as contas não existem.");
            }

            contaOrigem.debitar(valor);
            contaDestino.creditar(valor);

            clienteRepository.save(contaOrigem);
            clienteRepository.save(contaDestino);

            transferencia.setSucesso(true);

        } catch (IllegalArgumentException e) {
            transferencia.setSucesso(false);
            throw e;
        } finally {
            transferenciaService.registrarTransferencia(transferencia);
        }

    }
}

