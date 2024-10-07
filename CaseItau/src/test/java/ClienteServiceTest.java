import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private TransferenciaService transferenciaService;

    @Mock
    private Transferencia transferencia;

    @InjectMocks
    private ClienteService clienteService;

    @Test
    void cadastrarClienteIsSucess() {
        Cliente cliente = new Cliente();
        cliente.setNumeroConta("12345");

        when(clienteRepository.findByNumeroConta("12345")).thenReturn(null);
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        Cliente resultado = clienteService.cadastrarCliente(cliente);

        assertNotNull(resultado);
        assertEquals("12345", resultado.getNumeroConta());
        verify(clienteRepository, times(1)).save(cliente);
    }

    @Test
    void cadastrarClienteContaExistente() {
        Cliente cliente = new Cliente();
        cliente.setNumeroConta("12345");

        when(clienteRepository.findByNumeroConta("12345")).thenReturn(cliente);

        assertThrows(IllegalArgumentException.class, () -> clienteService.cadastrarCliente(cliente));
        verify(clienteRepository, never()).save(any(Cliente.class));
    }

    @Test
    void listarTodosIsSucesso() {
        List<Cliente> clientes = Arrays.asList(new Cliente(), new Cliente());

        when(clienteRepository.findAll()).thenReturn(clientes);

        List<Cliente> resultado = clienteService.listarTodos();

        assertEquals(2, resultado.size());
        verify(clienteRepository, times(1)).findAll();
    }

    @Test
    void buscarPorNumeroContaIsSucess() {
        Cliente cliente = new Cliente();
        cliente.setNumeroConta("12345");

        when(clienteRepository.findByNumeroConta("12345")).thenReturn(cliente);

        Cliente resultado = clienteService.buscarPorNumeroConta("12345");

        assertNotNull(resultado);
        assertEquals("12345", resultado.getNumeroConta());
        verify(clienteRepository, times(1)).findByNumeroConta("12345");
    }

    @Test
    void buscarPorNumeroContaNotFound() {
        when(clienteRepository.findByNumeroConta("12345")).thenReturn(null);

        Cliente resultado = clienteService.buscarPorNumeroConta("12345");

        assertNull(resultado);
        verify(clienteRepository, times(1)).findByNumeroConta("12345");
    }

    @Test
    void realizarTransferenciaIsSucess() {
        Cliente contaOrigem = new Cliente();
        contaOrigem.setNumeroConta("12345");
        contaOrigem.setSaldo(200.00);

        Cliente contaDestino = new Cliente();
        contaDestino.setNumeroConta("54321");
        contaDestino.setSaldo(50.00);

        when(clienteRepository.findByNumeroConta("12345")).thenReturn(contaOrigem);
        when(clienteRepository.findByNumeroConta("54321")).thenReturn(contaDestino);

        clienteService.realizarTransferencia("12345", "54321", 50.00);

        ArgumentCaptor<Cliente> clienteCaptor = ArgumentCaptor.forClass(Cliente.class);

        verify(clienteRepository, times(2)).save(clienteCaptor.capture());

        List<Cliente> clientesSalvos = clienteCaptor.getAllValues();

        assertEquals(150.00, clientesSalvos.get(0).getSaldo());
        assertEquals(100.00, clientesSalvos.get(1).getSaldo());

        verify(transferenciaService, times(1)).registrarTransferencia(transferencia);
    }

    @Test
    void realizarTransferenciaWrongValue() {
        assertThrows(IllegalArgumentException.class, () ->
                clienteService.realizarTransferencia("12345", "54321", 150.00)
        );
        verify(transferenciaService, times(1)).registrarTransferencia(transferencia);
    }

    @Test
    void realizarTransferenciaAccountNotFound() {
        when(clienteRepository.findByNumeroConta("12345")).thenReturn(null);
        when(clienteRepository.findByNumeroConta("54321")).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () ->
                clienteService.realizarTransferencia("12345", "54321", 50.00)
        );
        verify(transferenciaService, times(1)).registrarTransferencia(transferencia);
    }
}