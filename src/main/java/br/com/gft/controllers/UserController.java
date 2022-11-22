package br.com.gft.controllers;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.gft.controllers.docs.UserControllerDoc;
import br.com.gft.dto.user.UserMapper;
import br.com.gft.dto.user.UserRequestDTO;
import br.com.gft.dto.user.UserResponseDTO;
import br.com.gft.entities.User;
import br.com.gft.services.UserService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/users")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController implements UserControllerDoc{

	private static final String HAS_AUTHORITY_ADMIN = "hasAuthority('ADMIN')";
	private static final String HAS_ANY_AUTHORITY_ADMIN_USUARIO = "hasAnyAuthority('ADMIN','USUARIO')";
	private static final String ID = "/{id}";
	private final UserService userService;

	@PostMapping
	public ResponseEntity<UserResponseDTO> create(@RequestBody @Valid UserRequestDTO dto) {
		User user = userService.create(UserMapper.fromDTO(dto));
		UserResponseDTO response = UserMapper.fromEntity(user);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path(ID).buildAndExpand(response.getId()).toUri();
		return ResponseEntity.created(uri).body(response);
	}

	@GetMapping(ID)
	@PreAuthorize(HAS_AUTHORITY_ADMIN)
	public ResponseEntity<UserResponseDTO> findById(@PathVariable Long id) {
		User user = userService.findById(id);
		UserResponseDTO response = UserMapper.fromEntity(user);
		return ResponseEntity.ok().body(response);
	}

	@GetMapping
	@PreAuthorize(HAS_AUTHORITY_ADMIN)
	public ResponseEntity<Page<UserResponseDTO>> findAll(@PageableDefault(size = 10) Pageable pageable) {
		Page<UserResponseDTO> response = userService.findAll(pageable).map(UserMapper::fromEntity);
		return ResponseEntity.ok().body(response);

	}

	@PutMapping(ID)
	@PreAuthorize(HAS_ANY_AUTHORITY_ADMIN_USUARIO)
	public ResponseEntity<UserResponseDTO> update(@PathVariable Long id, @RequestBody @Valid UserRequestDTO dto) {
		User user = userService.update(id, UserMapper.fromDTO(dto));
		UserResponseDTO response = UserMapper.fromEntity(user);
		return ResponseEntity.ok().body(response);
	}
	
	@PatchMapping(ID)
	@PreAuthorize(HAS_AUTHORITY_ADMIN)
	public ResponseEntity<UserResponseDTO> changeProfile(@PathVariable Long id, @RequestParam Long profileId){
		User user = userService.changeProfile(id, profileId);
		UserResponseDTO response = UserMapper.fromEntity(user);
		return ResponseEntity.ok().body(response);
	}

	@DeleteMapping(ID)
	@PreAuthorize(HAS_AUTHORITY_ADMIN)
	public ResponseEntity<UserResponseDTO> delete(@PathVariable Long id) {
		userService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
