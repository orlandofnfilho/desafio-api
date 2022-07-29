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
import br.com.gft.exceptions.BusinessRuleException;
import br.com.gft.exceptions.ResourceNotFoundException;
import br.com.gft.repositories.BreedRepository;

@ExtendWith(MockitoExtension.class)
class BreedServiceTest {
	
	
	private static final String ORIGEM_DESCONHECIDA = "Origem desconhecida";
	private static final String ORIGIN = "Germany, France";
	private static final String TEMPERAMENT = "Stubborn, Curious, Playful, Adventurous, Active, Fun-loving";
	private static final String LIFE_SPAN = "10 - 12 years";
	private static final String NAME = "Affenpinscher";
	private static final long ID = 1L;
	private static final String CHECK_IF_THROWS_BUSINESS_RULE_EXCEPTION = "Check if throws BusinessRuleException";
	private static final String CHECK_IF_THROWS_RESOURCE_NOT_FOUND_EXCEPTION = "Check if throws ResourceNotFoundException";
	private static final String CHECK_IF_PAGE_NOT_NULL_AND_CONTENT = "Check if page not null and content";
	private static final String CHECK_IF_NOT_NULL_OBJECT_CLASS_AND_ATTRIBUTES_VALUES = "Check if not null, object class and attributes values";

	private Breed breed;
	private Breed breedWihtoutOrigin;
	private Optional<Breed> optionalBreed;
	private Page<Breed> page;
	private Pageable pageable;
	
	@Mock
	private BreedRepository breedRepository;
	
	@InjectMocks
	private BreedService breedService;
	
	@BeforeEach
	void setUp() throws Exception {
		startBreed();
	}
	
	@Test
	@DisplayName("Should not throw an exception when checkName")
	void whenCheckNameThenReturnSuccess() {
		breedService.checkName(breed);
		verify(breedRepository, times(1)).findByNameIgnoreCase(NAME);
	}
	
	
	@Test
	@DisplayName("Should create a new Breed")
	void whenCreateThenReturnSuccess() {
		when(breedRepository.save(any())).thenReturn(breed);
		Breed response = breedService.create(breed);
		
		assertAll(CHECK_IF_NOT_NULL_OBJECT_CLASS_AND_ATTRIBUTES_VALUES
				, () -> assertNotNull(response)
				, () -> assertEquals(Breed.class, response.getClass())
				, () -> assertEquals(ID, response.getId())
				, () -> assertEquals(NAME, response.getName())
				, () -> assertEquals(LIFE_SPAN, response.getLife_span())
				, () -> assertEquals(TEMPERAMENT, response.getTemperament())
				, () -> assertEquals(ORIGIN, response.getOrigin()));
	}
	
	@Test
	@DisplayName("Should create a new Breed without origin")
	void whenCreateWithoutOriginThenReturnSuccess() {
		when(breedRepository.save(any())).thenReturn(breedWihtoutOrigin);
		Breed response = breedService.create(breedWihtoutOrigin);
		
		assertAll(CHECK_IF_NOT_NULL_OBJECT_CLASS_AND_ATTRIBUTES_VALUES
				, () -> assertNotNull(response)
				, () -> assertEquals(Breed.class, response.getClass())
				, () -> assertEquals(ID, response.getId())
				, () -> assertEquals(NAME, response.getName())
				, () -> assertEquals(LIFE_SPAN, response.getLife_span())
				, () -> assertEquals(TEMPERAMENT, response.getTemperament())
				, () -> assertEquals(ORIGEM_DESCONHECIDA, response.getOrigin()));
	}
	
	
	@Test
	@DisplayName("Should throws BusinessRuleException when checkName")
	void whenCheckNameThenThrowsBusinessRuleException() {
		when(breedRepository.findByNameIgnoreCase(anyString())).thenThrow(BusinessRuleException.class);
		
		assertThrows(BusinessRuleException.class
				, () -> breedService.checkName(breed)
				, CHECK_IF_THROWS_BUSINESS_RULE_EXCEPTION);
	}
	
	@Test
	@DisplayName("Should return a breed found by id")
	void whenFindByIdThenReturnSuccess() {
		when(breedRepository.findById(anyLong())).thenReturn(optionalBreed);
		Breed response = breedService.findById(ID);
		
		assertAll(CHECK_IF_NOT_NULL_OBJECT_CLASS_AND_ATTRIBUTES_VALUES
				, () -> assertNotNull(response)
				, () -> assertEquals(Breed.class, response.getClass())
				, () -> assertEquals(ID, response.getId())
				, () -> assertEquals(NAME, response.getName())
				, () -> assertEquals(LIFE_SPAN, response.getLife_span())
				, () -> assertEquals(TEMPERAMENT, response.getTemperament())
				, () -> assertEquals(ORIGIN, response.getOrigin()));
	
	}
	
