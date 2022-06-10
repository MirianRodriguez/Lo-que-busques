package edu.egg.loquebusques.seguridad;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import edu.egg.loquebusques.servicios.UsuarioServicio;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AppSeguridad extends WebSecurityConfigurerAdapter{
    private final UsuarioServicio usuarioServicio;
    private final BCryptPasswordEncoder codificador;

    public AppSeguridad(UsuarioServicio usuarioServicio, BCryptPasswordEncoder codificador) {
        this.usuarioServicio = usuarioServicio;
        this.codificador = codificador;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(usuarioServicio).passwordEncoder(codificador);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/autenticacion/registro", "/autenticacion/registrar", "/css/*", "/js/*", "/img/*", "/subidas/*", "/").permitAll()
                    .antMatchers("/**").authenticated()
                .and()
                .formLogin()
                    .loginPage("/autenticacion/acceder")
                        .loginProcessingUrl("/logincheck")
                        .usernameParameter("email")
                        .passwordParameter("pwd")
                        .defaultSuccessUrl("/")
                        .failureUrl("/autenticacion/acceder?error=true")
                        .permitAll()
                .and()
                    .logout()
                        .logoutUrl("/salir")
                        .logoutSuccessUrl("/")
                        .permitAll()
                .and()
                    .csrf()
                    .disable();
    }
}
