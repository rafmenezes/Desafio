import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Serviço responsável por gerenciar operações de transferência entre contas.
 * Inclui métodos para realizar transferências e registrar transferências.
 */

@Service
public class TransferenciaService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private TransferenciaRepository transferenciaRepository;


    /**
     * Registra uma transferência no banco de dados.
     *
     * @param transferencia a transferência a ser registrada
     */
    public void registrarTransferencia(Transferencia transferencia) {
        transferenciaRepository.save(transferencia);
    }

    public List<Transferencia> listarTransferencias(String numeroConta) {
        return transferenciaRepository.findByNumeroContaOrigemOrNumeroContaDestinoOrderByDataTransferenciaDesc(numeroConta,
                numeroConta);
    }
}
