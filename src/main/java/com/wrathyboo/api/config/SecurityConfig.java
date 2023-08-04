package com.wrathyboo.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import com.wrathyboo.api.config.JwtAuthenticationFilter;
import com.wrathyboo.api.service.JwtService;

import lombok.RequiredArgsConstructor;

import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	private final JwtAuthenticationFilter jwtAuthFilter;
	private final AuthenticationProvider authenticationProvider;
	private final LogoutHandler logoutHandler;
	
	private static final String[] AUTH_WHITELIST = {
	        "/api/v1/auth/**",
	        "/api/v1/product",
	        "/api/v1/product/{id}",
	        "/api/v1/category",
	        "/api/v1/category/{id}",
	        "/api/v1/uploads",
	        "/api/v1/uploads/**",
	        "/api/v1/cart/**",
	        "/api/v1/cart/add",
	        "/api/v1/cart/remove",
	        "/api/v1/cart/delete/**",
	        "/api/v1/cart/delete",
	        "/api/v1/users"
	};
	private static final String[] AUTH_USER = {
	       
	};

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http.authorizeHttpRequests(auth -> auth
		           .requestMatchers(AUTH_WHITELIST).permitAll()
		           .requestMatchers(HttpMethod.POST, "/api/v1/cart/add").anonymous()
		           .requestMatchers(HttpMethod.GET, "/api/v1/cart/remove").anonymous()
                   .anyRequest().hasAnyAuthority("ADMIN","USER"))
				   .csrf(csrf -> csrf.disable())
			       .sessionManagement((session) -> session
			               .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			       .authenticationProvider(authenticationProvider)
			       .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
			       .logout(logout -> logout 
			    		   .logoutUrl("/api/v1/auth/logout")
			    		   .addLogoutHandler(logoutHandler)
			    		   .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext()))
			       .build();
	}
}