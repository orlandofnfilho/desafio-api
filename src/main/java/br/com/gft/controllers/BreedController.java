package br.com.gft.controllers;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

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
	
	
	private static final String ID = "/{id}";
	private final BreedService breedService;
	
	@PostMapping
	public ResponseEntity<BreedResponseDTO> create(@RequestBody BreedRequestDTO obj){
		Breed breed = breedService.create(BreedMapper.fromDTO(obj));
		BreedResponseDTO response = BreedMapper.fromEntity(breed);
		response.setId(breed.getId());
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path(ID).buildAndExpand(breed.getId()).toUri();
		return ResponseEntity.created(uri).body(response);
		
	}
	
	@GetMapping(ID)
	public ResponseEntity<BreedResponseDTO> findById(@PathVariable Long id){
		Breed breed = breedService.findById(id);
		BreedResponseDTO response = BreedMapper.fromEntity(breed);
		return ResponseEntity.ok().body(response);
		
	}
	
	@GetMapping
	public ResponseEntity<List<BreedResponseDTO>> findAll(){
		List<BreedResponseDTO> response = breedService.findAll().stream()
				.map(b -> BreedMapper.fromEntity(b)).collect(Collectors.toList());
		return ResponseEntity.ok().body(response);
	}
	
	
	@PutMapping(ID)
	public ResponseEntity<BreedResponseDTO> update(@PathVariable Long id, @RequestBody BreedRequestDTO obj){
		Breed breed = breedService.update(id, BreedMapper.fromDTO(obj));
		BreedResponseDTO response = BreedMapper.fromEntity(breed);
		return ResponseEntity.ok().body(response);
	}
	
	@DeleteMapping(ID)
	public ResponseEntity<BreedResponseDTO> delete(@PathVariable Long id){
		breedService.delete(id);
		return ResponseEntity.noContent().build();
	}

}
