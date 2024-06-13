package com.task_pay.task_pay.configurations;
import com.task_pay.task_pay.models.enums.Permission;
import com.task_pay.task_pay.models.enums.Role;
import com.task_pay.task_pay.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
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
        http.cors(withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        authorize -> authorize
                                // no restrictions
                                .requestMatchers(PUBLIC_URLS)
                                .permitAll()
                                // Admin endpoint: only admin can access
                                .requestMatchers("/api/user/**").hasAnyRole(ADMIN.name())
                                .requestMatchers(GET, "/api/user/**").hasAnyAuthority(ADMIN_READ.name())
                                .requestMatchers(POST, "/api/user/**").hasAuthority(ADMIN_CREATE.name())
                                .requestMatchers(PUT, "/api/user/**").hasAuthority(ADMIN_UPDATE.name())
                                .requestMatchers(DELETE, "/api/user/**").hasAuthority(ADMIN_DELETE.name())


                                .requestMatchers("/api/invite/**").hasRole(ADMIN.name())
                                .requestMatchers(GET,"/api/invite/**").hasAuthority(ADMIN_READ.name())
                                .requestMatchers(POST,"/api/invite/**").hasAuthority(ADMIN_CREATE.name())
                                .requestMatchers(PUT,"/api/invite/**").hasAuthority(ADMIN_UPDATE.name())
                                .requestMatchers(DELETE,"/api/invite/**").hasAuthority(ADMIN_DELETE.name())


                                .requestMatchers("/api/task/**").hasRole(ADMIN.name())
                                .requestMatchers(GET,"/api/task/**").hasAuthority(ADMIN_READ.name())
                                .requestMatchers(POST,"/api/task/**").hasAuthority(ADMIN_CREATE.name())
                                .requestMatchers(PUT,"/api/task/**").hasAuthority(ADMIN_UPDATE.name())
                                .requestMatchers(DELETE,"/api/task/**").hasAuthority(ADMIN_DELETE.name())


                                .requestMatchers("/api/payment/**").hasRole(ADMIN.name())
                                .requestMatchers(GET,"/api/payment/**").hasAuthority(ADMIN_READ.name())
                                .requestMatchers(POST,"/api/payment/**").hasAuthority(ADMIN_CREATE.name())
                                .requestMatchers(PUT,"/api/payment/**").hasAuthority(ADMIN_UPDATE.name())
                                .requestMatchers(DELETE,"/api/payment/**").hasAuthority(ADMIN_DELETE.name())

                                //Banker end point:only banker can access
                                .requestMatchers("/api/banker/**").hasRole(BANKER.name())
                                .requestMatchers(GET, "/api/banker/**").hasAuthority(BANKER_READ.name())


                                .requestMatchers("/api/banker/**").hasRole(BANKER.name())
                                .requestMatchers(GET,"/api/banker/**").hasAuthority(BANKER_READ.name())


                                .requestMatchers("/api/banker/**").hasRole(BANKER.name())
                                .requestMatchers(GET,"/api/banker/**").hasAuthority(BANKER_READ.name())


                                //User end point:only user can access
//                                .requestMatchers("/api/user/**").hasRole(USER.name())
//                                .requestMatchers(GET,"/api/user/fetchUserById{userId}").hasAuthority(USER_READ.name())
//                                .requestMatchers(PUT,"/api/user/updateUserType/{userId}").hasAuthority(USER_UPDATE.name())
//                                .requestMatchers(PUT,"/api/user/updatePassword").hasAuthority(USER_UPDATE.name())
//                                .requestMatchers(POST,"/api/user/uploadUserImage/{userId}").hasAuthority(USER_CREATE.name())
//                                .requestMatchers(GET,"/api/user/serveUserImage/{userId}").hasAuthority(USER_READ.name())
//                                .requestMatchers("/api/invite/**").hasRole(USER.name())
//                                .requestMatchers(GET,"/api/invite/**").hasAuthority(USER_READ.name())
//                                .requestMatchers(POST,"/api/invite/**").hasAuthority(USER_CREATE.name())
//                                .requestMatchers(PUT,"/api/invite/**").hasAuthority(USER_UPDATE.name())
//                                .requestMatchers(DELETE,"/api/invite/**").hasAuthority(USER_DELETE.name())
//                                .requestMatchers("/api/task/**").hasRole(USER.name())
//                                .requestMatchers(GET,"/api/task/**").hasAuthority(USER_READ.name())
//                                .requestMatchers(POST,"/api/task/**").hasAuthority(USER_CREATE.name())
//                                .requestMatchers(PUT,"/api/task/**").hasAuthority(USER_UPDATE.name())
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
}