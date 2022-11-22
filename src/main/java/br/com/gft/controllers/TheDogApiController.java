package br.com.gft.controllers;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.gft.controllers.docs.TheDogApiControllerDoc;
import br.com.gft.dto.breed.BreedMapper;
import br.com.gft.dto.breed.BreedResponseDTO;
import br.com.gft.dto.vote.VoteDeleteResponseDTO;
import br.com.gft.dto.vote.VoteRequestDTO;
import br.com.gft.dto.vote.VoteResponseDTO;
import br.com.gft.services.TheDogApiService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/thedogapi")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TheDogApiController implements TheDogApiControllerDoc{

	private static final String HAS_ANY_AUTHORITY_ADMIN_USUARIO = "hasAnyAuthority('ADMIN','USUARIO')";
	private final TheDogApiService theDogApiService;

	@GetMapping
	@PreAuthorize(HAS_ANY_AUTHORITY_ADMIN_USUARIO)
	public ResponseEntity<List<BreedResponseDTO>> findAll() {
		List<BreedResponseDTO> response = theDogApiService.findAllTheDogApi().stream()
				.map(x -> BreedMapper.fromEntity(x)).collect(Collectors.toList());
		return ResponseEntity.ok().body(response);
	}

	@PostMapping
	@PreAuthorize(HAS_ANY_AUTHORITY_ADMIN_USUARIO)
	public ResponseEntity<VoteResponseDTO> createVote(@RequestBody @Valid VoteRequestDTO obj) {
		VoteResponseDTO response = theDogApiService.createVote(obj);
		return ResponseEntity.ok().body(response);
	}

	@DeleteMapping("{vote_id}")
	@PreAuthorize(HAS_ANY_AUTHORITY_ADMIN_USUARIO)
	public ResponseEntity<VoteDeleteResponseDTO> deleteVote(@PathVariable String vote_id) {
		VoteDeleteResponseDTO response = theDogApiService.deleteVote(vote_id);
		return ResponseEntity.ok().body(response);
	}
}
