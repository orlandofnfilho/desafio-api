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
import java.time.LocalDate;
import java.util.ArrayList;
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
import br.com.gft.dto.dog.DogRequestDTO;
import br.com.gft.dto.dog.DogResponseDTO;
import br.com.gft.entities.Breed;
import br.com.gft.entities.Client;
import br.com.gft.entities.Dog;
import br.com.gft.services.BreedService;
import br.com.gft.services.ClientService;
import br.com.gft.services.DogService;

@ExtendWith(MockitoExtension.class)
class DogControllerTest {

	private static final String TUTOR_PHONE = "12345678";
	private static final String TUTOR_CPF = "12345678901";
	private static final String TUTOR = "Alex Green";
	private static final LocalDate BIRTHDATE = LocalDate.of(2010, 1, 1);
	private static final String BREED_ORIGIN = "Germany, France";
	private static final String BREED_TEMPERAMENT = "Stubborn, Curious, Playful, Adventurous, Active, Fun-loving";
	private static final String BREED_LIFE_SPAN = "10 - 12 years";
	private static final String BREED_NAME = "Affenpinscher";
	private static final String REGCOD = "ALX123";
	private static final String NAME = "Milo";
	private static final long ID = 1L;
	private static final String CHECK_IF_NOT_NULL_HTTP_STATUS_AND_RESPONSE_BODY = "Check if not null, HttpStatus and response body";
	private static final int INDEX = 0;

	private Dog dog;
	private DogRequestDTO requestDTO;
	private DogResponseDTO responseDTO;
	private Breed breed;
	private Client client;
	private BreedRequestDTO breedRequestDTO;
	private BreedResponseDTO breedResponseDTO;
	private Page<Dog> page;
	private Pageable pageable;

	@Mock
	private DogService dogService;
	
	@Mock
	private ClientService clientService;
	
	@Mock
	private BreedService breedService;

	@InjectMocks
	private DogController dogController;

	@BeforeEach
	void setUp() throws Exception {
		startDog();
	}

	@Test
	@DisplayName("Should return a DogResponseDTO")
	void whenCreateThenReturnCreated() throws URISyntaxException {
		when(dogService.create(any())).thenReturn(dog);
		
		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

		ResponseEntity<DogResponseDTO> response = dogController.create(requestDTO);

		assertAll(CHECK_IF_NOT_NULL_HTTP_STATUS_AND_RESPONSE_BODY, () -> assertNotNull(response),
				() -> assertEquals(ResponseEntity.class, response.getClass()),
				() -> assertEquals(HttpStatus.CREATED, response.getStatusCode()),
				() -> assertEquals(response.getBody(), responseDTO));
	}
	
	@Test
	@DisplayName("Should return a page with list of DogResponseDTO")
	void whenFindAllThenReturnAPageOfDogResponseDTO() {
		when(dogService.findAll(pageable)).thenReturn(page);
		ResponseEntity<Page<DogResponseDTO>> response = dogController.findAll(pageable);
		
		assertAll(CHECK_IF_NOT_NULL_HTTP_STATUS_AND_RESPONSE_BODY
				, () -> assertNotNull(response)
				, () -> assertNotNull(response.getBody())
				, () -> assertEquals(HttpStatus.OK, response.getStatusCode())
				, () -> assertEquals(ResponseEntity.class, response.getClass())
				, () -> assertEquals(PageImpl.class, response.getBody().getClass())
				, () -> assertEquals(DogResponseDTO.class, response.getBody().getContent().get(INDEX).getClass())
				, () -> assertEquals(ID, response.getBody().getContent().get(INDEX).getId())
				, () -> assertEquals(NAME, response.getBody().getContent().get(INDEX).getName())
				, () -> assertEquals(ID, response.getBody().getContent().get(INDEX).getTutorId())
				, () -> assertEquals(BREED_NAME, response.getBody()
						.getContent().get(INDEX).getBreed().getName()));
	}
	
	@Test
	@DisplayName("Should return a DogResponseDTO found by id")
	void whenFindByIdThenReturnSuccess() {
		when(dogService.findById(ID)).thenReturn(dog);
		ResponseEntity<DogResponseDTO> response = dogController.findById(ID);
		
		assertAll(CHECK_IF_NOT_NULL_HTTP_STATUS_AND_RESPONSE_BODY
				, () -> assertNotNull(response)
				, () -> assertNotNull(response.getBody())
				, () -> assertEquals(HttpStatus.OK, response.getStatusCode())
				, () -> assertEquals(ResponseEntity.class, response.getClass())
				, () -> assertEquals(ID, response.getBody().getId())
				, () -> assertEquals(NAME, response.getBody().getName())
				, () -> assertEquals(ID, response.getBody().getTutorId())
				, () -> assertEquals(BREED_NAME, response.getBody().getBreed().getName()));
	}
	
	@Test
	@DisplayName("Should delete a Dogthen return noContent")
	void whenDeleteThenReturnNoContent() {
		doNothing().when(dogService).delete(anyLong());
		
		ResponseEntity<DogResponseDTO> response = dogController.delete(ID);
		assertAll(CHECK_IF_NOT_NULL_HTTP_STATUS_AND_RESPONSE_BODY
				, () -> assertNotNull(response)
				, () -> assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode())
				, () -> assertEquals(ResponseEntity.class, response.getClass()));
		verify(dogService, times(1)).delete(anyLong());
	}
	
	@Test
	@DisplayName("Should update a Dog then return DogResponseDTO")
	void whenUpdateThenReturnSuccess() {
		when(dogService.update(anyLong(), any(Dog.class))).thenReturn(dog);
		
		ResponseEntity<DogResponseDTO> response = dogController.update(ID, requestDTO);
		assertAll(CHECK_IF_NOT_NULL_HTTP_STATUS_AND_RESPONSE_BODY
				, () -> assertNotNull(response)
				, () -> assertNotNull(response.getBody())
				, () -> assertEquals(HttpStatus.OK, response.getStatusCode())
				, () -> assertEquals(ResponseEntity.class, response.getClass())
				, () -> assertEquals(ID, response.getBody().getId())
				, () -> assertEquals(NAME, response.getBody().getName())
				, () -> assertEquals(ID, response.getBody().getTutorId())
				, () -> assertEquals(BREED_NAME, response.getBody().getBreed().getName()));
		
	}
	

	private void startDog() {
		breed = new Breed(ID, BREED_NAME, BREED_LIFE_SPAN, BREED_TEMPERAMENT, BREED_ORIGIN);
		client = new Client(ID, TUTOR,  TUTOR_CPF, TUTOR_PHONE, new ArrayList<>());
		breedRequestDTO = new BreedRequestDTO(BREED_NAME, BREED_LIFE_SPAN, BREED_TEMPERAMENT, BREED_ORIGIN);
		breedResponseDTO = new BreedResponseDTO(ID, BREED_NAME, BREED_LIFE_SPAN, BREED_TEMPERAMENT, BREED_ORIGIN);
		dog = new Dog(ID, NAME, REGCOD, BIRTHDATE, breed, client, new ArrayList<>());
		requestDTO = new DogRequestDTO(NAME, ID, BIRTHDATE, breedRequestDTO);
		responseDTO = new DogResponseDTO(ID, NAME, REGCOD, BIRTHDATE, ID, breedResponseDTO);
		pageable = PageRequest.of(0, 10);
		page = new PageImpl<>(List.of(dog), pageable, 10);

	}

}
