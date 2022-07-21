package br.com.gft.services;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.gft.entities.Breed;
import br.com.gft.entities.Dog;
import br.com.gft.exceptions.ResourceNotFoundException;
import br.com.gft.repositories.DogRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
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

	@Transactional(readOnly = true)
	public Dog findById(Long id) {
		Optional<Dog> obj = dogRepository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException("Cachorro não encontrado: " + id));
	}

	@Transactional(readOnly = true)
	public Page<Dog> findAll(Pageable pageable) {
		return dogRepository.findAll(pageable);
	}

	public Dog update(Long id, Dog obj) {
		Dog dogSaved = this.findById(id);
		obj.setId(dogSaved.getId());
		obj.setReg_cod(dogSaved.getReg_cod());
		Optional<Breed> breedSaved = breedService.findByName(obj.getBreed().getName());
		if(breedSaved.isPresent()) {
			obj.setBreed(breedSaved.get());
		}
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
