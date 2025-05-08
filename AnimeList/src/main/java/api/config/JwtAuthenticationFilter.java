package api.config;
//JwtAuthenticationFilter.java

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component // Define a classe como um componente: será automaticamente injetada onde necessário;
public class JwtAuthenticationFilter extends OncePerRequestFilter { // Filtro que será executado uma única vez por requisição

	// Injeta o JwtServiceGenerator: Serviço responsável por gerar e validar tokens JWT;
    @Autowired
    private JwtServiceGenerator jwtService;

    // Injeta o UserDetailsService: Serviço que carrega os dados do usuário a partir do banco;
    @Autowired
    private UserDetailsService userDetailsService; 

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request, 	// Objeto que representa a requisição HTTP
            @NonNull HttpServletResponse response, 	// Objeto que representa a resposta HTTP
            @NonNull FilterChain filterChain 		// Cadeia de filtros que será executada
    ) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization"); // Extrai o cabeçalho "Authorization" da requisição
        final String jwt; 		// Armazena o token JWT
        final String userEmail; // Armazena o nome de usuário extraído do token

        // Se não houver cabeçalho ou ele não começar com Bearer pula este filtro
        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        // Remove o prefixo "Bearer " e obtém apenas o token
        jwt = authHeader.substring(7);

        // Extrai o nome de usuário (subject) do token
        userEmail = jwtService.extractUsername(jwt);

        // Se o nome de usuário foi extraído com sucesso e não há autenticação ativa no contexto
        if(userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Carrega os detalhes do usuário com base no nome de usuário
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            
            // Verifica se o token é válido
            if(jwtService.isTokenValid(jwt, userDetails)) {
            	
                // Cria um objeto de autenticação contendo o usuário e suas permissões
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                // Adiciona informações adicionais sobre a requisição (como IP, navegador, etc.)
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                // Define o usuário autenticado no contexto de segurança do Spring
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        // Continua com a execução da cadeia de filtros
        filterChain.doFilter(request, response);
    }
}

