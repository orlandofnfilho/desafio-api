package br.com.gft.controllers;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.gft.dto.breed.BreedMapper;
import br.com.gft.dto.breed.BreedRequestDTO;
import br.com.gft.dto.breed.BreedResponseDTO;
import br.com.gft.entities.Breed;
import br.com.gft.services.BreedService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/breeds")
public class BreedController {
	
	
	private static final String HAS_AUTHORITY_ADMIN = "hasAuthority('ADMIN')";
	private static final String HAS_ANY_AUTHORITY_ADMIN_USUARIO = "hasAnyAuthority('ADMIN','USUARIO')";
	private static final String ID = "/{id}";
	private final BreedService breedService;

	
	@PostMapping
	@PreAuthorize(HAS_AUTHORITY_ADMIN)
	public ResponseEntity<BreedResponseDTO> create(@RequestBody @Valid  BreedRequestDTO obj){
		Breed breed = breedService.create(BreedMapper.fromDTO(obj));
		BreedResponseDTO response = BreedMapper.fromEntity(breed);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path(ID).buildAndExpand(response.getId()).toUri();
		return ResponseEntity.created(uri).body(response);
		
	}
	
	@GetMapping(ID)
	@PreAuthorize(HAS_AUTHORITY_ADMIN)
	public ResponseEntity<BreedResponseDTO> findById(@PathVariable Long id){
		Breed breed = breedService.findById(id);
		BreedResponseDTO response = BreedMapper.fromEntity(breed);
		return ResponseEntity.ok().body(response);
		
	}
	
	@GetMapping
	@PreAuthorize(HAS_ANY_AUTHORITY_ADMIN_USUARIO)
	public ResponseEntity<Page<BreedResponseDTO>> findAll(@PageableDefault(size = 10) Pageable pageable) {
		Page<BreedResponseDTO> response = breedService.findAll(pageable)
				.map(BreedMapper::fromEntity);
		return ResponseEntity.ok().body(response);

	}
	
	
	@PutMapping(ID)
	@PreAuthorize(HAS_AUTHORITY_ADMIN)
	public ResponseEntity<BreedResponseDTO> update(@PathVariable Long id, @RequestBody @Valid BreedRequestDTO obj){
		Breed breed = breedService.update(id, BreedMapper.fromDTO(obj));
		BreedResponseDTO response = BreedMapper.fromEntity(breed);
		return ResponseEntity.ok().body(response);
	}
	
	@DeleteMapping(ID)
	@PreAuthorize(HAS_AUTHORITY_ADMIN)
	public ResponseEntity<BreedResponseDTO> delete(@PathVariable Long id){
		breedService.delete(id);
		return ResponseEntity.noContent().build();
	}

}
