package br.com.gft.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
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
	
	private final DogService dogService;
	
	
	@PostMapping
	public ResponseEntity<DogResponseDTO> create(@RequestBody DogRequestDTO obj){
		Dog dog = dogService.create(DogMapper.fromDTO(obj));
		return ResponseEntity.ok().body(DogMapper.fromEntity(dog));
	}

}
