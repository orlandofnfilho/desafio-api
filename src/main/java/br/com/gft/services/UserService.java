package br.com.gft.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.gft.entities.User;
import br.com.gft.repositories.UserRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {

	private static final String E_MAIL_JA_CADASTRADO = "E-mail já cadastrado";
	private static final String USUARIO_NAO_ENCONTRADO = "Usuário não encontrado";
	
	private final UserRepository userRepository;
	
	public User findByEmail(String email) {
		Optional<User> optional = userRepository.findByEmail(email);
		if (optional.isEmpty()) {
			throw new RuntimeException(USUARIO_NAO_ENCONTRADO);
		}
		return optional.get();
	}
	
}
