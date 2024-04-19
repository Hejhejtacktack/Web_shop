package com.example.web_shop.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    DataSource dataSource;

    private final UserDetailsServiceImpl myUserDetailsServiceImpl;

    public SecurityConfig(UserDetailsServiceImpl myUserDetailsServiceImpl) {
        this.myUserDetailsServiceImpl = myUserDetailsServiceImpl;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/login", "/register").permitAll()
                        .requestMatchers("/products/add").hasAuthority("ROLE_ADMIN")
                        .anyRequest().authenticated()
                )
                .userDetailsService(myUserDetailsServiceImpl)
                .httpBasic(Customizer.withDefaults())
                .formLogin((formLogin) -> formLogin
                        .loginPage("/login"))
                .logout(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public UserDetailsManager users(DataSource dataSource, PasswordEncoder passwordEncoder) {
        UserDetails user = User.builder()
                .username("user")
                .password(passwordEncoder.encode("userpw"))
                .roles("USER")
                .build();
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("adminpw"))
                .roles("USER", "ADMIN")
                .build();

        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
        if (!users.userExists(user.getUsername()))
            users.createUser(user);
        if (!users.userExists(admin.getUsername()))
            users.createUser(admin);
        return users;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }
}

