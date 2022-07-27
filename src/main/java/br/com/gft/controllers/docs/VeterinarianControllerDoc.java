package br.com.gft.controllers.docs;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import br.com.gft.dto.veterinarian.VeterinarianRequestDTO;
import br.com.gft.dto.veterinarian.VeterinarianResponseDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api("Manages veterinarians")
public interface VeterinarianControllerDoc {
	
	public static final String FAILED_TO_DELETE_RESOURCE_ASSOCIATED_WITH_ANOTHER = "Failed to delete, resource associated with another";
	public static final String VET_SUCCESSFULLY_DELETED = "Veterinarian successfully deleted";
	public static final String VET_SUCESSFULLY_FOUND = "Veterinarian sucessfully found";
	public static final String CONFLICT_VET_IS_ALREADY_SAVED = "Conflict, the veterinarian is already saved.";
	public static final String NEW_VET_SUCCESSFULLY_CREATED = "New veterinarian successfully created";
	public static final String VET_WITH_GIVEN_ID_NOT_FOUND = "Veterinarian with given id not found.";
	public static final String VET_SUCESSFULLY_UPDATED = "Vet successfully updated";
	public static final String PAGEABLE_LIST_OF_ALL_VETS_REGISTERED_IN_THE_SYSTEM = "Pageable list of all vets registered in the system";
	public static final String FORBIDDEN_DON_T_HAVE_PERMISSION_TO_ACCESS_THIS = "Forbidden. Don't have permission to access this";
	public static final String MISSING_REQUIRED_FIELDS_OR_WRONG_FIELD_RANGE_VALUE = "Missing required fields or wrong field range value.";

	
	@ApiOperation(value = "Create a new Veterinarian")
	@ApiResponses(value = {
	            @ApiResponse(code = 201, message = NEW_VET_SUCCESSFULLY_CREATED),
	            @ApiResponse(code = 400, message = MISSING_REQUIRED_FIELDS_OR_WRONG_FIELD_RANGE_VALUE),
	            @ApiResponse(code = 403, message = FORBIDDEN_DON_T_HAVE_PERMISSION_TO_ACCESS_THIS),
	            @ApiResponse(code = 409, message = CONFLICT_VET_IS_ALREADY_SAVED)
	})
	public ResponseEntity<VeterinarianResponseDTO> create(VeterinarianRequestDTO obj);
	
	
	@ApiOperation(value = "Find a Veterinarian by Id")
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = VET_SUCESSFULLY_FOUND),
            @ApiResponse(code = 403, message = FORBIDDEN_DON_T_HAVE_PERMISSION_TO_ACCESS_THIS),
            @ApiResponse(code = 404, message = VET_WITH_GIVEN_ID_NOT_FOUND)
    })
	public ResponseEntity<VeterinarianResponseDTO> findById(Long id);
	
	
	
	@ApiOperation(value = "Returns a pageable list of all Veterinarians")
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = PAGEABLE_LIST_OF_ALL_VETS_REGISTERED_IN_THE_SYSTEM),
    		@ApiResponse(code = 403, message = FORBIDDEN_DON_T_HAVE_PERMISSION_TO_ACCESS_THIS),
    })
	public ResponseEntity<Page<VeterinarianResponseDTO>> findAll(Pageable pageable);
	
	
	
	@ApiOperation(value = "Update Veterinarian by a given Id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = VET_SUCESSFULLY_UPDATED),
            @ApiResponse(code = 400, message = MISSING_REQUIRED_FIELDS_OR_WRONG_FIELD_RANGE_VALUE),
            @ApiResponse(code = 403, message = FORBIDDEN_DON_T_HAVE_PERMISSION_TO_ACCESS_THIS),
            @ApiResponse(code = 404, message = VET_WITH_GIVEN_ID_NOT_FOUND),
            @ApiResponse(code = 409, message = CONFLICT_VET_IS_ALREADY_SAVED)
    })
	public ResponseEntity<VeterinarianResponseDTO> update(Long id, VeterinarianRequestDTO obj);
	
	
	@ApiOperation(value = "Delete a Veterianrian found by a given valid Id")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = VET_SUCCESSFULLY_DELETED),
            @ApiResponse(code = 400, message = FAILED_TO_DELETE_RESOURCE_ASSOCIATED_WITH_ANOTHER),
            @ApiResponse(code = 403, message = FORBIDDEN_DON_T_HAVE_PERMISSION_TO_ACCESS_THIS),
            @ApiResponse(code = 404, message = VET_WITH_GIVEN_ID_NOT_FOUND)
    })
	public ResponseEntity<VeterinarianResponseDTO> delete(Long id);

}
