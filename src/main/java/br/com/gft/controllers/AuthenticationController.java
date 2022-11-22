package br.com.gft.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.gft.controllers.docs.AuthControllerDoc;
import br.com.gft.dto.auth.AuthDTO;
import br.com.gft.dto.token.TokenDTO;
import br.com.gft.services.AuthenticationService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthenticationController implements AuthControllerDoc {

	private AuthenticationService authenticationService;

	@PostMapping
	public ResponseEntity<TokenDTO> authenticate(@RequestBody AuthDTO authDTO) {
		try {
			return ResponseEntity.ok(authenticationService.authenticate(authDTO));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

	}

}
