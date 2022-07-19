package br.com.gft.controllers;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
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
@RequestMapping("/api/v1/veterinarians")
public class VeterinarianController {

	private static final String ID = "/{id}";
	private final VeterinarianService veterinarianService;

	@PostMapping
	public ResponseEntity<VeterinarianResponseDTO> create(@RequestBody @Valid VeterinarianRequestDTO obj) {
		Veterinarian vet = veterinarianService.create(VeterinarianMapper.fromDTO(obj));
		VeterinarianResponseDTO response = VeterinarianMapper.fromEntity(vet);
		response.setId(vet.getId());
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path(ID).buildAndExpand(vet.getId()).toUri();
		return ResponseEntity.created(uri).body(response);

	}

	@GetMapping(ID)
	public ResponseEntity<VeterinarianResponseDTO> findById(@PathVariable Long id) {
		Veterinarian vet = veterinarianService.findById(id);
		VeterinarianResponseDTO response = VeterinarianMapper.fromEntity(vet);
		return ResponseEntity.ok().body(response);

	}

	@GetMapping
	public ResponseEntity<List<VeterinarianResponseDTO>> findAll() {
		List<VeterinarianResponseDTO> response = veterinarianService.findAll().stream()
				.map(v -> VeterinarianMapper.fromEntity(v)).collect(Collectors.toList());
		return ResponseEntity.ok().body(response);

	}

	@PutMapping(ID)
	public ResponseEntity<VeterinarianResponseDTO> update(@PathVariable Long id,
			@RequestBody @Valid VeterinarianRequestDTO obj) {
		Veterinarian vet = veterinarianService.update(id, VeterinarianMapper.fromDTO(obj));
		VeterinarianResponseDTO response = VeterinarianMapper.fromEntity(vet);
		return ResponseEntity.ok().body(response);
	}

	@DeleteMapping(ID)
	public ResponseEntity<VeterinarianResponseDTO> delete(@PathVariable Long id) {
		veterinarianService.delete(id);
		return ResponseEntity.noContent().build();
	}

}
