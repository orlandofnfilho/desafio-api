package br.com.gft.controllers.docs;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import br.com.gft.dto.breed.BreedRequestDTO;
import br.com.gft.dto.breed.BreedResponseDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(description = "Manage breeds", tags = { "3. Breeds" })
public interface BreedControllerDoc {

	public static final String FAILED_TO_DELETE_RESOURCE_ASSOCIATED_WITH_ANOTHER = "Failed to delete, resource associated with another";
	public static final String BREED_SUCCESSFULLY_DELETED = "Breed successfully deleted";
	public static final String BREED_SUCESSFULLY_FOUND = "Breed sucessfully found";
	public static final String CONFLICT_BREED_IS_ALREADY_SAVED = "Conflict, the breed is already saved.";
	public static final String NEW_BREED_SUCCESSFULLY_CREATED = "New breed successfully created";
	public static final String BREED_WITH_GIVEN_ID_NOT_FOUND = "Breed with given id not found.";
	public static final String BREED_SUCESSFULLY_UPDATED = "Breed successfully updated";
	public static final String PAGEABLE_LIST_OF_ALL_BREEDS_REGISTERED_IN_THE_SYSTEM = "Pageable list of all breeds registered in the system";
	public static final String FORBIDDEN_DON_T_HAVE_PERMISSION_TO_ACCESS_THIS = "Forbidden. Don't have permission to access this";
	public static final String MISSING_REQUIRED_FIELDS_OR_WRONG_FIELD_RANGE_VALUE = "Missing required fields or wrong field range value.";
	
	
	@ApiOperation(value = "Create a new Breed")
	 @ApiResponses(value = {
	            @ApiResponse(code = 201, message = NEW_BREED_SUCCESSFULLY_CREATED),
	            @ApiResponse(code = 400, message = MISSING_REQUIRED_FIELDS_OR_WRONG_FIELD_RANGE_VALUE),
	            @ApiResponse(code = 403, message = FORBIDDEN_DON_T_HAVE_PERMISSION_TO_ACCESS_THIS),
	            @ApiResponse(code = 409, message = CONFLICT_BREED_IS_ALREADY_SAVED)
	    })
	public ResponseEntity<BreedResponseDTO> create(BreedRequestDTO obj);
	
	@ApiOperation(value = "Find a Breed by id")
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = BREED_SUCESSFULLY_FOUND),
            @ApiResponse(code = 403, message = FORBIDDEN_DON_T_HAVE_PERMISSION_TO_ACCESS_THIS),
            @ApiResponse(code = 404, message = BREED_SUCCESSFULLY_DELETED)
    })
	public ResponseEntity<BreedResponseDTO> findById(Long id);
	
	
	@ApiOperation(value = "Returns a pageable list of all Breeds")
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = PAGEABLE_LIST_OF_ALL_BREEDS_REGISTERED_IN_THE_SYSTEM),
    		@ApiResponse(code = 403, message = FORBIDDEN_DON_T_HAVE_PERMISSION_TO_ACCESS_THIS),
    })
	public ResponseEntity<Page<BreedResponseDTO>> findAll(Pageable pageable);
	
	
	@ApiOperation(value = "Update Breed by a given Id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = BREED_SUCESSFULLY_UPDATED),
            @ApiResponse(code = 400, message = MISSING_REQUIRED_FIELDS_OR_WRONG_FIELD_RANGE_VALUE),
            @ApiResponse(code = 403, message = FORBIDDEN_DON_T_HAVE_PERMISSION_TO_ACCESS_THIS),
            @ApiResponse(code = 404, message = BREED_WITH_GIVEN_ID_NOT_FOUND),
            @ApiResponse(code = 409, message = CONFLICT_BREED_IS_ALREADY_SAVED)
    })
	public ResponseEntity<BreedResponseDTO> update(Long id,BreedRequestDTO obj);
	
	
	@ApiOperation(value = "Delete a Breed found by a given valid Id")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = BREED_SUCCESSFULLY_DELETED),
            @ApiResponse(code = 400, message = FAILED_TO_DELETE_RESOURCE_ASSOCIATED_WITH_ANOTHER),
            @ApiResponse(code = 403, message = FORBIDDEN_DON_T_HAVE_PERMISSION_TO_ACCESS_THIS),
            @ApiResponse(code = 404, message = BREED_WITH_GIVEN_ID_NOT_FOUND)
    })
	public ResponseEntity<BreedResponseDTO> delete(Long id);
	
}
