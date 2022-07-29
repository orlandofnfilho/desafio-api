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

import br.com.gft.entities.Client;
import br.com.gft.exceptions.BusinessRuleException;
import br.com.gft.exceptions.ResourceNotFoundException;
import br.com.gft.repositories.ClientRepository;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

	private static final String PHONE = "55999225566";
	private static final String CPF = "12345678901";
	private static final String NAME = "Alex Green";
	private static final long ID = 1L;
	private static final String CHECK_IF_THROWS_BUSINESS_RULE_EXCEPTION = "Check if throws BusinessRuleException";
	private static final String CHECK_IF_THROWS_RESOURCE_NOT_FOUND_EXCEPTION = "Check if throws ResourceNotFoundException";
	private static final String CHECK_IF_PAGE_NOT_NULL_AND_CONTENT = "Check if page not null and content";
	private static final String CHECK_IF_NOT_NULL_OBJECT_CLASS_AND_ATTRIBUTES_VALUES = "Check if not null, object class and attributes values";

	
	private Client client;
	private Optional<Client> optionalClient;
	private Page<Client> page;
	private Pageable pageable;
	
	@Mock
	private ClientRepository clientRepository;
	
	@InjectMocks
	private ClientService clientService;
	
	
	@BeforeEach
	void setUp() throws Exception {
		startClient();
	}
	
	@Test
	@DisplayName("Should not throw a exception when checkCpf")
	void whenCheckCpfThenReturnSuccess() {
		clientService.checkCpf(client);
		verify(clientRepository, times(1)).findByCpf(anyString());
	}
	
	@Test
	@DisplayName("Should throws BusinessRuleException when checkCpf")
	void whenCheckCpfThenThrowsBusinessRuleException() {
		when(clientRepository.findByCpf(anyString())).thenThrow(BusinessRuleException.class);
		
		assertThrows(BusinessRuleException.class
				, () -> clientService.checkCpf(client)
				, CHECK_IF_THROWS_BUSINESS_RULE_EXCEPTION);
	}
	
	@Test
	@DisplayName("Should create a new Client")
	void whenCreateThenReturnSuccess() {
		when(clientRepository.save(any())).thenReturn(client);
		Client response = clientService.create(client);
		
		assertAll(CHECK_IF_NOT_NULL_OBJECT_CLASS_AND_ATTRIBUTES_VALUES
				, () -> assertNotNull(response)
				, () -> assertEquals(Client.class, response.getClass())
				, () -> assertEquals(ID, response.getId())
				, () -> assertEquals(NAME, response.getName())
				, () -> assertEquals(CPF, response.getCpf())
				, () -> assertEquals(PHONE, response.getPhone())
				, () -> assertNotNull(response.getPets()));
	}
	
	@Test
	@DisplayName("Should return a Client found by id")
	void whenFindByidThenReturnsSuccess() {
		when(clientRepository.findById(anyLong())).thenReturn(optionalClient);
		Client response = clientService.findById(ID);
		
		assertAll(CHECK_IF_NOT_NULL_OBJECT_CLASS_AND_ATTRIBUTES_VALUES
				, () -> assertNotNull(response)
				, () -> assertEquals(Client.class, response.getClass())
				, () -> assertEquals(ID, response.getId())
				, () -> assertEquals(NAME, response.getName())
				, () -> assertEquals(CPF, response.getCpf())
				, () -> assertEquals(PHONE, response.getPhone())
				, () -> assertNotNull(response.getPets()));
	}
	
	@Test
	@DisplayName("Should throws ResourceNotFoundException when findById")
	void whenFindByIdThrowsResourceNotFoundException() {
		when(clientRepository.findById(anyLong())).thenThrow(ResourceNotFoundException.class);
		
		assertThrows(ResourceNotFoundException.class
				, () -> clientService.findById(ID)
				, CHECK_IF_THROWS_RESOURCE_NOT_FOUND_EXCEPTION);
	}
	
	
	@Test
	@DisplayName("Should return a Page with list of Client")
	void whenFindAllThenReturnSuccess() {
		when(clientRepository.findAll(pageable)).thenReturn(page);
		Page<Client> response = clientService.findAll(pageable);
		
		assertAll(CHECK_IF_PAGE_NOT_NULL_AND_CONTENT
				, () -> assertNotNull(response)
				, () -> assertEquals(List.of(client), response.getContent())
				, () -> assertEquals(10, response.getSize())
				, () -> assertEquals(10, response.getTotalElements())
				, () -> assertEquals(1, response.getTotalPages()));
	}
	
	@Test
	@DisplayName("Should delete a Client")
	void whenDeleteThenReturnSuccess() {
		when(clientRepository.findById(anyLong())).thenReturn(optionalClient);
		doNothing().when(clientRepository).delete(client);
		clientService.delete(ID);
		verify(clientRepository, times(1)).delete(client);
	}
	
	@Test
	@DisplayName("Should not throw an exception when validUpdate")
	void whenValidUpdateThenReturnSuccess() {
		when(clientRepository.findByCpf(anyString())).thenReturn(optionalClient);
		clientService.validUpdate(client);
		verify(clientRepository, times(1)).findByCpf(anyString());
	
	}
	
	@Test
	@DisplayName("Should throws BusinessRuleException when validUpdate")
	void whenValidUpdateThenThrowsBusinessRuleException() {
		when(clientRepository.findByCpf(anyString())).thenThrow(BusinessRuleException.class);
		assertThrows(BusinessRuleException.class
				, () -> clientService.checkCpf(client)
				, CHECK_IF_THROWS_BUSINESS_RULE_EXCEPTION);
	}
	
	@Test
	@DisplayName("Should update a Client")
	void whenUpdateThenReturnSuccess() {
		when(clientRepository.findById(anyLong())).thenReturn(optionalClient);
		when(clientRepository.save(any())).thenReturn(client);
		Client response = clientService.update(ID, client);
		
		assertAll(CHECK_IF_NOT_NULL_OBJECT_CLASS_AND_ATTRIBUTES_VALUES
				, () -> assertNotNull(response)
				, () -> assertEquals(Client.class, response.getClass())
				, () -> assertEquals(ID, response.getId())
				, () -> assertEquals(NAME, response.getName())
				, () -> assertEquals(CPF, response.getCpf())
				, () -> assertEquals(PHONE, response.getPhone()));
		
	}


	
	private void startClient() {
		client = new Client(ID, NAME, CPF, PHONE, new ArrayList<>());
		optionalClient = Optional.of(client);
		pageable = PageRequest.of(0, 10);
		page = new PageImpl<>(List.of(client), pageable, 10);
		
	}

}
