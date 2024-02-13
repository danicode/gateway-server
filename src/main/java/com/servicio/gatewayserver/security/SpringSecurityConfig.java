package com.servicio.gatewayserver.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
@Configuration
public class SpringSecurityConfig {

    @Bean
    public SecurityWebFilterChain configure(ServerHttpSecurity http) {
        return http
                .authorizeExchange(exchanges ->
                        exchanges
                                .pathMatchers("/api/security/oauth/**").permitAll()
                                .pathMatchers(HttpMethod.GET, "/api/productos/listar",
                                        "/api/items/listar",
                                        "/api/usuarios/usuarios",
                                        "/api/items/ver/{id}/cantidad/{cantidad}",
                                        "/api/productos/ver/{id}").permitAll()
                                .pathMatchers(HttpMethod.GET, "/api/usuarios/usuarios/{id}").hasAnyRole("ADMIN", "USER")
                                .pathMatchers("/api/productos/**", "/api/items/**", "/api/usuarios/usuarios/**").hasRole("ADMIN")
                                .anyExchange().authenticated()
                )
                .csrf(csrf -> csrf.disable())
                .build();
    }
}
