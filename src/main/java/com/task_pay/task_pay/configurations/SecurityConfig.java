package com.task_pay.task_pay.configurations;
import com.task_pay.task_pay.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import static com.task_pay.task_pay.models.enums.Permission.*;
import static com.task_pay.task_pay.models.enums.Role.*;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthFilter;

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private  LogoutHandler logoutHandler;


    private final String[] PUBLIC_URLS = {
            "/api/auth/**",
            "/v2/api-docs",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui/**",
            "/webjars/**",
            "/swagger-ui.html"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors(cors -> cors.configurationSource(corsConfigurationSource()))
//        http.cors(withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        authorize -> authorize
                                // no restrictions
                                .requestMatchers(PUBLIC_URLS)
                                .permitAll()


                                // Admin endpoint: only admin can access
                                .requestMatchers("/api/user/**").hasAnyRole(ADMIN.name(),USER.name())
                                .requestMatchers(GET, "/api/user/**").hasAnyAuthority(ADMIN_READ.name(),USER_READ.name())
                                .requestMatchers(POST, "/api/user/**").hasAnyAuthority(ADMIN_CREATE.name(),USER_CREATE.name())
                                .requestMatchers(PUT, "/api/user/**").hasAnyAuthority(ADMIN_UPDATE.name(),USER_UPDATE.name())
                                .requestMatchers(DELETE, "/api/user/**").hasAnyAuthority(ADMIN_DELETE.name(),USER_DELETE.name())


                                .requestMatchers("/api/invite/**").hasAnyRole(ADMIN.name(),USER.name())
                                .requestMatchers(GET,"/api/invite/**").hasAnyAuthority(ADMIN_READ.name(),USER_READ.name())
                                .requestMatchers(POST,"/api/invite/**").hasAnyAuthority(ADMIN_CREATE.name(),USER_CREATE.name())
                                .requestMatchers(PUT,"/api/invite/**").hasAnyAuthority(ADMIN_UPDATE.name(),USER_UPDATE.name())
                                .requestMatchers(DELETE,"/api/invite/**").hasAnyAuthority(ADMIN_DELETE.name(),USER_DELETE.name())


                                .requestMatchers("/api/task/**").hasAnyRole(ADMIN.name(),USER.name())
                                .requestMatchers(GET,"/api/task/**").hasAnyAuthority(ADMIN_READ.name(),USER_READ.name())
                                .requestMatchers(POST,"/api/task/**").hasAnyAuthority(ADMIN_CREATE.name(),USER_CREATE.name())
                                .requestMatchers(PUT,"/api/task/**").hasAnyAuthority(ADMIN_UPDATE.name(),USER_UPDATE.name())
                                .requestMatchers(DELETE,"/api/task/**").hasAnyAuthority(ADMIN_DELETE.name(),USER_DELETE.name())


                                .requestMatchers("/api/payment/**").hasAnyRole(ADMIN.name(),USER.name())
                                .requestMatchers(GET,"/api/payment/**").hasAnyAuthority(ADMIN_READ.name(),USER_READ.name())
                                .requestMatchers(POST,"/api/payment/**").hasAnyAuthority(ADMIN_CREATE.name(),USER_CREATE.name())
                                .requestMatchers(PUT,"/api/payment/**").hasAnyAuthority(ADMIN_UPDATE.name(),USER_UPDATE.name())
                                .requestMatchers(DELETE,"/api/payment/**").hasAnyAuthority(ADMIN_DELETE.name(),USER_DELETE.name())


                                //Banker end point:only banker can access
                                .requestMatchers("/api/banker/**").hasRole(BANKER.name())
                                .requestMatchers(GET, "/api/banker/**").hasAuthority(BANKER_READ.name())


                                .requestMatchers("/api/banker/**").hasRole(BANKER.name())
                                .requestMatchers(GET,"/api/banker/**").hasAuthority(BANKER_READ.name())


                                .requestMatchers("/api/banker/**").hasRole(BANKER.name())
                                .requestMatchers(GET,"/api/banker/**").hasAuthority(BANKER_READ.name())

                                .anyRequest()
                                .authenticated()
                )
                .exceptionHandling(ex->ex.authenticationEntryPoint(authenticationEntryPoint))
                .sessionManagement(
                        (session) -> session
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .authenticationProvider(authenticationProvider)

                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(
                        logoutHandler -> logoutHandler
                                .logoutUrl("/api/auth/logout")
                                .logoutSuccessHandler(
                                        (request, response, authentication) -> SecurityContextHolder.clearContext()
                                )
                );

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.applyPermitDefaultValues();
        configuration.setAllowCredentials(true);
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",configuration);
        return source;
    }



}