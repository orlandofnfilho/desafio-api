package br.com.gft.services;


import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

import br.com.gft.entities.Breed;
import br.com.gft.entities.Client;
import br.com.gft.entities.Dog;
import br.com.gft.exceptions.ResourceNotFoundException;
import br.com.gft.repositories.DogRepository;

@ExtendWith(MockitoExtension.class)
class DogServiceTest {

	private static final String REGCOD = "AL123G";
	private static final String NAME = "Milo";
	private static final String BREED_ORIGIN = "Germany, France";
	private static final String BREED_TEMPERAMENT = "Stubborn, Curious, Playful, Adventurous, Active, Fun-loving";
	private static final String BREED_LIFESPAN = "10 - 12 years";
	private static final String BREED_NAME = "Affenpinscher";
	private static final String TUTOR_PHONE = "55999223388";
	private static final String TUTOR_CPF = "12345678901";
	private static final String TUTOR_NAME = "Alex Green";
	private static final long ID = 1L;
	private static final String CHECK_IF_THROWS_RESOURCE_NOT_FOUND_EXCEPTION = "Check if throws ResourceNotFoundException";
	private static final String CHECK_IF_PAGE_NOT_NULL_AND_CONTENT = "Check if page not null and content";
	private static final String CHECK_IF_NOT_NULL_OBJECT_CLASS_AND_ATTRIBUTES_VALUES = "Check if not null, object class and attributes values";
	
	private Dog dog;
	private Optional<Dog> optionalDog;
	private Client client;
	private Breed breed;
	private Optional<Breed> optionalBreed;
	private Page<Dog> page;
	private Pageable pageable;
	
	@Mock
	private DogRepository dogRepository;
	
	@Mock
	private BreedService breedService;
	
	@Mock
	private ClientService clientService;
	
	@InjectMocks
	private DogService dogService;
	

	@BeforeEach
	void setUp() throws Exception {
		startDog();
	}
	
	
	@Test
	@DisplayName("Should create a new Dog")
	void whenCreateThenReturnSuccess() {
		when(breedService.findByName(anyString())).thenReturn(optionalBreed);
		when(dogRepository.save(any())).thenReturn(dog);
		when(clientService.findById(anyLong())).thenReturn(client);
		Dog response = dogService.create(dog);
		
		assertAll(CHECK_IF_NOT_NULL_OBJECT_CLASS_AND_ATTRIBUTES_VALUES
				, () -> assertNotNull(response)
				, () -> assertEquals(ID, response.getId())
				, () -> assertEquals(NAME, response.getName())
				, () -> assertEquals(BREED_NAME, response.getBreed().getName())
				, () -> assertEquals(TUTOR_NAME, response.getTutor().getName()));
	}
	
	@Test
	@DisplayName("Should create a new Dog getting Breed from TheDogApi")
	void whenCreateGetBreedFromTheDogApiThenReturnSuccess() {
		when(breedService.getFromDogApi(anyString())).thenReturn(List.of(breed));
		when(dogRepository.save(any())).thenReturn(dog);
		when(clientService.findById(anyLong())).thenReturn(client);
		Dog response = dogService.create(dog);
		
		assertAll(CHECK_IF_NOT_NULL_OBJECT_CLASS_AND_ATTRIBUTES_VALUES
				, () -> assertNotNull(response)
				, () -> assertEquals(ID, response.getId())
				, () -> assertEquals(NAME, response.getName())
				, () -> assertEquals(BREED_NAME, response.getBreed().getName())
				, () -> assertEquals(TUTOR_NAME, response.getTutor().getName()));
	}
	
	@Test
	@DisplayName("Should set Breed attributes from TheDogApi")
	void whenSetBreedFromApiThenReturnSuccess() {
		dogService.setBreedFromApi(dog, List.of(breed));
		assertAll(CHECK_IF_NOT_NULL_OBJECT_CLASS_AND_ATTRIBUTES_VALUES
				, () -> assertNotNull(dog)
				, () -> assertEquals(BREED_NAME, dog.getBreed().getName())
				, () -> assertEquals(BREED_LIFESPAN, dog.getBreed().getLife_span())
				, () -> assertEquals(BREED_TEMPERAMENT, dog.getBreed().getTemperament())
				, () -> assertEquals(BREED_ORIGIN, dog.getBreed().getOrigin()));	
	}
	
	@Test
	@DisplayName("Should generate Dog regCod")
	void whenGenerateRegCodThenReturnSuccess() {
		String response = dogService.generateRegCod(TUTOR_NAME);
		assertAll(CHECK_IF_NOT_NULL_OBJECT_CLASS_AND_ATTRIBUTES_VALUES
				, () -> assertNotNull(response)
				, () -> assertEquals(6, response.length()));
	}
	
