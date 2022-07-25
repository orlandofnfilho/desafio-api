package br.com.gft.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.gft.entities.Profile;
import br.com.gft.entities.User;
import br.com.gft.exceptions.BusinessRuleException;
import br.com.gft.exceptions.ResourceNotFoundException;
import br.com.gft.repositories.ProfileRepository;
import br.com.gft.repositories.UserRepository;

@Service
@Transactional
public class UserService implements UserDetailsService {

	private static final String E_MAIL_JA_CADASTRADO = "E-mail já cadastrado";
	private static final String USUARIO_NAO_ENCONTRADO = "Usuário não encontrado";

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ProfileRepository profileRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return findByEmail(username);
	}

	public User create(User obj) {
		validEmail(obj.getEmail());
		Optional<Profile> profile = profileRepository.findById(2L);
		obj.setProfile(profile.get());
		obj.setPassword(new BCryptPasswordEncoder().encode(obj.getPassword()));
		return userRepository.save(obj);
	}

	@Transactional(readOnly = true)
	public User findByEmail(String email) {
		Optional<User> optional = userRepository.findByEmail(email);
		if (optional.isEmpty()) {
			throw new UsernameNotFoundException(USUARIO_NAO_ENCONTRADO);
		}
		return optional.get();
	}

	@Transactional(readOnly = true)
	public User findById(Long id) {
		Optional<User> optional = userRepository.findById(id);
		if (optional.isEmpty()) {
			throw new ResourceNotFoundException(USUARIO_NAO_ENCONTRADO);
		}
		return optional.get();
	}

	@Transactional(readOnly = true)
	public Page<User> findAll(Pageable pageable) {
		return userRepository.findAll(pageable);
	}

	public User update(Long id, User obj) {
		validUpdate(id, obj);
		obj.setId(id);
		obj.setPassword(new BCryptPasswordEncoder().encode(obj.getPassword()));
		return userRepository.save(obj);
	}

	public User changeProfile(Long profileId, Long userId) {
		User userSaved = this.findById(userId);
		Optional<Profile> profileSaved = profileRepository.findById(profileId);
		if (profileSaved.isEmpty()) {
			throw new ResourceNotFoundException("Perfil não encontrado");
		}
		userSaved.setProfile(profileSaved.get());
		return userRepository.save(userSaved);
	}

	public void delete(Long id) {
		User user = this.findById(id);
		userRepository.delete(user);
	}

	@Transactional(readOnly = true)
	public void validEmail(String email) {
		Optional<User> userSaved = userRepository.findByEmail(email);
		if (userSaved.isPresent()) {
			throw new BusinessRuleException(E_MAIL_JA_CADASTRADO);
		}
	}

	@Transactional(readOnly = true)
	public void validUpdate(Long id, User obj) {
		Optional<User> userSaved = userRepository.findByEmail(obj.getEmail());
		Optional<User> userFoundById = userRepository.findById(id);
		if (userSaved.isPresent()) {
			throw new BusinessRuleException(E_MAIL_JA_CADASTRADO);
		}
		if (userFoundById.isEmpty()) {
			throw new ResourceNotFoundException(USUARIO_NAO_ENCONTRADO);
		}
	}

}
