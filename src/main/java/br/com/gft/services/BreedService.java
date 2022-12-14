package br.com.gft.services;

import java.util.List;
import java.util.Optional;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import br.com.gft.entities.Breed;
import br.com.gft.exceptions.BusinessRuleException;
import br.com.gft.exceptions.ResourceNotFoundException;
import br.com.gft.repositories.BreedRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class BreedService {

	private static final String RACA_JA_CADASTRADA = "Raça já cadastrada: ";
	private static final String ORIGEM_DESCONHECIDA = "Origem desconhecida";
	private final BreedRepository breedRepository;

	public Breed create(Breed obj) {
		List<Breed> breedFromApi = this.getFromDogApi(obj.getName());
		checkName(obj);
		if (breedFromApi.size() == 1) {
			this.findByName(breedFromApi.get(0).getName());
			if (breedFromApi.get(0).getOrigin() == null || breedFromApi.get(0).getOrigin().isBlank())
				breedFromApi.get(0).setOrigin(ORIGEM_DESCONHECIDA);
			return breedRepository.save(breedFromApi.get(0));
		}
		if (obj.getOrigin() == null || obj.getOrigin().isBlank())
			obj.setOrigin(ORIGEM_DESCONHECIDA);
		return breedRepository.save(obj);
	}

	@Transactional(readOnly = true)
	public Breed findById(Long id) {
		Optional<Breed> obj = breedRepository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException("Raça não encontrada id: " + id));
	}

	@Transactional(readOnly = true)
	public Page<Breed> findAll(Pageable pageable) {
		return breedRepository.findAll(pageable);
	}

	public Breed update(Long id, Breed obj) {
		Breed breedSaved = this.findById(id);
		obj.setId(breedSaved.getId());
		if (obj.getOrigin() == null || obj.getOrigin().isBlank())
			obj.setOrigin(ORIGEM_DESCONHECIDA);
		validUpdate(obj);
		return breedRepository.save(obj);
	}

	public void delete(Long id) {
		Breed breedSaved = this.findById(id);
		breedRepository.delete(breedSaved);
	}

	@Transactional(readOnly = true)
	public Optional<Breed> findByName(String name) {
		return breedRepository.findByNameIgnoreCase(name);
	}

	@Transactional(readOnly = true)
	public void checkName(Breed obj) {
		Optional<Breed> breedSaved = findByName(obj.getName());
		if (breedSaved.isPresent()) {
			throw new BusinessRuleException(RACA_JA_CADASTRADA + breedSaved.get().getName());
		}
	}

	public List<Breed> getFromDogApi(String name) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("x-api-key", "ce3c5fd-feb4-4bae-bfb6-b6eb22f57724");
		HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

		ResponseEntity<List<Breed>> response = new RestTemplate().exchange(
				"https://api.thedogapi.com/v1/breeds/search?q=" + name, HttpMethod.GET, requestEntity,
				new ParameterizedTypeReference<>() {
				});
		return response.getBody();
	}

	@Transactional(readOnly = true)
	public void validUpdate(Breed obj) {
		Optional<Breed> breedSaved = findByName(obj.getName());
		if (breedSaved.isPresent() && obj.getId() != breedSaved.get().getId()) {
			throw new BusinessRuleException(RACA_JA_CADASTRADA + breedSaved.get().getName());
		}
	}
}
