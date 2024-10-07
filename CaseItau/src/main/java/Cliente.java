import jakarta.persistence.Entity;

/**
 * Representa um cliente bancário.
 * Contém informações sobre o cliente, como o número da conta e o saldo.
 */

@Entity
public class Cliente {

    private Long id;
    private String nome;
    private String numeroConta;
    private Double saldo;


    public Cliente(Long id, String nome, String numeroConta, Double saldo) {
        this.id = id;
        this.nome = nome;
        this.numeroConta = numeroConta;
        this.saldo = saldo;
    }

    public Cliente() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNumeroConta() {
        return numeroConta;
    }

    public void setNumeroConta(String numeroConta) {
        this.numeroConta = numeroConta;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public void debitar(double valor) {
        if (valor <= 0 || saldo < valor) {
            throw new IllegalArgumentException("Valor inválido ou saldo insuficiente.");
        }
        this.saldo -= valor;
    }

    public void creditar(double valor) {
        if (valor <= 0) {
            throw new IllegalArgumentException("Valor inválido.");
        }
        this.saldo += valor;
    }

}
