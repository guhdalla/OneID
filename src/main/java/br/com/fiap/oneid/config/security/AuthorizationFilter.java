package br.com.fiap.oneid.config.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.fiap.oneid.model.Usuario;
import br.com.fiap.oneid.service.TokenService;
import br.com.fiap.oneid.service.UsuarioService;

public class AuthorizationFilter extends OncePerRequestFilter {

	private TokenService tokenService;
	
	private UsuarioService usuarioService;
	
	public AuthorizationFilter(TokenService tokenService, UsuarioService usuarioService) {
		this.tokenService = tokenService;
		this.usuarioService = usuarioService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String token = extractToken(request);
		
		boolean valid = tokenService.isValid(token);
		
		if(valid) authorizeUser(token);
		
		filterChain.doFilter(request, response);
	}

	private void authorizeUser(String token) {
		Long id = tokenService.getIdUsuario(token);
		
		Usuario usuario = usuarioService.findById(id);
		
		UsernamePasswordAuthenticationToken authentication = 
				new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	private String extractToken(HttpServletRequest request) {
		String header = request.getHeader("Authorization");
		if (header == null || header.isEmpty() || !header.startsWith("Bearer "))
			return null;
		
		return header.substring(7, header.length());
		
	}

}
