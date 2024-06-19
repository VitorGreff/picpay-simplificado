package greff.picpay.infra;

import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class Config {
    @Bean
    public FlywayMigrationStrategy setupFlyway() {
        return flyway -> {
            flyway.clean();
            flyway.migrate();
        };
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}

