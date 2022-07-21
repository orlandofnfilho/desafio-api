package br.com.gft.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.gft.dto.dog.DogMapper;
import br.com.gft.dto.dog.DogRequestDTO;
import br.com.gft.dto.dog.DogResponseDTO;
import br.com.gft.entities.Dog;
import br.com.gft.services.DogService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/dogs")
public class DogController {

	private static final String ID = "/{id}";
	private final DogService dogService;

	@PostMapping
	public ResponseEntity<DogResponseDTO> create(@RequestBody DogRequestDTO obj) {
		Dog dog = dogService.create(DogMapper.fromDTO(obj));
		return ResponseEntity.ok().body(DogMapper.fromEntity(dog));
	}

	@GetMapping(ID)
	public ResponseEntity<DogResponseDTO> findById(@PathVariable Long id) {
		Dog dog = dogService.findById(id);
		DogResponseDTO response = DogMapper.fromEntity(dog);
		return ResponseEntity.ok().body(response);
	}

	@GetMapping
	public ResponseEntity<Page<DogResponseDTO>> findAll(@PageableDefault(size = 10) Pageable pageable) {
		Page<DogResponseDTO> response = dogService.findAll(pageable).map(DogMapper::fromEntity);
		return ResponseEntity.ok().body(response);

	}

	@PutMapping(ID)
	public ResponseEntity<DogResponseDTO> update(@PathVariable Long id, @RequestBody DogRequestDTO obj) {
		Dog dog = dogService.update(id, DogMapper.fromDTO(obj));
		DogResponseDTO response = DogMapper.fromEntity(dog);
		return ResponseEntity.ok().body(response);
	}

	@DeleteMapping(ID)
	public ResponseEntity<DogResponseDTO> delete(@PathVariable Long id) {
		dogService.delete(id);
		return ResponseEntity.noContent().build();
	}

}
