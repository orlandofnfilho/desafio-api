package br.com.gft.services;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import br.com.gft.entities.Breed;
import br.com.gft.entities.Dog;
import br.com.gft.repositories.DogRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DogService {

	private final DogRepository dogRepository;
	private final BreedService breedService;

	public Dog create(Dog obj) {
		Optional<Breed> breedSaved = breedService.findByName(obj.getBreed().getName());
		if (breedSaved.isPresent()) {
			obj.setBreed(breedSaved.get());
		} else {
			List<Breed> breedFromApi = breedService.getFromDogApi(obj.getBreed().getName());
			if (breedFromApi.size() == 1) {
				setBreedFromApi(obj, breedFromApi);
			}
		}
		obj.setReg_cod(generateRegCod());
		return dogRepository.save(obj);
	}

	public Dog findById(Long id) {
		Optional<Dog> obj = dogRepository.findById(id);
		return obj.orElseThrow(() -> new ResourceAccessException("Cachorro não encontrado: " + id));
	}

	public List<Dog> findAll() {
		return dogRepository.findAll();
	}

	public Dog update(Long id, Dog obj) {
		Dog dogSaved = this.findById(id);
		obj.setId(dogSaved.getId());
		return dogRepository.save(obj);
	}

	public void delete(Long id) {
		Dog dogSaved = this.findById(id);
		dogRepository.delete(dogSaved);
	}

	public String generateRegCod() {
		String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		Random rnd = new Random();
		StringBuilder sb = new StringBuilder(6);
		for (int i = 0; i < 6; i++)
			sb.append(chars.charAt(rnd.nextInt(chars.length())));
		return sb.toString();
	}

	public void setBreedFromApi(Dog obj, List<Breed> breedFromApi) {
		obj.getBreed().setName(breedFromApi.get(0).getName());
		obj.getBreed().setLife_span(breedFromApi.get(0).getLife_span());
		obj.getBreed().setOrigin(breedFromApi.get(0).getOrigin());
		obj.getBreed().setTemperament(breedFromApi.get(0).getTemperament());
	}

}
