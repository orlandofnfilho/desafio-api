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

import br.com.gft.entities.Veterinarian;
import br.com.gft.exceptions.BusinessRuleException;
import br.com.gft.exceptions.ResourceNotFoundException;
import br.com.gft.repositories.VeterinarianRepository;

@ExtendWith(MockitoExtension.class)
class VeterinarianServiceTest {
	private static final String PHONE = "55999112233";
	private static final String CRMV = "12345";
	private static final String NAME = "Dr. John";
	private static final long ID = 1L;
	private static final String CHECK_IF_THROWS_BUSINESS_RULE_EXCEPTION = "Check if throws BusinessRuleException";
	private static final String CHECK_IF_THROWS_RESOURCE_NOT_FOUND_EXCEPTION = "Check if throws ResourceNotFoundException";
	private static final String CHECK_IF_PAGE_NOT_NULL_AND_CONTENT = "Check if page not null and content";
	private static final String CHECK_IF_NOT_NULL_OBJECT_CLASS_AND_ATTRIBUTES_VALUES = "Check if not null, object class and attributes values";
	
	
	private Veterinarian veterinarian;
	private Optional<Veterinarian> optionalVeterinarian;
	private Page<Veterinarian> page;
	private Pageable pageable;
	
	@Mock
	private VeterinarianRepository veterinarianRepository;
	
	@InjectMocks
	private VeterinarianService veterinarianService;
	
	@BeforeEach
	void setUp() throws Exception {
		startVeterinarian();
	}
	
	@Test
	@DisplayName("Should not throw an exception when checkCrmv")
	void whenCheckCrmvThenReturnSuccess(){
		veterinarianService.checkCrmv(veterinarian);
		verify(veterinarianRepository, times(1)).findByCrmv(anyString());
		
	}
	
	@Test
	@DisplayName("Should throws BusinessRuleException when checkCrmv")
	void whenCheckCrmvThenThrowsBusinessRuleException() {
		when(veterinarianRepository.findByCrmv(anyString())).thenThrow(BusinessRuleException.class);
		
		assertThrows(BusinessRuleException.class
				, () -> veterinarianService.checkCrmv(veterinarian)
				, CHECK_IF_THROWS_BUSINESS_RULE_EXCEPTION);
	}
	
	@Test
	@DisplayName("Should create a new Veterinarian")
	void whenCreateThenReturnSuccess() {
		when(veterinarianRepository.save(any())).thenReturn(veterinarian);
		Veterinarian response = veterinarianService.create(veterinarian);
		
		assertAll(CHECK_IF_NOT_NULL_OBJECT_CLASS_AND_ATTRIBUTES_VALUES
				, () -> assertNotNull(response)
				, () -> assertEquals(Veterinarian.class, response.getClass())
				, () -> assertEquals(ID, response.getId())
				, () -> assertEquals(NAME, response.getName())
				, () -> assertEquals(CRMV, response.getCrmv())
				, () -> assertEquals(PHONE, response.getPhone()));
	}
	
	@Test
	@DisplayName("Should return a Veterinarian found by id")
	void whenFindByIdThenReturnSuccess() {
		when(veterinarianRepository.findById(anyLong())).thenReturn(optionalVeterinarian);
		Veterinarian response = veterinarianService.findById(ID);
		
		assertAll(CHECK_IF_NOT_NULL_OBJECT_CLASS_AND_ATTRIBUTES_VALUES
				, () -> assertNotNull(response)
				, () -> assertEquals(Veterinarian.class, response.getClass())
				, () -> assertEquals(ID, response.getId())
				, () -> assertEquals(NAME, response.getName())
				, () -> assertEquals(CRMV, response.getCrmv())
				, () -> assertEquals(PHONE, response.getPhone()));
	}
	
