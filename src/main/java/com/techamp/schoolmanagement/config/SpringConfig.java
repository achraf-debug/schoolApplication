package com.techamp.schoolmanagement.config;


import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
public class SpringConfig  {
	
	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http,  HandlerMappingIntrospector introspector) throws Exception {
		// Permit All Requests inside the Web Application
//		http.authorizeHttpRequests(requests -> requests.anyRequest().permitAll())
//		.formLogin(Customizer.withDefaults())
//		.httpBasic(Customizer.withDefaults());
//
		// Deny All Requests inside the Web Application
//            http.authorizeHttpRequests(requests -> requests.anyRequest().denyAll())
//                .formLogin(Customizer.withDefaults())
//                .httpBasic(Customizer.withDefaults());
		
		MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(introspector);
		
		http.csrf((csrf) -> csrf.ignoringRequestMatchers(mvcMatcherBuilder.pattern("/saveMsg"))
		.ignoringRequestMatchers(mvcMatcherBuilder.pattern("/public/**"))
		.ignoringRequestMatchers(PathRequest.toH2Console()))
		.authorizeHttpRequests((requests) -> requests.requestMatchers(mvcMatcherBuilder.pattern("/dashboard")).authenticated()
		.requestMatchers(mvcMatcherBuilder.pattern("/displayMessages")).hasRole("ADMIN")
		.requestMatchers(mvcMatcherBuilder.pattern("/closeMsg/**")).hasRole("ADMIN")
		.requestMatchers(mvcMatcherBuilder.pattern("/admin/**")).hasRole("ADMIN")
		.requestMatchers(mvcMatcherBuilder.pattern("/student/**")).hasRole("STUDENT")
		.requestMatchers(mvcMatcherBuilder.pattern("")).permitAll()
		.requestMatchers("/displayProfile").authenticated()
		.requestMatchers("/updateProfile").authenticated()
		.requestMatchers(mvcMatcherBuilder.pattern("/public/**")).permitAll()
		.requestMatchers(mvcMatcherBuilder.pattern("/")).permitAll()
		.requestMatchers(mvcMatcherBuilder.pattern("/home")).permitAll()
		.requestMatchers(mvcMatcherBuilder.pattern("/holidays/**")).permitAll()
		.requestMatchers(mvcMatcherBuilder.pattern("/contact")).permitAll()
		.requestMatchers(mvcMatcherBuilder.pattern("/saveMsg")).permitAll()
		.requestMatchers(mvcMatcherBuilder.pattern("/courses")).permitAll()
		.requestMatchers(mvcMatcherBuilder.pattern("/about")).permitAll()
		.requestMatchers(mvcMatcherBuilder.pattern("/assets/**")).permitAll()
		.requestMatchers(mvcMatcherBuilder.pattern("/login")).permitAll()
		.requestMatchers(mvcMatcherBuilder.pattern("/logout")).permitAll()
		.requestMatchers(PathRequest.toH2Console()).permitAll())
		.formLogin(loginConfigurer -> loginConfigurer.loginPage("/login")
		.defaultSuccessUrl("/dashboard").failureUrl("/login?error=true").permitAll())
		.logout(logoutConfigurer -> logoutConfigurer.logoutSuccessUrl("/login?logout=true")
		.invalidateHttpSession(true).permitAll())
		.httpBasic(Customizer.withDefaults());
		
		http.headers(headersConfigurer -> headersConfigurer
		.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));
		
		return http.build();
	}
	
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	@Bean
	public InMemoryUserDetailsManager userDetailsService() {
		
		UserDetails user = User.withDefaultPasswordEncoder()
		.username("user")
		.password("12345")
		.roles("USER")
		.build();
		UserDetails admin = User.withDefaultPasswordEncoder()
		.username("admin")
		.password("54321")
		.roles("ADMIN")
		.build();
		return new InMemoryUserDetailsManager(user, admin);
	}
}
