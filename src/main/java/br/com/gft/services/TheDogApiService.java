package br.com.gft.services;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.gft.dto.vote.VoteDeleteResponseDTO;
import br.com.gft.dto.vote.VoteRequestDTO;
import br.com.gft.dto.vote.VoteResponseDTO;
import br.com.gft.entities.Breed;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TheDogApiService {

	private static final String APPLICATION_JSON = "application/json";
	private static final String CONTENT_TYPE = "Content-Type";
	private static final String MY_KEY = "fce3c5fd-feb4-4bae-bfb6-b6eb22f57724";
	private static final String X_API_KEY = "x-api-key";

	public List<Breed> findAllTheDogApi() {
		HttpHeaders headers = new HttpHeaders();
		headers.set(X_API_KEY, MY_KEY);
		HttpEntity<?> requestEntity = new HttpEntity<>(headers);
		ResponseEntity<List<Breed>> response = new RestTemplate().exchange("https://api.thedogapi.com/v1/breeds/",
				HttpMethod.GET, requestEntity, new ParameterizedTypeReference<>() {
				});
		return response.getBody();
	}

	public VoteResponseDTO createVote(VoteRequestDTO obj) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set(CONTENT_TYPE, APPLICATION_JSON);
		headers.set(X_API_KEY, MY_KEY);
		HttpEntity<VoteRequestDTO> requestEntity = new HttpEntity<>(obj, headers);
		ResponseEntity<VoteResponseDTO> response = new RestTemplate().exchange("https://api.thedogapi.com/v1/votes",
				HttpMethod.POST, requestEntity, VoteResponseDTO.class);
		return response.getBody();
	}
	
	public VoteDeleteResponseDTO deleteVote(String vote_id) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set(CONTENT_TYPE, APPLICATION_JSON);
		headers.set(X_API_KEY, MY_KEY);
		HttpEntity<?> requestEntity = new HttpEntity<>(headers);
		ResponseEntity<VoteDeleteResponseDTO> response = new RestTemplate().exchange("https://api.thedogapi.com/v1/votes/"+vote_id,
				HttpMethod.DELETE, requestEntity, VoteDeleteResponseDTO.class);
		return response.getBody();
	}

}
