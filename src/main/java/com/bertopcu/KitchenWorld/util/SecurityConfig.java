package com.bertopcu.KitchenWorld.util;

import com.bertopcu.KitchenWorld.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.ArrayList;
import java.util.List;


@Configuration
public class SecurityConfig
{
    @Autowired
    UserService userService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests().antMatchers("/users/login", "/users/signup", "/public/**", "/api-docs/**", "/swagger-ui/**").permitAll().anyRequest().authenticated()
                .and()
                .cors().configurationSource(corsConfigurationSource()).and()
                .httpBasic()
                .and().formLogin().failureHandler(authenticationFailureHandler())
                .and().exceptionHandling().accessDeniedHandler(accessDeniedHandler());

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        List<UserDetails> users = new ArrayList<UserDetails>();
        List<com.bertopcu.KitchenWorld.model.User> userList = userService.listAllUser();

        for(com.bertopcu.KitchenWorld.model.User usr: userList) {
            String role = usr.getType() == 1 ? "ADMIN" : "USER";
            UserDetails user = User
                    .withUsername(usr.getuName())
                    .password("{noop}"+usr.getPwd())
                    .roles(role)
                    .build();
            users.add(user);
        }

        return new InMemoryUserDetailsManager(users);
    }
}
