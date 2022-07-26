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

import br.com.gft.dto.client.ClientMapper;
import br.com.gft.dto.client.ClientRequestDTO;
import br.com.gft.dto.client.ClientResponseDTO;
import br.com.gft.entities.Client;
import br.com.gft.services.ClientService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/vetApi/v1/clients")
public class ClientController {

	private static final String HAS_AUTHORITY_ADMIN = "hasAuthority('ADMIN')";
	private static final String ID = "/{id}";
	private final ClientService clientService;

	@PostMapping
	@PreAuthorize(HAS_AUTHORITY_ADMIN)
	public ResponseEntity<ClientResponseDTO> create(@RequestBody @Valid ClientRequestDTO obj) {
		Client client = clientService.create(ClientMapper.fromDTO(obj));
		ClientResponseDTO response = ClientMapper.fromEntity(client);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path(ID).buildAndExpand(response.getId()).toUri();
		return ResponseEntity.created(uri).body(response);

	}

	@GetMapping(ID)
	@PreAuthorize(HAS_AUTHORITY_ADMIN)
	public ResponseEntity<ClientResponseDTO> findById(@PathVariable Long id) {
		Client client = clientService.findById(id);
		ClientResponseDTO response = ClientMapper.fromEntity(client);
		return ResponseEntity.ok().body(response);

	}

	@GetMapping
	@PreAuthorize(HAS_AUTHORITY_ADMIN)
	public ResponseEntity<Page<ClientResponseDTO>> findAll(@PageableDefault(size = 10) Pageable pageable) {
		Page<ClientResponseDTO> response = clientService.findAll(pageable).map(ClientMapper::fromEntity);
		return ResponseEntity.ok().body(response);

	}

	@PutMapping(ID)
	@PreAuthorize(HAS_AUTHORITY_ADMIN)
	public ResponseEntity<ClientResponseDTO> update(@PathVariable Long id, @RequestBody @Valid ClientRequestDTO obj) {
		Client client = clientService.update(id, ClientMapper.fromDTO(obj));
		ClientResponseDTO response = ClientMapper.fromEntity(client);
		return ResponseEntity.ok().body(response);
	}

	@DeleteMapping(ID)
	@PreAuthorize(HAS_AUTHORITY_ADMIN)
	public ResponseEntity<ClientResponseDTO> delete(@PathVariable Long id) {
		clientService.delete(id);
		return ResponseEntity.noContent().build();
	}

}
