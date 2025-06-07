package com.example.demo.config;

import com.example.demo.DAO.AdministratorRepository;
import com.example.demo.DAO.UserRepository;
import com.example.demo.entity.AuthUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdministratorRepository administratorRepository;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            AuthUser user = userRepository.findByUsername(username);
            if (user != null) {
                return user;
            }
            user = administratorRepository.findByUsername(username);
            if (user == null) {
                throw new UsernameNotFoundException("User or Administrator not found: " + username);
            }
            return user;
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/login", "/css/**", "/js/**", "/signup").permitAll()
                        .requestMatchers("/administrators/**", "/admin/**", "/api/v1/administrators/**").hasRole("ADMIN") // Protect all admin routes
                        .requestMatchers("/api/v1/**").authenticated()
                        .requestMatchers("/users/**", "/products/**").hasAnyRole("USER", "ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/home", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                )
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/api/v1/**")
                )
                .exceptionHandling(ex -> ex
                        .accessDeniedPage("/access-denied")
                );
        return http.build();
    }
}
