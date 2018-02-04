package my.test.ingenico;

import java.math.BigDecimal;
import my.test.ingenico.model.Account;
import my.test.ingenico.model.AccountRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@Configuration
public class IngenicoApplication {

    public static void main(String[] args) {
        SpringApplication.run(IngenicoApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(
            AccountRepository accountRepository) {
        return (args) -> {
            accountRepository.save(new Account("a", new BigDecimal(100).setScale(2, BigDecimal.ROUND_HALF_UP)));
            accountRepository.save(new Account("b", new BigDecimal(100).setScale(2, BigDecimal.ROUND_HALF_UP)));
            accountRepository.save(new Account("c", new BigDecimal(100).setScale(2, BigDecimal.ROUND_HALF_UP)));
            accountRepository.save(new Account("d", new BigDecimal(100).setScale(2, BigDecimal.ROUND_HALF_UP)));
        };
    }
}
