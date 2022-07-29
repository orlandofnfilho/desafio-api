package br.com.gft.controllers;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.net.URISyntaxException;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import br.com.gft.dto.breed.BreedRequestDTO;
import br.com.gft.dto.breed.BreedResponseDTO;
import br.com.gft.entities.Breed;
import br.com.gft.services.BreedService;

@ExtendWith(MockitoExtension.class)
class BreedControllerTest {
	
	private static final String CHECK_IF_NOT_NULL_HTTP_STATUS_AND_RESPONSE_BODY = "Check if not null, HttpStatus and response body";
	private static final long ID = 1L;
	private static final int INDEX = 0;
	private static final String ORIGIN = "Germany, France";
	private static final String TEMPERAMENT = "Stubborn, Curious, Playful, Adventurous, Active, Fun-loving";
	private static final String LIFE_SPAN = "10 - 12 years";
	private static final String NAME = "Affenpinscher";
	
	private Breed breed;
	private BreedRequestDTO requestDTO;
	private BreedResponseDTO responseDTO;
	private Page<Breed> page;
	private Pageable pageable;
	
	@Mock
	private BreedService breedService;
	
	@InjectMocks
	private BreedController breedController;
	
	
	@BeforeEach
	void setUp() throws Exception {
		startBreed();
		
	}

	@Test
	@DisplayName("Should return a BreedResponseDTO")
	void whenCreateThenReturnCreated() throws URISyntaxException{
		when(breedService.create(any())).thenReturn(breed);		

		MockHttpServletRequest request = new MockHttpServletRequest();
	    RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request)); 
		
		 ResponseEntity<BreedResponseDTO> response  = breedController.create(requestDTO);
			
		    assertAll(CHECK_IF_NOT_NULL_HTTP_STATUS_AND_RESPONSE_BODY
		    		, () -> assertNotNull(response)
		    		, () -> assertEquals(ResponseEntity.class, response.getClass())
		    		, () -> assertEquals(HttpStatus.CREATED, response.getStatusCode())
		    		, () -> assertEquals(response.getBody(), responseDTO));	
	}
	
	@Test
	@DisplayName("Should return a page with list of BreedResponseDTO")
	void whenFindAllThenReturnAPageOfBreedResponseDTO() {
		when(breedService.findAll(pageable)).thenReturn(page);
		ResponseEntity<Page<BreedResponseDTO>> response = breedController.findAll(pageable);
		
		assertAll(CHECK_IF_NOT_NULL_HTTP_STATUS_AND_RESPONSE_BODY
				, () -> assertNotNull(response)
				, () -> assertNotNull(response.getBody())
				, () -> assertEquals(HttpStatus.OK, response.getStatusCode())
				, () -> assertEquals(ResponseEntity.class, response.getClass())
				, () -> assertEquals(PageImpl.class, response.getBody().getClass())
				, () -> assertEquals(BreedResponseDTO.class, response.getBody().getContent().get(INDEX).getClass())
				, () -> assertEquals(ID, response.getBody().getContent().get(INDEX).getId())
				, () -> assertEquals(NAME, response.getBody().getContent().get(INDEX).getName())
				, () -> assertEquals(LIFE_SPAN, response.getBody().getContent().get(INDEX).getLife_span())
				, () -> assertEquals(TEMPERAMENT, response.getBody().getContent().get(INDEX).getTemperament())
				, () -> assertEquals(ORIGIN, response.getBody().getContent().get(INDEX).getOrigin()));
	}
	
	@Test
	@DisplayName("Should return a BreedResponseDTO found by id")
	void whenFindByIdThenReturnSuccess() {
		when(breedService.findById(ID)).thenReturn(breed);
		ResponseEntity<BreedResponseDTO> response = breedController.findById(ID);
		
		assertAll(CHECK_IF_NOT_NULL_HTTP_STATUS_AND_RESPONSE_BODY
				, () -> assertNotNull(response)
				, () -> assertNotNull(response.getBody())
				, () -> assertEquals(HttpStatus.OK, response.getStatusCode())
				, () -> assertEquals(ResponseEntity.class, response.getClass())
				, () -> assertEquals(ID, response.getBody().getId())
				, () -> assertEquals(NAME, response.getBody().getName())
				, () -> assertEquals(LIFE_SPAN, response.getBody().getLife_span())
				, () -> assertEquals(TEMPERAMENT, response.getBody().getTemperament())
				, () -> assertEquals(ORIGIN, response.getBody().getOrigin()));
	}
	
	@Test
	@DisplayName("Should delete a Breed then return noContent")
	void whenDeleteThenReturnNoContent() {
		doNothing().when(breedService).delete(anyLong());
		
		ResponseEntity<BreedResponseDTO> response = breedController.delete(ID);
		assertAll(CHECK_IF_NOT_NULL_HTTP_STATUS_AND_RESPONSE_BODY
				, () -> assertNotNull(response)
				, () -> assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode())
				, () -> assertEquals(ResponseEntity.class, response.getClass()));
		verify(breedService, times(1)).delete(anyLong());
	}
	
	@Test
	@DisplayName("Should update a Breed then return BreedResponseDTO")
	void whenUpdateThenReturnSuccess() {
		when(breedService.update(anyLong(), any(Breed.class))).thenReturn(breed);
		
		ResponseEntity<BreedResponseDTO> response = breedController.update(ID, requestDTO);
		assertAll(CHECK_IF_NOT_NULL_HTTP_STATUS_AND_RESPONSE_BODY
				, () -> assertNotNull(response)
				, () -> assertNotNull(response.getBody())
				, () -> assertEquals(HttpStatus.OK, response.getStatusCode())
				, () -> assertEquals(ResponseEntity.class, response.getClass())
				, () -> assertEquals(ID, response.getBody().getId())
				, () -> assertEquals(NAME, response.getBody().getName())
				, () -> assertEquals(LIFE_SPAN, response.getBody().getLife_span())
				, () -> assertEquals(TEMPERAMENT, response.getBody().getTemperament())
				, () -> assertEquals(ORIGIN, response.getBody().getOrigin()));
	}
	
	
	private void startBreed() {
		breed = new Breed(ID, NAME, LIFE_SPAN, TEMPERAMENT, ORIGIN);
		requestDTO = new BreedRequestDTO(NAME, LIFE_SPAN, TEMPERAMENT, ORIGIN);
		responseDTO = new BreedResponseDTO(ID, NAME, LIFE_SPAN, TEMPERAMENT, ORIGIN);
		pageable = PageRequest.of(0, 10);
		page = new PageImpl<>(List.of(breed), pageable, 10);
		
	}

}
