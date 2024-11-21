package processo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.pattern.PathPatternParser;

@SpringBootApplication
public class CadastroServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CadastroServiceApplication.class, args);
    }

     @Bean
    public PathPatternParser pathPatternParser() {
        PathPatternParser parser = new PathPatternParser();
        parser.setCaseSensitive(false); // Ignorar sensibilidade de maiúsculas/minúsculas nas URLs
        return parser;
    }
    
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
