package practicas.gestion_personal.auth;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import practicas.gestion_personal.auth.filters.JwtAuthenticationFilter;
import practicas.gestion_personal.auth.filters.JwtValidationFilter;

@Configuration
@AllArgsConstructor
public class SpringSecurityConfig {
    private AuthenticationConfiguration authenticationConfiguration;
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    AuthenticationManager authenticationManager()throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http)throws Exception{
        http
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.GET, "/user/").permitAll()
                .requestMatchers("/swagger-ui/**",
                        "/swagger-resources/*",
                        "/v3/api-docs/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(new JwtAuthenticationFilter(authenticationConfiguration.getAuthenticationManager()))
                .addFilter(new JwtValidationFilter(authenticationConfiguration.getAuthenticationManager()))
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(management->management.sessionCreationPolicy(SessionCreationPolicy.STATELESS));


        return http.build();

    }
}
