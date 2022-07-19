package br.com.gft.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.gft.entities.Breed;
import br.com.gft.exceptions.BusinessRuleException;
import br.com.gft.exceptions.ResourceNotFoundException;
import br.com.gft.repositories.BreedRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BreedService {

	private final BreedRepository breedRepository;

	public Breed create(Breed obj) {
		List<Breed> breedFromApi = this.getFromDogApi(obj.getName());
		if(breedFromApi.size()==1) {
			this.finByName(breedFromApi.get(0).getName());
			return breedRepository.save(breedFromApi.get(0));
		}
		this.finByName(obj.getName());
		return breedRepository.save(obj);
	}

	public Breed findById(Long id) {
		Optional<Breed> obj = breedRepository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException("Raça não encontrada: " + id));
	}
	
	public List<Breed> findAll(){
		return breedRepository.findAll();
	}

	public Breed update(Long id, Breed obj) {
		Breed breedSaved = this.findById(id);
		obj.setId(breedSaved.getId());
		return breedRepository.save(obj);
	}

	public void delete(Long id) {
		Breed breedSaved = this.findById(id);
		breedRepository.delete(breedSaved);
	}

	public Optional<Breed> finByName(String name) {
		Optional<Breed> breedSaved = breedRepository.findByNameIgnoreCase(name);
		if (breedSaved.isPresent()) {
			throw new BusinessRuleException("Raça já cadastrada: " + breedSaved.get().getName());
		}
		return breedSaved;
	}
	
	
	public List<Breed> getFromDogApi(String name) {
		Breed[] response = new RestTemplate().getForObject("https://api.thedogapi.com/v1/breeds/search?q={name}", Breed[].class,
				name);
		return List.of(response);
	}
}
