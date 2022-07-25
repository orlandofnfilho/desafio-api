package br.com.gft.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.gft.entities.User;
import br.com.gft.exceptions.ResourceNotFoundException;
import br.com.gft.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService {

	private static final String E_MAIL_JA_CADASTRADO = "E-mail já cadastrado";
	private static final String USUARIO_NAO_ENCONTRADO = "Usuário não encontrado";

	@Autowired
	private  UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return findByEmail(username);
	}

	public User findByEmail(String email) {
		Optional<User> optional = userRepository.findByEmail(email);
		if (optional.isEmpty()) {
			throw new UsernameNotFoundException(USUARIO_NAO_ENCONTRADO);
		}
		return optional.get();
	}

	public User findById(Long id) {
		Optional<User> optional = userRepository.findById(id);
		if (optional.isEmpty()) {
			throw new ResourceNotFoundException(USUARIO_NAO_ENCONTRADO);
		}
		return optional.get();
	}

}
