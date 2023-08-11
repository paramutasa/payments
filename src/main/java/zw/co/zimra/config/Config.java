package zw.co.zimra.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class Config {
    @Bean
    public RestTemplate restTesmplate(RestTemplateBuilder builder) {
        return builder.build();
    }
}
