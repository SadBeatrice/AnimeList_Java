package api.config;
//JwtService.java

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import api.auth.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service // Marca esta classe como um serviço que pode ser injetado em outros componentes do Spring
public class JwtServiceGenerator {  

    // Parâmetros para geração do token
    public static final String SECRET_KEY = "CopoDeChoppDeVidroModeloTulipa200mlParcialmentePreenchidoDeCocaColaZeroCom3PedrasDeGelo"; // Chave secreta utilizada para assinar o token
    public static final SignatureAlgorithm ALGORITMO_ASSINATURA = SignatureAlgorithm.HS256; // Algoritmo de assinatura (HS256)
    public static final int HORAS_EXPIRACAO_TOKEN = 1; // Tempo de expiração do token em horas

    // Método para gerar o payload do token
    public Map<String, Object> gerarPayload(Usuario usuario){
        
        // Criar um mapa com os dados que serão incluídos no payload do token
        Map<String, Object> payloadData = new HashMap<>();
        payloadData.put("username", usuario.getUsername()); 	// Adiciona o nome de usuário
        payloadData.put("id", usuario.getId().toString()); 		// Adiciona o ID do usuário
        payloadData.put("role", usuario.getRole()); 			// Adiciona o papel (role) do usuário
        
        return payloadData; // Retorna o payload do token
    }

    // Método para gerar o token JWT
    public String generateToken(Usuario usuario) {
        
        Map<String, Object> payloadData = this.gerarPayload(usuario); // Gera o payload do token

        // Cria e retorna o token JWT assinado
        return Jwts
                .builder() // Inicia a construção do token JWT
                .setClaims(payloadData) // Define as informações no payload
                .setSubject(usuario.getUsername()) // Define o "subject" do token (usuário)
                .setIssuedAt(new Date(System.currentTimeMillis())) // Define a data de emissão do token
                .setExpiration(new Date(new Date().getTime() + 3600000 * this.HORAS_EXPIRACAO_TOKEN)) // Define a data de expiração (1 hora)
                .signWith(getSigningKey(), this.ALGORITMO_ASSINATURA) // Assina o token com a chave secreta e o algoritmo
                .compact(); // Conclui a construção do token
    }

    // Método privado para extrair todos os claims do token
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder() // Cria um parser para o token
                .setSigningKey(getSigningKey()) // Define a chave secreta usada para validar o token
                .build()
                .parseClaimsJws(token) // Parseia o token JWT
                .getBody(); // Retorna os claims do token
    }

    // Método para validar se o token é válido
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token); // Extrai o nome de usuário do token
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token); // Verifica se o nome de usuário corresponde e se o token não expirou
    }

    // Método para verificar se o token expirou
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date()); // Compara a data de expiração com a data atual
    }

    // Método para extrair a data de expiração do token
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration); // Extrai a data de expiração dos claims do token
    }

    // Método privado para obter a chave secreta para assinar o token
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(this.SECRET_KEY); // Decodifica a chave secreta de Base64
        return Keys.hmacShaKeyFor(keyBytes); // Cria uma chave HMAC para assinar o token
    }

    // Método para extrair o nome de usuário do token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject); // Extrai o nome de usuário do subject dos claims
    }

    // Método genérico para extrair um claim específico do token
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token); // Extrai todos os claims do token
        return claimsResolver.apply(claims); // Aplica a função para extrair um claim específico
    }
}