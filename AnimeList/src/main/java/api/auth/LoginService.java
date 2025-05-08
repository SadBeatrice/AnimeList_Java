//AuthenticationService.java
package api.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import api.config.JwtServiceGenerator;

@Service
public class LoginService {

	// Injeção das dependências necessárias: LoginRepository, JwtServiceGenerator e AuthenticationManager
	@Autowired
	private LoginRepository repository;
	@Autowired
	private JwtServiceGenerator jwtService;
	@Autowired
	private AuthenticationManager authenticationManager;


    // Realiza o processo de autenticação e retorna um token JWT
    public String logar(Login login) {
        String token = this.gerarToken(login);
        return token;
    }

    // Autentica as credenciais do usuário e gera o token JWT correspondente
    public String gerarToken(Login login) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                login.getUsername(),
                login.getPassword()
            )
        );
        Usuario user = repository.findByUsername(login.getUsername()).get();
        String jwtToken = jwtService.generateToken(user);
        return jwtToken;
    }
}
