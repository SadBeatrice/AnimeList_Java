package api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import api.auth.LoginRepository;


@Configuration // Define esta classe como uma configuração do Spring;
public class SecurityManager {
	
	// Injeta o LoginRepository
	@Autowired
	private LoginRepository loginRepository;
	
	// Define o Bean para o PasswordEncoder utilizando o BCryptPasswordEncoder;
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// Define o Bean para o AuthenticationProvider, responsável por autenticar o usuário;
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());	// Busca o usuário;
		authProvider.setPasswordEncoder(passwordEncoder());			// Codificador de senha;
		return authProvider;
	}

	// Define o Bean para o AuthenticationManager, necessário para gerenciar a autenticação;
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();	// Retorna o AuthenticationManager configurado;
	}

	// Define o Bean para o UserDetailsService, responsável por carregar os detalhes do usuário para autenticação;
	@Bean
	public UserDetailsService userDetailsService() {
		return username -> loginRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado") );
	}
}
