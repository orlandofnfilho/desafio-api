package br.com.gft.controllers.docs;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import br.com.gft.dto.appointment.AppointmentRequestDTO;
import br.com.gft.dto.appointment.AppointmentResponseDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api("Manages appointments")
public interface AppointmentControllerDoc {
	
	public static final String DOG_ID_OR_VETERINARIAN_ID_NOT_FOUND = "Dog Id or Veterinarian Id not found";
	public static final String FAILED_TO_DELETE_RESOURCE_ASSOCIATED_WITH_ANOTHER = "Failed to delete, resource associated with another";
	public static final String APPOINTMENT_SUCCESSFULLY_DELETED = "Appointment successfully deleted";
	public static final String APPOINTMENT_SUCESSFULLY_FOUND = "Appointment sucessfully found";
	public static final String CONFLICT_APPOINTMENT_IS_ALREADY_SAVED = "Conflict, the Appointment is already saved.";
	public static final String NEW_APPOINTMENT_SUCCESSFULLY_CREATED = "New Appointment successfully created";
	public static final String APPOINTMENT_WITH_GIVEN_ID_NOT_FOUND = "Appointment with given id not found.";
	public static final String APPOINTMENT_SUCESSFULLY_UPDATED = "Appointment successfully updated";
	public static final String PAGEABLE_LIST_OF_ALL_APPOINTMENTS_REGISTERED_IN_THE_SYSTEM = "Pageable list of all Appointments registered in the system";
	public static final String PAGEABLE_LIST_OF_ALL_APPOINTMENTS_BY_DOG = "Pageable list of all Appointments by Dog RegCod";
	public static final String FORBIDDEN_DON_T_HAVE_PERMISSION_TO_ACCESS_THIS = "Forbidden. Don't have permission to access this";
	public static final String MISSING_REQUIRED_FIELDS_OR_WRONG_FIELD_RANGE_VALUE = "Missing required fields or wrong field range value.";
	public static final String PAGEABLE_LIST_OF_ALL_APPOINTMENTS_BY_VET = "Pageable list of all Appointments by Vet CRMV";
	

	@ApiOperation(value = "Create a new Appointment")
	@ApiResponses(value = {
	            @ApiResponse(code = 201, message = NEW_APPOINTMENT_SUCCESSFULLY_CREATED),
	            @ApiResponse(code = 400, message = MISSING_REQUIRED_FIELDS_OR_WRONG_FIELD_RANGE_VALUE),
	            @ApiResponse(code = 403, message = FORBIDDEN_DON_T_HAVE_PERMISSION_TO_ACCESS_THIS),
	            @ApiResponse(code = 404, message = DOG_ID_OR_VETERINARIAN_ID_NOT_FOUND)
	})
	public ResponseEntity<AppointmentResponseDTO> create(AppointmentRequestDTO dto);
	
	
	@ApiOperation(value = "Find an Appointment by Id")
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = APPOINTMENT_SUCESSFULLY_FOUND),
            @ApiResponse(code = 403, message = FORBIDDEN_DON_T_HAVE_PERMISSION_TO_ACCESS_THIS),
            @ApiResponse(code = 404, message = APPOINTMENT_WITH_GIVEN_ID_NOT_FOUND)
    })
	public ResponseEntity<AppointmentResponseDTO> findById(Long id);
	
	
	@ApiOperation(value = "Returns a pageable list of all Appointments")
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = PAGEABLE_LIST_OF_ALL_APPOINTMENTS_REGISTERED_IN_THE_SYSTEM),
    		@ApiResponse(code = 403, message = FORBIDDEN_DON_T_HAVE_PERMISSION_TO_ACCESS_THIS),
    })
	public ResponseEntity<Page<AppointmentResponseDTO>> findAll(Pageable pageable);
	
	@ApiOperation(value = "Returns a pageable list of all Appointments search by Dog RegCod")
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = PAGEABLE_LIST_OF_ALL_APPOINTMENTS_BY_DOG),
    		@ApiResponse(code = 403, message = FORBIDDEN_DON_T_HAVE_PERMISSION_TO_ACCESS_THIS),
    })
	public ResponseEntity<Page<AppointmentResponseDTO>> findByDog(Pageable pageable, String regCod);
	
	
	@ApiOperation(value = "Returns a pageable list of all Appointments search by Vet CRMV")
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = PAGEABLE_LIST_OF_ALL_APPOINTMENTS_BY_VET),
    		@ApiResponse(code = 403, message = FORBIDDEN_DON_T_HAVE_PERMISSION_TO_ACCESS_THIS),
    })
	public ResponseEntity<Page<AppointmentResponseDTO>> findByVet(Pageable pageable, String crmv);
	
	@ApiOperation(value = "Update Appointment by a given Id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = APPOINTMENT_SUCESSFULLY_UPDATED),
            @ApiResponse(code = 400, message = MISSING_REQUIRED_FIELDS_OR_WRONG_FIELD_RANGE_VALUE),
            @ApiResponse(code = 403, message = FORBIDDEN_DON_T_HAVE_PERMISSION_TO_ACCESS_THIS),
            @ApiResponse(code = 404, message = APPOINTMENT_WITH_GIVEN_ID_NOT_FOUND),
            @ApiResponse(code = 404, message = DOG_ID_OR_VETERINARIAN_ID_NOT_FOUND)
    })
	public ResponseEntity<AppointmentResponseDTO> update(Long id,AppointmentRequestDTO obj);


	@ApiOperation(value = "Delete an Appointment found by a given valid Id")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = APPOINTMENT_SUCCESSFULLY_DELETED),
            @ApiResponse(code = 400, message = FAILED_TO_DELETE_RESOURCE_ASSOCIATED_WITH_ANOTHER),
            @ApiResponse(code = 403, message = FORBIDDEN_DON_T_HAVE_PERMISSION_TO_ACCESS_THIS),
            @ApiResponse(code = 404, message = APPOINTMENT_WITH_GIVEN_ID_NOT_FOUND)
    })
	public ResponseEntity<AppointmentResponseDTO> delete(Long id);
}
