package uz.brb.loan_management_system.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import uz.brb.loan_management_system.dto.Response;

import java.time.LocalDateTime;

import static uz.brb.loan_management_system.util.Util.localDateTimeFormatter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JWTAuthenticationFilter jwtAuthenticationFilter;

    /**
     * @param configuration
     * @return Bu metod ko'pincha, foydalanuvchi JWT tokeni bilan autentifikatsiya qilayotgan
     * va Spring Security bilan o'rnatilgan ilovalarda ishlatiladi.
     * @throws Exception
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizationManager -> {
                    authorizationManager.requestMatchers(
                                    "/swagger-ui/**",
                                    "/swagger-ui.html",
                                    "/v3/api-docs/**",
                                    "/api/auths/**",
                                    "/api/users/**",
                                    "/webjars/**",
                                    "/actuator/**").permitAll()
                            .requestMatchers(
                                    "/api/v1/loans/**",
                                    "/api/v1/loan-applications/**",
                                    "/api/v1/accounts/**",
                                    "/api/v1/clients/**",
                                    "/api/cards/**",
                                    "/api/v1/transactions/**",
                                    "/api/v1/histories/**"
                                    ).hasRole("ADMIN")
                            .anyRequest().authenticated();
                })
                .formLogin(AbstractAuthenticationFilterConfigurer::permitAll)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exception -> exception
                        // 401 Unauthorized
                        .authenticationEntryPoint((request, response, authException) -> {
                            Response resp = Response.builder()
                                    .code(HttpStatus.UNAUTHORIZED.value())
                                    .status(HttpStatus.UNAUTHORIZED)
                                    .message("Unauthorized")
                                    .success(false)
                                    .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                                    .build();

                            response.setContentType("application/json");
                            response.setStatus(HttpStatus.UNAUTHORIZED.value());
                            new ObjectMapper().writeValue(response.getOutputStream(), resp);
                        })
                        // 403 Forbidden
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            Response resp = Response.builder()
                                    .code(HttpStatus.FORBIDDEN.value())
                                    .status(HttpStatus.FORBIDDEN)
                                    .message("Forbidden")
                                    .success(false)
                                    .timestamp(localDateTimeFormatter(LocalDateTime.now()))
                                    .build();

                            response.setContentType("application/json");
                            response.setStatus(HttpStatus.FORBIDDEN.value());
                            new ObjectMapper().writeValue(response.getOutputStream(), resp);
                        })
                )
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
