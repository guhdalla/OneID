package br.com.fiap.oneid.service;

import java.util.Date;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import br.com.fiap.oneid.model.Usuario;
import br.com.fiap.oneid.model.UsuarioFisico;
import br.com.fiap.oneid.model.UsuarioJuridico;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Service
public class TokenService {

	private String secret = "ipfwxnitkzomvapmwfteowxjzbhfssnmpjkagtgvkewycrbiet";
	
	@Autowired
	private UsuarioService serviceUsuario;
	
	@Autowired
	private UsuarioFisicoService serviceFisico;
	
	@Autowired
	private UsuarioJuridicoService serviceJuridico;

	public String getToken(Authentication authhenticate) {
		
		Usuario usuario = (Usuario) authhenticate.getPrincipal();
		
		Date today = new Date();
		long duration = 60000 * 1440;
		Date expirationDate = new Date(today.getTime() + duration);
		return Jwts.builder()
						.setIssuer("OneID")
						.setSubject(usuario.getIdUsuario().toString())
						.setIssuedAt(today)
						.setExpiration(expirationDate)
						.signWith(SignatureAlgorithm.HS256, secret)
					.compact()
		;
	}

	public boolean isValid(String token) {
		try {
			Jwts.parser()
				.setSigningKey(this.secret)
				.parseClaimsJws(token);
			return true;
		} catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException
				| IllegalArgumentException e) {
			return false;
		}
	}

	public Long getIdUsuario(String token) {
		Claims data = Jwts.parser()
			.setSigningKey(this.secret)
			.parseClaimsJws(token)
			.getBody();
		
		return Long.valueOf(data.getSubject());
	}
	
	public Object findByToken(String token) {
		if(token == null) return null;
		Long id = getIdUsuario(token);
		Usuario usuario = serviceUsuario.findById(id);
		if(usuario == null) return null;

		switch (usuario.getRoles().get(0).getAuthority()) {
		case "ROLE_FISICO":
			
			Optional<UsuarioFisico> usuarioFisico = serviceFisico.findById(usuario.getIdUsuario());
			if(usuarioFisico.isEmpty()) return null;
			return usuarioFisico.get();
			
		case "ROLE_JURIDICO":
			
			Optional<UsuarioJuridico> usuarioJuridico = serviceJuridico.findById(usuario.getIdUsuario());
			if(usuarioJuridico.isEmpty()) return null;
			return usuarioJuridico.get();
			
		default:
			return null;
		}
	}
	
	public String extractToken(HttpServletRequest request) {
		String header = request.getHeader("Authorization");
		if (header == null || header.isEmpty() || !header.startsWith("Bearer "))
			return null;
		
		return header.substring(7, header.length());
		
	}

}
