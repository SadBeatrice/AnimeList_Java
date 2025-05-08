package api.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/login")
@CrossOrigin(origins = "http://localhost:4200")
public class LoginController {

	// Injeta o LoginService
	@Autowired
	private LoginService loginService;

	// Autentica o usuário e retorna o token JWT
	@PostMapping
	public ResponseEntity<String> logar(@RequestBody Login login) {
		String token = loginService.logar(login);
		return new ResponseEntity<>(token, HttpStatus.OK);
	}
}
