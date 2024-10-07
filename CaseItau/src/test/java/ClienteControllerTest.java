
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.ArrayList;
import java.util.List;

class ClienteControllerTest {

    @Mock
    private ClienteService clienteService;

    @Mock
    private TransferenciaService transferenciaService;

    @InjectMocks
    private ClienteController clienteController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void cadastrarClienteSucesso() {
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNumeroConta("12345");

        when(clienteService.cadastrarCliente(any(Cliente.class))).thenReturn(cliente);

        ResponseEntity<Cliente> response = clienteController.cadastrarCliente(cliente);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("12345", response.getBody().getNumeroConta());
    }

    @Test
    void cadastrarClienteException() {
        when(clienteService.cadastrarCliente(any(Cliente.class))).thenThrow(
                new IllegalArgumentException("Número de conta já cadastrado"));

        Cliente cliente = new Cliente();
        cliente.setNumeroConta("12345");

        ResponseEntity<Cliente> response = clienteController.cadastrarCliente(cliente);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void listarTodosClientes() {
        List<Cliente> clientes = new ArrayList<>();
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNumeroConta("12345");
        clientes.add(cliente);

        when(clienteService.listarTodos()).thenReturn(clientes);

        ResponseEntity<List<Cliente>> response = clienteController.listarTodos();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void listarTodosClientesNull() {
        when(clienteService.listarTodos()).thenReturn(new ArrayList<>());

        ResponseEntity<List<Cliente>> response = clienteController.listarTodos();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void buscarPorNumeroContaIsSucess() {
        Cliente cliente = new Cliente();
        cliente.setNumeroConta("12345");

        when(clienteService.buscarPorNumeroConta(anyString())).thenReturn(cliente);

        ResponseEntity<Cliente> response = clienteController.buscarPorNumeroConta("12345");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("12345", response.getBody().getNumeroConta());
    }

    @Test
    void buscarPorNumeroContaNotSucess() {
        when(clienteService.buscarPorNumeroConta(anyString())).thenReturn(null);

        ResponseEntity<Cliente> response = clienteController.buscarPorNumeroConta("12345");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void realizarTransferenciaIsSucess() {
        ResponseEntity<String> response = clienteController.realizarTransferencia("12345",
                "54321", 50.00);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Transferência realizada com sucesso!", response.getBody());
    }

    @Test
    void realizarTransferenciaNotSucess() {
        doThrow(new IllegalArgumentException("Saldo insuficiente"))
                .when(clienteService).realizarTransferencia(anyString(), anyString(), anyDouble());

        ResponseEntity<String> response = clienteController.realizarTransferencia("12345",
                "54321", 150.00);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Saldo insuficiente", response.getBody());
    }

    @Test
    void realizarTransferencia_comErroInterno() {
        doThrow(new RuntimeException("Erro inesperado"))
                .when(clienteService).realizarTransferencia(anyString(), anyString(), anyDouble());

        ResponseEntity<String> response = clienteController.realizarTransferencia("12345",
                "54321", 50.00);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Erro inesperado", response.getBody());
    }

    @Test
    void buscarTransferenciasIsSucess() {
        List<Transferencia> transferencias = new ArrayList<>();
        transferencias.add(new Transferencia());

        when(transferenciaService.listarTransferencias(anyString())).thenReturn(transferencias);

        ResponseEntity<List<Transferencia>> response = clienteController.buscarTransferencias("12345");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void buscarTransferenciasNotSucess() {
        when(transferenciaService.listarTransferencias(anyString())).thenReturn(new ArrayList<>());

        ResponseEntity<List<Transferencia>> response = clienteController.buscarTransferencias("12345");

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }

}