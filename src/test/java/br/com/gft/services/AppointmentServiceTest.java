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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
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

import br.com.gft.entities.Appointment;
import br.com.gft.entities.Breed;
import br.com.gft.entities.Client;
import br.com.gft.entities.Dog;
import br.com.gft.entities.Veterinarian;
import br.com.gft.exceptions.ResourceNotFoundException;
import br.com.gft.repositories.AppointmentRepository;

@ExtendWith(MockitoExtension.class)
class AppointmentServiceTest {

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
	private static final String CHECK_IF_THROWS_RESOURCE_NOT_FOUND_EXCEPTION = "Check if throws ResourceNotFoundException";
	private static final String CHECK_IF_PAGE_NOT_NULL_AND_CONTENT = "Check if page not null and content";
	private static final String CHECK_IF_NOT_NULL_OBJECT_CLASS_AND_ATTRIBUTES_VALUES = "Check if not null, object class and attributes values";

	private Appointment appointment;
	private Optional<Appointment> optionalAppointment;
	private Dog dog;
	private Veterinarian veterinarian;
	private Page<Appointment> page;
	private Pageable pageable;

	@Mock
	private AppointmentRepository appointmentRepository;

	@Mock
	private DogService dogService;

	@Mock
	private VeterinarianService veterinarianService;

	@InjectMocks
	private AppointmentService appointmentService;

	@BeforeEach
	void setUp() throws Exception {
		startAppointment();
	}
	
	@Test
	@DisplayName("Should create an Appointment")
	void whenCreateThenReturnSuccess() {
		when(dogService.findById(anyLong())).thenReturn(dog);
		when(veterinarianService.findById(anyLong())).thenReturn(veterinarian);
		when(appointmentRepository.save(any())).thenReturn(appointment);
		Appointment response = appointmentService.create(appointment);
		
		assertAll(CHECK_IF_NOT_NULL_OBJECT_CLASS_AND_ATTRIBUTES_VALUES
				, () -> assertNotNull(response)
				, () -> assertEquals(Appointment.class, response.getClass())
				, () -> assertEquals(ID, response.getId())
				, () -> assertEquals(TUTOR, response.getTutor())
				, () -> assertEquals(TUTOR_CPF, response.getTutorCpf())
				, () -> assertEquals(DOG_NAME, response.getDog().getName())
				, () -> assertEquals(VET_NAME, response.getVeterinarian().getName())
				, () -> assertEquals(DIAGNOSTIC, response.getDiagnostic())
				, () -> assertEquals(COMMENTS, response.getComments()));	
	}
	
	@Test
	@DisplayName("Should return a Page with list of Appointments")
	void whenFindAllReturnAPageWithListOfAppointments() {
		when(appointmentRepository.findAll(pageable)).thenReturn(page);
		Page<Appointment> response = appointmentService.findAll(pageable);
		
		assertAll(CHECK_IF_PAGE_NOT_NULL_AND_CONTENT
				, () -> assertNotNull(response)
				, () -> assertEquals(List.of(appointment), response.getContent())
				, () -> assertEquals(10, response.getSize())
				, () -> assertEquals(10, response.getTotalElements())
				, () -> assertEquals(1, response.getTotalPages()));
	}
	
	@Test
	@DisplayName("Should delete an Appointment")
	void whenDeleteThenReturnSuccess() {
		when(appointmentRepository.findById(anyLong())).thenReturn(optionalAppointment);
		doNothing().when(appointmentRepository).delete(appointment);
		appointmentService.delete(ID);
		verify(appointmentRepository, times(1)).delete(appointment);
	}
	
	@Test
	@DisplayName("Should return an Appointment found by Id")
	void whenFindByIdThenReturnSuccess() {
		when(appointmentRepository.findById(anyLong())).thenReturn(optionalAppointment);
		Appointment response = appointmentService.findById(ID);
		
		assertAll(CHECK_IF_NOT_NULL_OBJECT_CLASS_AND_ATTRIBUTES_VALUES
				, () -> assertNotNull(response)
				, () -> assertEquals(Appointment.class, response.getClass())
				, () -> assertEquals(ID, response.getId())
				, () -> assertEquals(TUTOR, response.getTutor())
				, () -> assertEquals(TUTOR_CPF, response.getTutorCpf())
				, () -> assertEquals(DOG_NAME, response.getDog().getName())
				, () -> assertEquals(VET_NAME, response.getVeterinarian().getName())
				, () -> assertEquals(DIAGNOSTIC, response.getDiagnostic())
				, () -> assertEquals(COMMENTS, response.getComments()));
	}
	
