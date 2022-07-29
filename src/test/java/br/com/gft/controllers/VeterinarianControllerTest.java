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

import br.com.gft.dto.veterinarian.VeterinarianRequestDTO;
import br.com.gft.dto.veterinarian.VeterinarianResponseDTO;
import br.com.gft.entities.Veterinarian;
import br.com.gft.services.VeterinarianService;

@ExtendWith(MockitoExtension.class)
class VeterinarianControllerTest {
	
	
	private static final String CHECK_IF_NOT_NULL_HTTP_STATUS_AND_RESPONSE_BODY = "Check if not null, HttpStatus and response body";
	private static final String PHONE = "123456789";
	private static final String CRMV = "12345";
	private static final String NAME = "Dr. John";
	private static final long ID = 1L;
	private static final int INDEX = 0;
	private Veterinarian veterinarian;
	private VeterinarianRequestDTO requestDTO;
	private VeterinarianResponseDTO responseDTO;
	private Page<Veterinarian> page;
	private Pageable pageable;
	
	@Mock
	private VeterinarianService veterinarianService;
	
	@InjectMocks
	private VeterinarianController veterinarianController;

	@BeforeEach
	void setUp() throws Exception {
		startVeterinarian();
	}

	@Test
	@DisplayName("Should return a VeterinarianResponseDTO")
	void whenCreateThenReturnCreated() throws URISyntaxException{
		when(veterinarianService.create(any())).thenReturn(veterinarian);		

		MockHttpServletRequest request = new MockHttpServletRequest();
	    RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request)); 
		
		 ResponseEntity<VeterinarianResponseDTO> response  = veterinarianController.create(requestDTO);
			
		    assertAll(CHECK_IF_NOT_NULL_HTTP_STATUS_AND_RESPONSE_BODY
		    		, () -> assertNotNull(response)
		    		, () -> assertEquals(ResponseEntity.class, response.getClass())
		    		, () -> assertEquals(HttpStatus.CREATED, response.getStatusCode())
		    		, () -> assertEquals(response.getBody(), responseDTO));	
	}
	
	@Test
	@DisplayName("Should return a page with list of VeterinarianResponseDTO")
	void whenFindAllThenReturnAPageOfVeterinarianResponseDTO() {
		when(veterinarianService.findAll(pageable)).thenReturn(page);
		ResponseEntity<Page<VeterinarianResponseDTO>> response = veterinarianController.findAll(pageable);
		
		assertAll(CHECK_IF_NOT_NULL_HTTP_STATUS_AND_RESPONSE_BODY
				, () -> assertNotNull(response)
				, () -> assertNotNull(response.getBody())
				, () -> assertEquals(HttpStatus.OK, response.getStatusCode())
				, () -> assertEquals(ResponseEntity.class, response.getClass())
				, () -> assertEquals(PageImpl.class, response.getBody().getClass())
				, () -> assertEquals(VeterinarianResponseDTO.class, response.getBody().getContent().get(INDEX).getClass())
				, () -> assertEquals(ID, response.getBody().getContent().get(INDEX).getId())
				, () -> assertEquals(NAME, response.getBody().getContent().get(INDEX).getName()));
	}
	
	@Test
	@DisplayName("Should return a VeterinarianResponseDTO found by id")
	void whenFindByIdThenReturnSuccess() {
		when(veterinarianService.findById(ID)).thenReturn(veterinarian);
		ResponseEntity<VeterinarianResponseDTO> response = veterinarianController.findById(ID);
		
		assertAll(CHECK_IF_NOT_NULL_HTTP_STATUS_AND_RESPONSE_BODY
				, () -> assertNotNull(response)
				, () -> assertNotNull(response.getBody())
				, () -> assertEquals(HttpStatus.OK, response.getStatusCode())
				, () -> assertEquals(ResponseEntity.class, response.getClass())
				, () -> assertEquals(ID, response.getBody().getId())
				, () -> assertEquals(NAME, response.getBody().getName())
				, () -> assertEquals(CRMV, response.getBody().getCrmv())
				, () -> assertEquals(PHONE, response.getBody().getPhone()));
	}
	
	
	@Test
	@DisplayName("Should delete a Veterinarian then return noContent")
	void whenDeleteThenReturnNoContent() {
		doNothing().when(veterinarianService).delete(anyLong());
		
		ResponseEntity<VeterinarianResponseDTO> response = veterinarianController.delete(ID);
		assertAll(CHECK_IF_NOT_NULL_HTTP_STATUS_AND_RESPONSE_BODY
				, () -> assertNotNull(response)
				, () -> assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode())
				, () -> assertEquals(ResponseEntity.class, response.getClass()));
		verify(veterinarianService, times(1)).delete(anyLong());
	}
	
	@Test
	@DisplayName("Should update a Veterinarian then return VeterinarianResponseDTO")
	void whenUpdateThenReturnSuccess() {
		when(veterinarianService.update(anyLong(), any(Veterinarian.class))).thenReturn(veterinarian);
		
		ResponseEntity<VeterinarianResponseDTO> response = veterinarianController.update(ID, requestDTO);
		assertAll(CHECK_IF_NOT_NULL_HTTP_STATUS_AND_RESPONSE_BODY
				, () -> assertNotNull(response)
				, () -> assertNotNull(response.getBody())
				, () -> assertEquals(HttpStatus.OK, response.getStatusCode())
				, () -> assertEquals(ResponseEntity.class, response.getClass())
				, () -> assertEquals(ID, response.getBody().getId())
				, () -> assertEquals(NAME, response.getBody().getName())
				, () -> assertEquals(CRMV, response.getBody().getCrmv())
				, () -> assertEquals(PHONE, response.getBody().getPhone()));
		
	}
	
	private void startVeterinarian() {
		veterinarian = new Veterinarian(ID, NAME, CRMV, PHONE, new ArrayList<>());
		requestDTO = new VeterinarianRequestDTO(NAME, CRMV, PHONE);
		responseDTO = new VeterinarianResponseDTO(ID, NAME, CRMV, PHONE);
		pageable = PageRequest.of(0, 10);
		page = new PageImpl<>(List.of(veterinarian), pageable, 10);
	}

}