	@Test
	@DisplayName("Should return a Page with list of Dog")
	void whenFindAllReturnAPageWithListOfDog() {
		when(dogRepository.findAll(pageable)).thenReturn(page);
		Page<Dog> response = dogService.findAll(pageable);
		
		assertAll(CHECK_IF_PAGE_NOT_NULL_AND_CONTENT
				, () -> assertNotNull(response)
				, () -> assertEquals(List.of(dog), response.getContent())
				, () -> assertEquals(10, response.getSize())
				, () -> assertEquals(10, response.getTotalElements())
				, () -> assertEquals(1, response.getTotalPages()));
	}
	
	
	@Test
	@DisplayName("Should delete a Dog")
	void whenDeleteThenReturnSuccess() {
		when(dogRepository.findById(anyLong())).thenReturn(optionalDog);
		doNothing().when(dogRepository).delete(dog);
		dogService.delete(ID);
		verify(dogRepository, times(1)).delete(dog);
	}
	
	@Test
	@DisplayName("Should return a Dog found by Id")
	void whenFindByIdThenReturnSuccess() {
		when(dogRepository.findById(anyLong())).thenReturn(optionalDog);
		Dog response = dogService.findById(ID);
		
		assertAll(CHECK_IF_NOT_NULL_OBJECT_CLASS_AND_ATTRIBUTES_VALUES
				, () -> assertNotNull(response)
				, () -> assertEquals(Dog.class, response.getClass())
				, () -> assertEquals(ID, response.getId())
				, () -> assertEquals(NAME, response.getName())
				, () -> assertEquals(BREED_NAME, response.getBreed().getName())
				, () -> assertEquals(TUTOR_NAME, response.getTutor().getName()));
	}
	
	@Test
	@DisplayName("Should throws ResourceNotFoundException when findById")
	void whenFindByIdThenThrowsResourceNotFoundException() {
		when(dogRepository.findById(anyLong())).thenThrow(ResourceNotFoundException.class);
		
		assertThrows(ResourceNotFoundException.class
				, () -> dogService.findById(ID)
				, CHECK_IF_THROWS_RESOURCE_NOT_FOUND_EXCEPTION);
	}
	
	@Test
	@DisplayName("Should update a Dog")
	void whenUpdateThenReturnSuccess() {
		when(dogRepository.findById(anyLong())).thenReturn(optionalDog);
		when(dogRepository.save(any())).thenReturn(dog);
		when(clientService.findById(anyLong())).thenReturn(client);
		when(breedService.findByName(anyString())).thenReturn(optionalBreed);
		Dog response = dogService.update(ID, dog);
		
		assertAll(CHECK_IF_NOT_NULL_OBJECT_CLASS_AND_ATTRIBUTES_VALUES
				, () -> assertNotNull(response)
				, () -> assertEquals(Dog.class, response.getClass())
				, () -> assertEquals(ID, response.getId())
				, () -> assertEquals(NAME, response.getName())
				, () -> assertEquals(BREED_NAME, response.getBreed().getName())
				, () -> assertEquals(TUTOR_NAME, response.getTutor().getName()));
		
	}
	
	@Test
	@DisplayName("Should find a Dog by regCod")
	void whenFindByRegCodIgnoreCaseThenReturnSuccess() {
		when(dogRepository.findByRegCodIgnoreCase(anyString())).thenReturn(optionalDog);
		Dog response = dogService.findByRegCod(REGCOD);
		
		assertAll(CHECK_IF_NOT_NULL_OBJECT_CLASS_AND_ATTRIBUTES_VALUES
				, () -> assertNotNull(response)
				, () -> assertEquals(Dog.class, response.getClass())
				, () -> assertEquals(ID, response.getId())
				, () -> assertEquals(NAME, response.getName())
				, () -> assertEquals(BREED_NAME, response.getBreed().getName())
				, () -> assertEquals(TUTOR_NAME, response.getTutor().getName()));
		
		
	}
	
	@Test
	@DisplayName("Should throws ResourceNotFoundException when findByRegCod")
	void whenFindByRegCodThenThrowsResourceNotFoundException() {
		when(dogRepository.findByRegCodIgnoreCase(anyString())).thenThrow(ResourceNotFoundException.class);
		
		assertThrows(ResourceNotFoundException.class
				, () -> dogService.findByRegCod(REGCOD)
				, CHECK_IF_THROWS_RESOURCE_NOT_FOUND_EXCEPTION);
	}

	
	private void startDog() {
		client = new Client(ID, TUTOR_NAME, TUTOR_CPF, TUTOR_PHONE, new ArrayList<>());
		breed = new Breed(ID, BREED_NAME, BREED_LIFESPAN, BREED_TEMPERAMENT, BREED_ORIGIN);
		optionalBreed = Optional.of(breed);
		dog = new Dog(ID, NAME, REGCOD, LocalDate.of(2010, 1,1), breed, client, new ArrayList<>());
		optionalDog = Optional.of(dog);
		pageable = PageRequest.of(0, 10);
		page = new PageImpl<>(List.of(dog), pageable, 10);
		
	}

}