	@Test
	@DisplayName("Should throws ResourceNotFoundException when findById")
	void whenFindByIdThenThrowsResourceNotFoundException() {
		when(veterinarianRepository.findById(anyLong())).thenThrow(ResourceNotFoundException.class);
		
		assertThrows(ResourceNotFoundException.class
				, () -> veterinarianService.findById(ID)
				, CHECK_IF_THROWS_RESOURCE_NOT_FOUND_EXCEPTION);
	}
	
	@Test
	@DisplayName("Should return a Page with list of Veterinarian")
	void whenFindAllReturnAPageWithListOfVeterinarian() {
		when(veterinarianRepository.findAll(pageable)).thenReturn(page);
		Page<Veterinarian> response = veterinarianService.findAll(pageable);
		
		assertAll(CHECK_IF_PAGE_NOT_NULL_AND_CONTENT
				, () -> assertNotNull(response)
				, () -> assertEquals(List.of(veterinarian), response.getContent())
				, () -> assertEquals(10, response.getSize())
				, () -> assertEquals(10, response.getTotalElements())
				, () -> assertEquals(1, response.getTotalPages()));
	}
	
	@Test
	@DisplayName("Should delete a Veterinarian")
	void whenDeleteThenReturnSuccess() {
		when(veterinarianRepository.findById(anyLong())).thenReturn(optionalVeterinarian);
		doNothing().when(veterinarianRepository).delete(veterinarian);
		veterinarianService.delete(ID);
		verify(veterinarianRepository, times(1)).delete(veterinarian);
	}
	
	@Test
	@DisplayName("Should not throw an exception when validUpdate")
	void whenValidUpdateThenReturnSuccess() {
		when(veterinarianRepository.findByCrmv(anyString())).thenReturn(optionalVeterinarian);
		veterinarianService.validUpdate(veterinarian);
		verify(veterinarianRepository, times(1)).findByCrmv(anyString());
	
	}
	
	@Test
	@DisplayName("Should throws BusinessRuleException when validUpdate")
	void whenValidUpdateThenThrowsBusinessRuleException() {
		when(veterinarianRepository.findByCrmv(anyString())).thenThrow(BusinessRuleException.class);
		assertThrows(BusinessRuleException.class
				, () -> veterinarianService.checkCrmv(veterinarian)
				, CHECK_IF_THROWS_BUSINESS_RULE_EXCEPTION);
	}
	
	@Test
	@DisplayName("Should update a Veterinarian")
	void whenUpdateThenReturnSuccess() {
		when(veterinarianRepository.findById(anyLong())).thenReturn(optionalVeterinarian);
		when(veterinarianRepository.save(any())).thenReturn(veterinarian);
		Veterinarian response = veterinarianService.update(ID, veterinarian);
		
		assertAll(CHECK_IF_NOT_NULL_OBJECT_CLASS_AND_ATTRIBUTES_VALUES
				, () -> assertNotNull(response)
				, () -> assertEquals(Veterinarian.class, response.getClass())
				, () -> assertEquals(ID, response.getId())
				, () -> assertEquals(NAME, response.getName())
				, () -> assertEquals(CRMV, response.getCrmv())
				, () -> assertEquals(PHONE, response.getPhone()));
		
	}
	
	@Test
	@DisplayName("Should throws ResourceNotFoundException when update")
	void whenUpdateThenThrowsResourceNotFoundException() {
		when(veterinarianRepository.findById(anyLong())).thenThrow(ResourceNotFoundException.class);
		assertThrows(ResourceNotFoundException.class
				, () -> veterinarianService.update(ID, veterinarian)
				, CHECK_IF_THROWS_RESOURCE_NOT_FOUND_EXCEPTION);
	}
	
	private void startVeterinarian() {
		veterinarian = new Veterinarian(ID, NAME, CRMV, PHONE, new ArrayList<>());
		optionalVeterinarian = Optional.of(veterinarian);
		pageable = PageRequest.of(0, 10);
		page = new PageImpl<>(List.of(veterinarian), pageable, 10);
	}
}
