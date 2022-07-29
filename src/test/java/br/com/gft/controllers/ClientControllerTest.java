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

import br.com.gft.dto.client.ClientRequestDTO;
import br.com.gft.dto.client.ClientResponseDTO;
import br.com.gft.entities.Client;
import br.com.gft.services.ClientService;

@ExtendWith(MockitoExtension.class)
class ClientControllerTest {
	
	
	private static final String CHECK_IF_NOT_NULL_HTTP_STATUS_AND_RESPONSE_BODY = "Check if not null, HttpStatus and response body";
	private static final String PHONE = "55999225566";
	private static final String CPF = "12345678901";
	private static final String NAME = "Alex Green";
	private static final long ID = 1L;
	private static final int INDEX = 0;
	

	private Client client;
	private ClientRequestDTO requestDTO;
	private ClientResponseDTO responseDTO;
	private Page<Client> page;
	private Pageable pageable;
	
	
	@Mock
	private ClientService clientService;
	
	@InjectMocks
	private ClientController clientController;
	
	@BeforeEach
	void setUp() throws Exception {
		startClient();
	}
	
	@Test
	@DisplayName("Should return a ClientResponseDTO")
	void whenCreateThenReturnCreated() throws URISyntaxException{
		when(clientService.create(any())).thenReturn(client);		

		MockHttpServletRequest request = new MockHttpServletRequest();
	    RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request)); 
		
		 ResponseEntity<ClientResponseDTO> response  = clientController.create(requestDTO);
			
		    assertAll(CHECK_IF_NOT_NULL_HTTP_STATUS_AND_RESPONSE_BODY
		    		, () -> assertNotNull(response)
		    		, () -> assertEquals(ResponseEntity.class, response.getClass())
		    		, () -> assertEquals(HttpStatus.CREATED, response.getStatusCode())
		    		, () -> assertEquals(response.getBody(), responseDTO));	
	}
	
	
	@Test
	@DisplayName("Should return a page with list of ClientResponseDTO")
	void whenFindAllThenReturnAPageOfClientResponseDTO() {
		when(clientService.findAll(pageable)).thenReturn(page);
		ResponseEntity<Page<ClientResponseDTO>> response = clientController.findAll(pageable);
		
		assertAll(CHECK_IF_NOT_NULL_HTTP_STATUS_AND_RESPONSE_BODY
				, () -> assertNotNull(response)
				, () -> assertNotNull(response.getBody())
				, () -> assertEquals(HttpStatus.OK, response.getStatusCode())
				, () -> assertEquals(ResponseEntity.class, response.getClass())
				, () -> assertEquals(PageImpl.class, response.getBody().getClass())
				, () -> assertEquals(ClientResponseDTO.class, response.getBody().getContent().get(INDEX).getClass())
				, () -> assertEquals(ID, response.getBody().getContent().get(INDEX).getId())
				, () -> assertEquals(NAME, response.getBody().getContent().get(INDEX).getName())
				, () -> assertEquals(CPF, response.getBody().getContent().get(INDEX).getCpf())
				, () -> assertEquals(PHONE, response.getBody().getContent().get(INDEX).getPhone()));
	}
	

	@Test
	@DisplayName("Should return a ClientResponseDTO found by id")
	void whenFindByIdThenReturnSuccess() {
		when(clientService.findById(ID)).thenReturn(client);
		ResponseEntity<ClientResponseDTO> response = clientController.findById(ID);
		
		assertAll(CHECK_IF_NOT_NULL_HTTP_STATUS_AND_RESPONSE_BODY
				, () -> assertNotNull(response)
				, () -> assertNotNull(response.getBody())
				, () -> assertEquals(HttpStatus.OK, response.getStatusCode())
				, () -> assertEquals(ResponseEntity.class, response.getClass())
				, () -> assertEquals(ID, response.getBody().getId())
				, () -> assertEquals(NAME, response.getBody().getName())
				, () -> assertEquals(CPF, response.getBody().getCpf())
				, () -> assertEquals(PHONE, response.getBody().getPhone()));
	}
	
	@Test
	@DisplayName("Should delete a Client then return noContent")
	void whenDeleteThenReturnNoContent() {
		doNothing().when(clientService).delete(anyLong());
		
		ResponseEntity<ClientResponseDTO> response = clientController.delete(ID);
		assertAll(CHECK_IF_NOT_NULL_HTTP_STATUS_AND_RESPONSE_BODY
				, () -> assertNotNull(response)
				, () -> assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode())
				, () -> assertEquals(ResponseEntity.class, response.getClass()));
		verify(clientService, times(1)).delete(anyLong());
	}
	
	@Test
	@DisplayName("Should update a Client then return ClientResponseDTO")
	void whenUpdateThenReturnSuccess() {
		when(clientService.update(anyLong(), any(Client.class))).thenReturn(client);
		
		ResponseEntity<ClientResponseDTO> response = clientController.update(ID, requestDTO);
		assertAll(CHECK_IF_NOT_NULL_HTTP_STATUS_AND_RESPONSE_BODY
				, () -> assertNotNull(response)
				, () -> assertNotNull(response.getBody())
				, () -> assertEquals(HttpStatus.OK, response.getStatusCode())
				, () -> assertEquals(ResponseEntity.class, response.getClass())
				, () -> assertEquals(ID, response.getBody().getId())
				, () -> assertEquals(NAME, response.getBody().getName())
				, () -> assertEquals(CPF, response.getBody().getCpf())
				, () -> assertEquals(PHONE, response.getBody().getPhone()));
		
	}

	private void startClient() {
		client = new Client(ID, NAME, CPF, PHONE, new ArrayList<>());
		requestDTO = new ClientRequestDTO(NAME, CPF, PHONE);
		responseDTO = new ClientResponseDTO(ID, NAME, CPF, PHONE, new ArrayList<>());
		pageable = PageRequest.of(0, 10);
		page = new PageImpl<>(List.of(client), pageable, 10);
	}
	
}
