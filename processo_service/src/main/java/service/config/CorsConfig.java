/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service.config;

/**
 *
 * @author MARCELO
 */
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOriginPattern("*"); // Permite todas as origens, ajuste conforme necessário
        config.addAllowedMethod("*");       // Permite todos os métodos HTTP (GET, POST, PUT, DELETE, etc.)
        config.addAllowedHeader("*");       // Permite todos os cabeçalhos
        config.setAllowCredentials(true);   // Permite o envio de credenciais (cookies, headers de autenticação)

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config); // Aplica para todas as rotas
        return new CorsFilter(source);
    }
}