	@Test
	@DisplayName("Should throws ResourceNotFoundException when findById")
	void whenFindByIdThenThrowsResourceNotFoundException() {
		when(appointmentRepository.findById(anyLong())).thenThrow(ResourceNotFoundException.class);
		
		assertThrows(ResourceNotFoundException.class
				, () -> appointmentService.findById(ID)
				, CHECK_IF_THROWS_RESOURCE_NOT_FOUND_EXCEPTION);
	}
	
	@Test
	@DisplayName("Should update a Dog")
	void whenUpdateThenReturnSuccess() {
		when(dogService.findById(anyLong())).thenReturn(dog);
		when(veterinarianService.findById(anyLong())).thenReturn(veterinarian);
		when(appointmentRepository.findById(anyLong())).thenReturn(optionalAppointment);
		when(appointmentRepository.save(any())).thenReturn(appointment);
		Appointment response = appointmentService.update(ID,appointment);
		
		assertAll(CHECK_IF_NOT_NULL_OBJECT_CLASS_AND_ATTRIBUTES_VALUES
				, () -> assertNotNull(response)
				, () -> assertEquals(Appointment.class, response.getClass())
				, () -> assertEquals(ID, response.getId())
				, () -> assertEquals(TUTOR, response.getTutor())
				, () -> assertEquals(TUTOR_CPF, response.getTutorCpf())
				, () -> assertEquals(DOG_NAME, response.getDog().getName())
				, () -> assertEquals(VET_NAME, response.getVeterinarian().getName())
				, () -> assertEquals(DIAGNOSTIC, response.getDiagnostic())
				, () -> assertEquals(COMMENTS, response.getComments()));	
	}
	
	@Test
	@DisplayName("Should return a Page with list of Appointments findByDogRegCod")
	void whenFindByDogReturnAPageWithListOfAppointmentByRegCod() {
		when(appointmentRepository.findByDog_RegCodIgnoreCase(any(), anyString()))
		.thenReturn(page);
		Page<Appointment> response = appointmentService.findByDog(pageable, DOG_REGCOD);
		
		
		assertAll(CHECK_IF_PAGE_NOT_NULL_AND_CONTENT
				, () -> assertNotNull(response)
				, () -> assertEquals(List.of(appointment), response.getContent())
				, () -> assertEquals(10, response.getSize())
				, () -> assertEquals(10, response.getTotalElements())
				, () -> assertEquals(1, response.getTotalPages())
				, () -> assertEquals(DOG_REGCOD, response.getContent()
						.get(0).getDog().getRegCod()));
	}
	
	@Test
	@DisplayName("Should return a Page with list of Appointments findByVet")
	void whenFindByVetReturnApageWithListOfAppointmentByVet() {
		when(appointmentRepository.findByVeterinarian_crmvIgnoreCase(any(), anyString()))
		.thenReturn(page);
		Page<Appointment> response = appointmentService.findByVet(pageable, VET_CRMV);
		
		
		assertAll(CHECK_IF_PAGE_NOT_NULL_AND_CONTENT
				, () -> assertNotNull(response)
				, () -> assertEquals(List.of(appointment), response.getContent())
				, () -> assertEquals(10, response.getSize())
				, () -> assertEquals(10, response.getTotalElements())
				, () -> assertEquals(1, response.getTotalPages())
				, () -> assertEquals(VET_CRMV, response.getContent()
						.get(0).getVeterinarian().getCrmv()));
	}
	
	
	private void startAppointment() {
		veterinarian = new Veterinarian(ID, VET_NAME, VET_CRMV, VET_PHONE, new ArrayList<>());
		dog = new Dog(ID, DOG_NAME, DOG_REGCOD, LocalDate.of(2010, 1, 1), new Breed(),
				new Client(ID, TUTOR, TUTOR_CPF, TUTOR_PHONE, new ArrayList<>()), new ArrayList<>());
		appointment = new Appointment(ID, ZonedDateTime.now(ZoneId.of("UTC")), dog.getTutor().getName(),
				dog.getTutor().getCpf(), veterinarian, dog, 1, BigDecimal.valueOf(2.0), DIAGNOSTIC, COMMENTS);
		optionalAppointment = Optional.of(appointment);
		pageable = PageRequest.of(0, 10);
		page = new PageImpl<>(List.of(appointment), pageable, 10);

	}

}
