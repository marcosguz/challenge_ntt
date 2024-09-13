package com.bank.wealthstream;

import static org.springframework.security.config.Customizer.withDefaults;

import java.util.HashMap;
import java.util.List;

import com.bank.wealthstream.security.JwtAuthenticationEntryPoint;
import com.bank.wealthstream.security.JwtRequestFilter;
import org.apache.coyote.http2.Http2Protocol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
	@Value("${cors.rest}")
	private List<String> restCors;

	@Value("${cors.rest.allowed-headers}")
	private List<String> restHeaders;

	@Value("${cors.rest.allowed-methods}")
	private List<String> restMethods;

	@Autowired
	private JwtAuthenticationEntryPoint jwtAEP;

	@Autowired
	private JwtRequestFilter jwtFilter;
//
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(withDefaults()).authorizeHttpRequests().requestMatchers("/api/auth/login").permitAll();
		http.csrf(withDefaults()).authorizeHttpRequests().requestMatchers("/api/customer/**").permitAll();
		http.csrf(withDefaults()).authorizeHttpRequests().requestMatchers("/api/account/**").permitAll();
		http.csrf(withDefaults()).authorizeHttpRequests().requestMatchers("/api/account-movement/movement/**").permitAll();
		http.csrf(AbstractHttpConfigurer::disable).authorizeHttpRequests().requestMatchers("/api/**").authenticated().and()
				.exceptionHandling(handling -> handling.authenticationEntryPoint(jwtAEP))
				.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
		http.cors(withDefaults());
		return http.build();
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

		// HTTP CORS
		final CorsConfiguration cnf = new CorsConfiguration();
		cnf.setAllowedOrigins(restCors);
		cnf.setAllowedMethods(restMethods);
		cnf.setAllowedHeaders(restHeaders);

		HashMap<String, CorsConfiguration> corsConf = new HashMap<>();
		corsConf.put("/api/**", cnf);
		source.setCorsConfigurations(corsConf);

		return source;
	}

	@Bean
	ConfigurableServletWebServerFactory tomcatCustomizer() {
		TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
		factory.addConnectorCustomizers(connector -> connector.addUpgradeProtocol(new Http2Protocol()));
		return factory;
	}

	@Bean
	MultipartResolver multipartResolver() {
		StandardServletMultipartResolver resolver = new StandardServletMultipartResolver();
		return resolver;
	}
}