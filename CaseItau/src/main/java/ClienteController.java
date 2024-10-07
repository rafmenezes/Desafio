import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * Controlador REST para gerenciar operações relacionadas a clientes.
 * Expondo endpoints para cadastrar, listar e buscar clientes.
 */

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private TransferenciaService transferenciaService;

    /**
     * Cadastra um novo cliente.
     *
     * @param cliente o cliente a ser cadastrado
     * @return uma resposta contendo o cliente cadastrado e o status HTTP 201 (Created)
     * @throws IllegalArgumentException se o número da conta já estiver cadastrado
     */

    @PostMapping
    public ResponseEntity<Cliente> cadastrarCliente(@RequestBody Cliente cliente) {
        try {
            Cliente novoCliente = clienteService.cadastrarCliente(cliente);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoCliente);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    /**
     * Lista todos os clientes cadastrados.
     *
     * @return uma resposta contendo uma lista de clientes e o status HTTP 200 (OK)
     */

    @GetMapping
    public ResponseEntity<List<Cliente>> listarTodos() {
        List<Cliente> clientes = clienteService.listarTodos();
        if (clientes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(clientes);
    }

    /**
     * Busca um cliente pelo número da conta.
     *
     * @param numeroConta o número da conta do cliente a ser buscado
     * @return uma resposta contendo o cliente encontrado e o status HTTP 200 (OK),
     *         ou status HTTP 404 (Not Found) se o cliente não for encontrado
     */

    @GetMapping("/numeroConta/{numeroConta}")
    public ResponseEntity<Cliente> buscarPorNumeroConta(@PathVariable String numeroConta) {
        Cliente cliente = clienteService.buscarPorNumeroConta(numeroConta);
        return cliente != null
                ? ResponseEntity.ok(cliente)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    /**
     * Realiza uma transferência entre duas contas.
     *
     * @param numeroContaOrigem o número da conta de origem
     * @param numeroContaDestino o número da conta de destino
     * @param valor o valor a ser transferido
     * @return uma resposta contendo uma mensagem de sucesso e o status HTTP 200 (OK),
     *         ou uma mensagem de erro e o status HTTP 400 (Bad Request) se os parâmetros forem inválidos,
     *         ou status HTTP 500 (Internal Server Error) em caso de erro interno
     */

    @PostMapping("/transferencia")
    public ResponseEntity<String> realizarTransferencia(@RequestParam String numeroContaOrigem,
                                                        @RequestParam String numeroContaDestino,
                                                        @RequestParam double valor) {
        try {
            clienteService.realizarTransferencia(numeroContaOrigem, numeroContaDestino, valor);
            return ResponseEntity.ok("Transferência realizada com sucesso!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    /**
     * Busca as transferências relacionadas a uma conta, por ordem de data decrescente.
     *
     * @param numeroConta o número da conta cujas transferências devem ser buscadas
     * @return uma resposta contendo uma lista de transferências e o status HTTP 200 (OK),
     *         ou status HTTP 204 (No Content) se não houver transferências registradas
     */

    @GetMapping("/{numeroConta}/transferencias")
    public ResponseEntity<List<Transferencia>> buscarTransferencias(@PathVariable String numeroConta) {
        List<Transferencia> transferencias = transferenciaService.listarTransferencias(numeroConta);
        if (transferencias.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(transferencias);
    }

}
