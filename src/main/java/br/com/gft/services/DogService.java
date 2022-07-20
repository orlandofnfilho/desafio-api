package br.com.gft.services;

import java.util.List;
import java.util.Random;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.gft.entities.Breed;
import br.com.gft.entities.Dog;
import br.com.gft.repositories.BreedRepository;
import br.com.gft.repositories.DogRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DogService {

	private final DogRepository dogRepository;
	private final BreedRepository breedRepository;
	private final BreedService breedService;

	public Dog create(Dog obj) {
		breedService.create(obj.getBreed());
		obj.setReg_cod(generateRegCod());
		return dogRepository.save(obj);
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
	
	public String generateRegCod(){
		String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		Random rnd = new Random();
		StringBuilder sb = new StringBuilder(6);
		for (int i = 0; i < 6; i++)
			sb.append(chars.charAt(rnd.nextInt(chars.length())));
		return sb.toString();
	}

}
