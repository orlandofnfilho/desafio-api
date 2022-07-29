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

import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
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

import br.com.gft.dto.appointment.AppointmentRequestDTO;
import br.com.gft.dto.appointment.AppointmentResponseDTO;
import br.com.gft.dto.breed.BreedResponseDTO;
import br.com.gft.dto.dog.DogResponseDTO;
import br.com.gft.dto.veterinarian.VeterinarianResponseDTO;
import br.com.gft.entities.Appointment;
import br.com.gft.entities.Breed;
import br.com.gft.entities.Client;
import br.com.gft.entities.Dog;
import br.com.gft.entities.Veterinarian;
import br.com.gft.services.AppointmentService;

@ExtendWith(MockitoExtension.class)
class AppointmentControllerTest {

	private static final String CHECK_IF_NOT_NULL_HTTP_STATUS_AND_RESPONSE_BODY = "Check if not null, HttpStatus and response body";
	private static final BigDecimal DOG_ACTUAL_WEIGHT = BigDecimal.valueOf(2.0);
	private static final int DOG_ACTUAL_AGE = 1;
	private static final String COMMENTS = "Comments";
	private static final String DIAGNOSTIC = "Diagnostic";
	private static final String TUTOR_PHONE = "55988778855";
	private static final String TUTOR_CPF = "12345678901";
	private static final String TUTOR = "Alex Green";
	private static final String DOG_REGCOD = "ALX123";
	private static final String DOG_NAME = "Milo";
	private static final String VET_PHONE = "55999445566";
	private static final String VET_CRMV = "12345";
	private static final String VET_NAME = "Dr. John";
	private static final long ID = 1L;
	private static final int INDEX = 0;

	private Appointment appointment;
	private AppointmentRequestDTO requestDTO;
	private AppointmentResponseDTO responseDTO;
	private DogResponseDTO dogResponseDTO;
	private BreedResponseDTO breedResponseDTO;
	private VeterinarianResponseDTO veterinarianResponseDTO;
	private Dog dog;
	private Veterinarian veterinarian;
	private Page<Appointment> page;
	private Pageable pageable;

	@Mock
	private AppointmentService appointmentService;

	@InjectMocks
	private AppointmentController appointmentController;

	@Test
	@DisplayName("Should return a AppointmentResponseDTO")
	void whenCreateThenReturnCreated() throws URISyntaxException {
		when(appointmentService.create(any())).thenReturn(appointment);

		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

		ResponseEntity<AppointmentResponseDTO> response = appointmentController.create(requestDTO);

		assertAll(CHECK_IF_NOT_NULL_HTTP_STATUS_AND_RESPONSE_BODY, () -> assertNotNull(response)
				, () -> assertEquals(ResponseEntity.class, response.getClass())
				, () -> assertEquals(HttpStatus.CREATED, response.getStatusCode())
				, () -> assertEquals(response.getBody().getId(), responseDTO.getId())
				, () -> assertEquals(response.getBody().getTutor(), responseDTO.getTutor())
				, () -> assertEquals(response.getBody().getDog().getRegCod(), responseDTO.getDog().getRegCod())
				, () -> assertEquals(response.getBody().getVeterinarian().getId(), requestDTO.getVeterinarianId()));
	}
	
	@Test
	@DisplayName("Should return a page with list of AppointmentResponseDTO")
	void whenFindAllThenReturnAPageOfAppointmentResponseDTO() {
		when(appointmentService.findAll(pageable)).thenReturn(page);
		ResponseEntity<Page<AppointmentResponseDTO>> response = appointmentController.findAll(pageable);
		
		assertAll(CHECK_IF_NOT_NULL_HTTP_STATUS_AND_RESPONSE_BODY
				, () -> assertNotNull(response)
				, () -> assertNotNull(response.getBody())
				, () -> assertEquals(HttpStatus.OK, response.getStatusCode())
				, () -> assertEquals(ResponseEntity.class, response.getClass())
				, () -> assertEquals(PageImpl.class, response.getBody().getClass())
				, () -> assertEquals(AppointmentResponseDTO.class, response.getBody().getContent().get(INDEX).getClass())
				, () -> assertEquals(ID, response.getBody().getContent().get(INDEX).getId())
				, () -> assertEquals(DOG_REGCOD, response.getBody().getContent().get(INDEX).getDog().getRegCod())
				, () -> assertEquals(ID, response.getBody().getContent().get(INDEX).getVeterinarian().getId()));
	}
	
	@Test
	@DisplayName("Should return a AppointmentResponseDTO found by id")
	void whenFindByIdThenReturnSuccess() {
		when(appointmentService.findById(ID)).thenReturn(appointment);
		ResponseEntity<AppointmentResponseDTO> response = appointmentController.findById(ID);
		
		assertAll(CHECK_IF_NOT_NULL_HTTP_STATUS_AND_RESPONSE_BODY
				, () -> assertNotNull(response)
				, () -> assertNotNull(response.getBody())
				, () -> assertEquals(HttpStatus.OK, response.getStatusCode())
				, () -> assertEquals(ResponseEntity.class, response.getClass())
				, () -> assertEquals(ID, response.getBody().getId())
				, () -> assertEquals(DOG_REGCOD, response.getBody().getDog().getRegCod())
				, () -> assertEquals(VET_CRMV, response.getBody().getVeterinarian().getCrmv())
				, () -> assertEquals(TUTOR, response.getBody().getTutor()));
	}

