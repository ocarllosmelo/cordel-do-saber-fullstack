package br.com.cordeldosaber.catalog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Libera o acesso para todas as rotas (/**)
        registry.addMapping("/**")
                // Apenas para o nosso Front-end
                .allowedOrigins("http://localhost:5173")
                // Libera os métodos que vamos usar
                .allowedMethods("GET", "POST", "PUT", "DELETE");
    }
}