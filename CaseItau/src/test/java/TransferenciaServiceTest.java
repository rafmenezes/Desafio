import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransferenciaServiceTest {

    @Mock
    private TransferenciaRepository transferenciaRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private TransferenciaService transferenciaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registrarTransferenciaSucess() {

        Transferencia transferencia = new Transferencia();
        transferencia.setNumeroContaOrigem("12345");
        transferencia.setNumeroContaDestino("54321");
        transferencia.setValor(50.00);

        transferenciaService.registrarTransferencia(transferencia);

        verify(transferenciaRepository, times(1)).save(transferencia);
    }

    @Test
    void listarTransferenciasSucess() {

        String numeroConta = "12345";
        Transferencia transferencia1 = new Transferencia();
        transferencia1.setNumeroContaOrigem("12345");
        transferencia1.setNumeroContaDestino("54321");

        Transferencia transferencia2 = new Transferencia();
        transferencia2.setNumeroContaOrigem("54321");
        transferencia2.setNumeroContaDestino("12345");

        List<Transferencia> transferenciasEsperadas = Arrays.asList(transferencia1, transferencia2);

        when(transferenciaRepository.findByNumeroContaOrigemOrNumeroContaDestinoOrderByDataTransferenciaDesc(numeroConta,
                numeroConta))
                .thenReturn(transferenciasEsperadas);

        List<Transferencia> transferenciasRetornadas = transferenciaService.listarTransferencias(numeroConta);

        assertEquals(transferenciasEsperadas, transferenciasRetornadas);
        verify(transferenciaRepository, times(1))
                .findByNumeroContaOrigemOrNumeroContaDestinoOrderByDataTransferenciaDesc(numeroConta, numeroConta);
    }

}