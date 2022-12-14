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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.gft.controllers.docs.DogControllerDoc;
import br.com.gft.dto.dog.DogMapper;
import br.com.gft.dto.dog.DogRequestDTO;
import br.com.gft.dto.dog.DogResponseDTO;
import br.com.gft.entities.Dog;
import br.com.gft.services.DogService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/dogs")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class DogController implements DogControllerDoc{
	
	private static final String HAS_AUTHORITY_ADMIN = "hasAuthority('ADMIN')";
	private static final String ID = "/{id}";
	private final DogService dogService;

	@PostMapping
	@PreAuthorize(HAS_AUTHORITY_ADMIN)
	public ResponseEntity<DogResponseDTO> create(@RequestBody @Valid DogRequestDTO obj) {
		Dog dog = dogService.create(DogMapper.fromDTO(obj));
		DogResponseDTO response = DogMapper.fromEntity(dog);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path(ID).buildAndExpand(response.getId()).toUri();
		return ResponseEntity.created(uri).body(response);
	}

	@GetMapping(ID)
	@PreAuthorize(HAS_AUTHORITY_ADMIN)
	public ResponseEntity<DogResponseDTO> findById(@PathVariable Long id) {
		DogResponseDTO response = DogMapper.fromEntity(dogService.findById(id));
		return ResponseEntity.ok().body(response);
	}

	@GetMapping
	@PreAuthorize(HAS_AUTHORITY_ADMIN)
	public ResponseEntity<Page<DogResponseDTO>> findAll(@PageableDefault(size = 10) Pageable pageable) {
		Page<DogResponseDTO> response = dogService.findAll(pageable).map(DogMapper::fromEntity);
		return ResponseEntity.ok().body(response);

	}

	@PutMapping(ID)
	@PreAuthorize(HAS_AUTHORITY_ADMIN)
	public ResponseEntity<DogResponseDTO> update(@PathVariable Long id, @RequestBody @Valid DogRequestDTO obj) {
		Dog dog = dogService.update(id, DogMapper.fromDTO(obj));
		DogResponseDTO response = DogMapper.fromEntity(dog);
		return ResponseEntity.ok().body(response);
	}

	@DeleteMapping(ID)
	@PreAuthorize(HAS_AUTHORITY_ADMIN)
	public ResponseEntity<DogResponseDTO> delete(@PathVariable Long id) {
		dogService.delete(id);
		return ResponseEntity.noContent().build();
	}

}
