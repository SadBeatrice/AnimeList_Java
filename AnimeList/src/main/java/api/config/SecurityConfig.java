package api.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@Configuration			// Define esta classe como uma configuração do Spring;
@EnableWebSecurity		// Habilita as configurações de segurança web do Spring Security;
@EnableMethodSecurity	// Habilita a segurança baseada em anotações para métodos (@PreAuthorize);
public class SecurityConfig  {

    // Configura a segurança da aplicação, definindo o comportamento das requisições HTTP;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http    
            .csrf(AbstractHttpConfigurer::disable) 				// Desabilita a proteção contra CSRF (necessário para APIs REST)
            .cors(AbstractHttpConfigurer::disable) 				// Desabilita o CORS, pois será configurado manualmente em outro lugar
            .authorizeHttpRequests((requests) -> requests
                .requestMatchers("/api/login").permitAll() 		// Permite acesso sem autenticação ao endpoint de login
                .requestMatchers("/api/register").permitAll() 	// Permite acesso sem autenticação ao endpoint de registro
                .anyRequest().authenticated()) 					// Requer autenticação para todas as outras requisições
            .authenticationProvider(authenticationProvider) 	// Define o AuthenticationProvider para autenticação
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
            .sessionManagement(customizer -> customizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build(); // Retorna a configuração de segurança
    }

    // Injeta JwtAuthenticationFilter
	@Autowired
	private JwtAuthenticationFilter jwtAuthFilter;

    // Injeta o AuthenticationProvider
    @Autowired
    private AuthenticationProvider authenticationProvider;

    // Configura o filtro CORS (Cross-Origin Resource Sharing) para a aplicação
    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter() {
        // Configura as origens, cabeçalhos e métodos permitidos para requisições CORS
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); // Permite enviar credenciais como cookies ou cabeçalhos de autenticação
        config.setAllowedOriginPatterns(Arrays.asList("*")); // Permite origens de qualquer domínio
        config.setAllowedHeaders(Arrays.asList(HttpHeaders.AUTHORIZATION, HttpHeaders.CONTENT_TYPE, HttpHeaders.ACCEPT)); // Permite cabeçalhos específicos
        config.setAllowedMethods(Arrays.asList(HttpMethod.GET.name(), HttpMethod.POST.name(), HttpMethod.PUT.name(), HttpMethod.DELETE.name())); // Permite métodos HTTP específicos
        config.setMaxAge(3600L); // Define o tempo máximo que a configuração CORS pode ser armazenada em cache

        // Registra o filtro CORS com a configuração especificada
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
        bean.setOrder(-102); // Define a ordem de execução do filtro CORS
        return bean; // Retorna o registro do filtro CORS
    }

}
