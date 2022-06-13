package edu.egg.loquebusques.configuracion;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class AppConfiguracion {
    @Bean
    public BCryptPasswordEncoder codificador() {
        return new BCryptPasswordEncoder();
    }
}
