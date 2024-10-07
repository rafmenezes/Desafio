import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Classe principal do aplicativo API Clientes.
 * Esta classe é responsável por inicializar a aplicação Spring Boot.
 */

@SpringBootApplication
@ComponentScan(basePackages = "java")
public class ApiClientesApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiClientesApplication.class, args);
    }
}
