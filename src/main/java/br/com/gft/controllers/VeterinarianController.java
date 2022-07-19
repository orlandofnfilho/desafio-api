package br.com.gft.controllers;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.gft.dto.veterinarianDTO.VeterinarianMapper;
import br.com.gft.dto.veterinarianDTO.VeterinarianRequestDTO;
import br.com.gft.dto.veterinarianDTO.VeterinarianResponseDTO;
import br.com.gft.entities.Veterinarian;
import br.com.gft.services.VeterinarianService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/veterinarians")
public class VeterinarianController {
	
	private final VeterinarianService veterinarianService;
	
	@PostMapping
	public ResponseEntity<VeterinarianResponseDTO> create(@RequestBody VeterinarianRequestDTO obj){
		Veterinarian vet = veterinarianService.create(VeterinarianMapper.fromDTO(obj));
		VeterinarianResponseDTO response = VeterinarianMapper.fromEntity(vet);
		response.setId(vet.getId());
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(vet.getId()).toUri();
		return ResponseEntity.created(uri).body(response);
		
	}

}