	@Test
	@DisplayName("Should throws ResourceNotFoundException when findById")
	void whenFindByIdThenThrowsResourceNotFoundException() {
		when(breedRepository.findById(anyLong())).thenThrow(ResourceNotFoundException.class);
		
		assertThrows(ResourceNotFoundException.class
				, () -> breedService.findById(ID)
				, CHECK_IF_THROWS_RESOURCE_NOT_FOUND_EXCEPTION);
	}
	
	@Test
	@DisplayName("Should return a Page with list of Breed")
	void whenFindAllReturnAPageWithListOfBreed() {
		when(breedRepository.findAll(pageable)).thenReturn(page);
		Page<Breed> response = breedService.findAll(pageable);
		
		assertAll(CHECK_IF_PAGE_NOT_NULL_AND_CONTENT
				, () -> assertNotNull(response)
				, () -> assertEquals(List.of(breed), response.getContent())
				, () -> assertEquals(10, response.getSize())
				, () -> assertEquals(10, response.getTotalElements())
				, () -> assertEquals(1, response.getTotalPages()));
	}
	
	@Test
	@DisplayName("Should delete a Breed")
	void whenDeleteThenReturnSuccess() {
		when(breedRepository.findById(anyLong())).thenReturn(optionalBreed);
		doNothing().when(breedRepository).delete(breed);
		breedService.delete(ID);
		verify(breedRepository, times(1)).delete(breed);
	}
	
	@Test
	@DisplayName("Should not throw an exception when validUpdate")
	void whenValidUpdateThenReturnSuccess() {
		when(breedRepository.findByNameIgnoreCase(anyString())).thenReturn(optionalBreed);
		breedService.validUpdate(breed);
		verify(breedRepository, times(1)).findByNameIgnoreCase(NAME);
	
	}
	
	@Test
	@DisplayName("Should throws BusinessRuleException when validUpdate")
	void whenValidUpdateThenThrowsBusinessRuleException() {
		when(breedRepository.findByNameIgnoreCase(anyString())).thenThrow(BusinessRuleException.class);
		assertThrows(BusinessRuleException.class
				, () -> breedService.checkName(breed)
				, CHECK_IF_THROWS_BUSINESS_RULE_EXCEPTION);
	}
	
	@Test
	@DisplayName("Should update a Breed")
	void whenUpdateThenReturnSuccess() {
		when(breedRepository.findById(anyLong())).thenReturn(optionalBreed);
		when(breedRepository.save(any())).thenReturn(breed);
		Breed response = breedService.update(ID, breed);
		
		assertAll(CHECK_IF_NOT_NULL_OBJECT_CLASS_AND_ATTRIBUTES_VALUES
				, () -> assertNotNull(response)
				, () -> assertEquals(Breed.class, response.getClass())
				, () -> assertEquals(ID, response.getId())
				, () -> assertEquals(NAME, response.getName())
				, () -> assertEquals(LIFE_SPAN, response.getLife_span())
				, () -> assertEquals(TEMPERAMENT, response.getTemperament())
				, () -> assertEquals(ORIGIN, response.getOrigin()));
		
	}
	
	@Test
	@DisplayName("Should update a Breed without origin")
	void whenUpdateWithoutOriginThenReturnSuccess() {
		when(breedRepository.findById(anyLong())).thenReturn(Optional.of(breedWihtoutOrigin));
		when(breedRepository.save(any())).thenReturn(breedWihtoutOrigin);
		Breed response = breedService.update(ID, breedWihtoutOrigin);
		
		assertAll(CHECK_IF_NOT_NULL_OBJECT_CLASS_AND_ATTRIBUTES_VALUES
				, () -> assertNotNull(response)
				, () -> assertEquals(Breed.class, response.getClass())
				, () -> assertEquals(ID, response.getId())
				, () -> assertEquals(NAME, response.getName())
				, () -> assertEquals(LIFE_SPAN, response.getLife_span())
				, () -> assertEquals(TEMPERAMENT, response.getTemperament())
				, () -> assertEquals(ORIGEM_DESCONHECIDA, response.getOrigin()));
		
	}
	
	private void startBreed() {
		breed = new Breed(ID, NAME, LIFE_SPAN, TEMPERAMENT, ORIGIN);
		breedWihtoutOrigin = new Breed(ID, NAME, LIFE_SPAN, TEMPERAMENT, ORIGEM_DESCONHECIDA);
		optionalBreed = Optional.of(breed);
		pageable = PageRequest.of(0, 10);
		page = new PageImpl<>(List.of(breed), pageable, 10);
		
	}

}
