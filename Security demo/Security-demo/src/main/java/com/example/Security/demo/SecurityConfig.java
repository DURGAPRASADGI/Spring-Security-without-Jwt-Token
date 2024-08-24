package com.example.Security.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
	
	@Autowired
	DataSource dataSource;
	
	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		http.sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		http.authorizeHttpRequests((requests) -> requests.anyRequest().authenticated());
       // http.formLogin(withDefaults());
        http.httpBasic(withDefaults());
		return http.build();
	}
//details  has store in memory
//	@Bean
//	 UserDetailsService userDetailsService() {
//		UserDetails user1=User
//				          .withUsername("user")
//				          .password(encoder().encode("1234"))
//				          .roles("USER")
//				          .build();
//		
//		UserDetails admin=User
//		          .withUsername("admin")
//		          .password(encoder().encode("9876"))
//		          .roles("ADMIN")
//		          .build();
//
//	 
//		return new InMemoryUserDetailsManager(user1,admin);
//		
//	}
//	
	// jdbcAuthentication
	@Bean
	 UserDetailsService userDetailsService() {
		UserDetails user1=User
				          .withUsername("user")
				          .password(encoder().encode("1234"))
				          .roles("USER")
				          .build();
		
		UserDetails admin=User
		          .withUsername("admin")
		          .password(encoder().encode("9876"))
		          .roles("ADMIN")
		          .build();
JdbcUserDetailsManager userDetailsManager=new JdbcUserDetailsManager(dataSource);
if (!userDetailsManager.userExists(user1.getUsername())) {
    userDetailsManager.createUser(user1);
}

if (!userDetailsManager.userExists(admin.getUsername())) {
    userDetailsManager.createUser(admin);
}

	 
		return userDetailsManager;
		
	}
	
	@Bean
     PasswordEncoder encoder() {
    	 return new BCryptPasswordEncoder();
     }
}