	@Test
	@DisplayName("Should delete a Appointment then return noContent")
	void whenDeleteThenReturnNoContent() {
		doNothing().when(appointmentService).delete(anyLong());
		
		ResponseEntity<AppointmentResponseDTO> response = appointmentController.delete(ID);
		assertAll(CHECK_IF_NOT_NULL_HTTP_STATUS_AND_RESPONSE_BODY
				, () -> assertNotNull(response)
				, () -> assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode())
				, () -> assertEquals(ResponseEntity.class, response.getClass()));
		verify(appointmentService, times(1)).delete(anyLong());
	}
	
	@Test
	@DisplayName("Should update a Appointment then return AppointmentResponseDTO")
	void whenUpdateThenReturnSuccess() {
		when(appointmentService.update(anyLong(), any(Appointment.class))).thenReturn(appointment);
		
		ResponseEntity<AppointmentResponseDTO> response = appointmentController.update(ID, requestDTO);
		assertAll(CHECK_IF_NOT_NULL_HTTP_STATUS_AND_RESPONSE_BODY
				, () -> assertNotNull(response)
				, () -> assertNotNull(response.getBody())
				, () -> assertEquals(HttpStatus.OK, response.getStatusCode())
				, () -> assertEquals(ResponseEntity.class, response.getClass())
				, () -> assertEquals(ID, response.getBody().getId())
				, () -> assertEquals(DOG_REGCOD, response.getBody().getDog().getRegCod())
				, () -> assertEquals(VET_CRMV, response.getBody().getVeterinarian().getCrmv())
				, () -> assertEquals(TUTOR, response.getBody().getTutor()));
		
	}
	
	
	@Test
	@DisplayName("Should return a page with list of AppointmentResponseDTO by Dog regCod")
	void whenFindByDogThenReturnAPageOfAppointmentResponseDTO() {
		when(appointmentService.findByDog(any(), any())).thenReturn(page);
		ResponseEntity<Page<AppointmentResponseDTO>> response = appointmentController.findByDog(pageable, DOG_REGCOD);
		
		assertAll(CHECK_IF_NOT_NULL_HTTP_STATUS_AND_RESPONSE_BODY
				, () -> assertNotNull(response)
				, () -> assertNotNull(response.getBody())
				, () -> assertEquals(HttpStatus.OK, response.getStatusCode())
				, () -> assertEquals(ResponseEntity.class, response.getClass())
				, () -> assertEquals(PageImpl.class, response.getBody().getClass())
				, () -> assertEquals(AppointmentResponseDTO.class, response.getBody().getContent().get(INDEX).getClass())
				, () -> assertEquals(ID, response.getBody().getContent().get(INDEX).getId())
				, () -> assertEquals(DOG_REGCOD, response.getBody().getContent().get(INDEX).getDog().getRegCod())
				, () -> assertEquals(ID, response.getBody().getContent().get(INDEX).getVeterinarian().getId()));
	}
	
	@Test
	@DisplayName("Should return a page with list of AppointmentResponseDTO by Vet CRMV")
	void whenFindByVetThenReturnAPageOfAppointmentResponseDTO() {
		when(appointmentService.findByVet(any(), any())).thenReturn(page);
		ResponseEntity<Page<AppointmentResponseDTO>> response = appointmentController.findByVet(pageable, VET_CRMV);
		
		assertAll(CHECK_IF_NOT_NULL_HTTP_STATUS_AND_RESPONSE_BODY
				, () -> assertNotNull(response)
				, () -> assertNotNull(response.getBody())
				, () -> assertEquals(HttpStatus.OK, response.getStatusCode())
				, () -> assertEquals(ResponseEntity.class, response.getClass())
				, () -> assertEquals(PageImpl.class, response.getBody().getClass())
				, () -> assertEquals(AppointmentResponseDTO.class, response.getBody().getContent().get(INDEX).getClass())
				, () -> assertEquals(ID, response.getBody().getContent().get(INDEX).getId())
				, () -> assertEquals(DOG_REGCOD, response.getBody().getContent().get(INDEX).getDog().getRegCod())
				, () -> assertEquals(ID, response.getBody().getContent().get(INDEX).getVeterinarian().getId()));
	}
	
	
	@BeforeEach
	void setUp() throws Exception {
		startAppointment();
	}

	private void startAppointment() {
		veterinarian = new Veterinarian(ID, VET_NAME, VET_CRMV, VET_PHONE, new ArrayList<>());
		veterinarianResponseDTO = new VeterinarianResponseDTO(ID, VET_NAME, VET_CRMV, VET_PHONE);
		dog = new Dog(ID, DOG_NAME, DOG_REGCOD, LocalDate.of(2010, 1, 1), new Breed(),
				new Client(ID, TUTOR, TUTOR_CPF, TUTOR_PHONE, new ArrayList<>()), new ArrayList<>());

		dogResponseDTO = new DogResponseDTO(ID, DOG_NAME, DOG_REGCOD, LocalDate.of(2010, 1, 1), ID, breedResponseDTO);
		appointment = new Appointment(ID, ZonedDateTime.now(ZoneId.of("UTC")), TUTOR, TUTOR_CPF, veterinarian, dog,
				DOG_ACTUAL_AGE, DOG_ACTUAL_WEIGHT, DIAGNOSTIC, COMMENTS);

		requestDTO = new AppointmentRequestDTO(ID, ID, DOG_ACTUAL_AGE, DOG_ACTUAL_WEIGHT, DIAGNOSTIC, COMMENTS);

		responseDTO = new AppointmentResponseDTO(ID, ZonedDateTime.now(ZoneId.of("UTC")), TUTOR, TUTOR_CPF,
				veterinarianResponseDTO, dogResponseDTO, DOG_ACTUAL_AGE, DOG_ACTUAL_WEIGHT, DIAGNOSTIC, COMMENTS);
		pageable = PageRequest.of(0, 10);
		page = new PageImpl<>(List.of(appointment), pageable, 10);

	}

}
