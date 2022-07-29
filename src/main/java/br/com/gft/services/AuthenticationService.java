package br.com.gft.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;

import br.com.gft.dto.auth.AuthDTO;
import br.com.gft.dto.token.TokenDTO;
import br.com.gft.entities.User;

@Service
public class AuthenticationService {

	@Autowired @Lazy
	private AuthenticationManager authManager;

	@Value("${desafio-api.jwt.secret}")
	private String secret;

	@Value("${desafio-api.jwt.expiration}")
	private String expiration;

	@Value("${desafio-api.jwt.issuer}")
	private String issuer;

	public TokenDTO authenticate(AuthDTO authDTO) throws AuthenticationException {
		Authentication authenticate = authManager
				.authenticate(new UsernamePasswordAuthenticationToken(authDTO.getEmail(), authDTO.getPassword()));
		String token = generateToken(authenticate);
		return new TokenDTO(token);

	}

	private Algorithm createAlgorithm() {
		return Algorithm.HMAC256(secret);
	}

	private String generateToken(Authentication authenticate) {
		User principal = (User) authenticate.getPrincipal();
		Date today = new Date();
		Date expDate = new Date(today.getTime() + Long.parseLong(expiration));
		return JWT.create().withIssuer(issuer).withExpiresAt(expDate).withSubject(principal.getId().toString())
				.sign(this.createAlgorithm());
	}

	public boolean verifyToken(String token) {
		try {
			if (token == null)
				return false;
			JWT.require(this.createAlgorithm()).withIssuer(issuer).build().verify(token);
			return true;
		} catch (JWTVerificationException e) {
			return false;
		}

	}

	public Long getUserId(String token) {
		String subject = JWT.require(this.createAlgorithm()).withIssuer(issuer).build().verify(token).getSubject();
		return Long.parseLong(subject);
	}

}
