package br.com.fiap.oneid.service;

import java.util.Date;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import br.com.fiap.oneid.model.Usuario;
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

}
