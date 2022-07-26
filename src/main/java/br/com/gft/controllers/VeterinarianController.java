package br.com.gft.controllers;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.gft.dto.veterinarian.VeterinarianMapper;
import br.com.gft.dto.veterinarian.VeterinarianRequestDTO;
import br.com.gft.dto.veterinarian.VeterinarianResponseDTO;
import br.com.gft.entities.Veterinarian;
import br.com.gft.services.VeterinarianService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/vetApi/v1/veterinarians")
public class VeterinarianController {

	private static final String HAS_AUTHORITY_ADMIN = "hasAuthority('ADMIN')";
	private static final String HAS_ANY_AUTHORITY_ADMIN_USUARIO = "hasAnyAuthority('ADMIN','USUARIO')";
	private static final String ID = "/{id}";
	private final VeterinarianService veterinarianService;

	@PostMapping
	@PreAuthorize(HAS_AUTHORITY_ADMIN)
	public ResponseEntity<VeterinarianResponseDTO> create(@RequestBody @Valid VeterinarianRequestDTO obj) {
		Veterinarian vet = veterinarianService.create(VeterinarianMapper.fromDTO(obj));
		VeterinarianResponseDTO response = VeterinarianMapper.fromEntity(vet);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path(ID).buildAndExpand(response.getId()).toUri();
		return ResponseEntity.created(uri).body(response);

	}

	@GetMapping(ID)
	@PreAuthorize(HAS_AUTHORITY_ADMIN)
	public ResponseEntity<VeterinarianResponseDTO> findById(@PathVariable Long id) {
		Veterinarian vet = veterinarianService.findById(id);
		VeterinarianResponseDTO response = VeterinarianMapper.fromEntity(vet);
		return ResponseEntity.ok().body(response);

	}

	@GetMapping
	@PreAuthorize(HAS_ANY_AUTHORITY_ADMIN_USUARIO)
	public ResponseEntity<Page<VeterinarianResponseDTO>> findAll(@PageableDefault(size = 10) Pageable pageable) {
		Page<VeterinarianResponseDTO> response = veterinarianService.findAll(pageable)
				.map(VeterinarianMapper::fromEntity);
		return ResponseEntity.ok().body(response);

	}

	@PutMapping(ID)
	@PreAuthorize(HAS_AUTHORITY_ADMIN)
	public ResponseEntity<VeterinarianResponseDTO> update(@PathVariable Long id,
			@RequestBody @Valid VeterinarianRequestDTO obj) {
		Veterinarian vet = veterinarianService.update(id, VeterinarianMapper.fromDTO(obj));
		VeterinarianResponseDTO response = VeterinarianMapper.fromEntity(vet);
		return ResponseEntity.ok().body(response);
	}

	@DeleteMapping(ID)
	@PreAuthorize(HAS_AUTHORITY_ADMIN)
	public ResponseEntity<VeterinarianResponseDTO> delete(@PathVariable Long id) {
		veterinarianService.delete(id);
		return ResponseEntity.noContent().build();
	}

}
