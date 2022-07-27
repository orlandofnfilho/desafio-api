package br.com.gft.controllers.docs;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import br.com.gft.dto.dog.DogRequestDTO;
import br.com.gft.dto.dog.DogResponseDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(description = "Manage dogs", tags = { "6. Dogs" })
public interface DogControllerDoc {

	public static final String FAILED_TO_DELETE_RESOURCE_ASSOCIATED_WITH_ANOTHER = "Failed to delete, resource associated with another";
	public static final String DOG_SUCCESSFULLY_DELETED = "Dog successfully deleted";
	public static final String DOG_SUCESSFULLY_FOUND = "Dog sucessfully found";
	public static final String CONFLICT_DOG_IS_ALREADY_SAVED = "Conflict, the Dog is already saved.";
	public static final String NEW_DOG_SUCCESSFULLY_CREATED = "New Dog successfully created";
	public static final String DOG_WITH_GIVEN_ID_NOT_FOUND = "Dog with given id not found.";
	public static final String DOG_SUCESSFULLY_UPDATED = "Dog successfully updated";
	public static final String PAGEABLE_LIST_OF_ALL_DOGS_REGISTERED_IN_THE_SYSTEM = "Pageable list of all Dogs registered in the system";
	public static final String FORBIDDEN_DON_T_HAVE_PERMISSION_TO_ACCESS_THIS = "Forbidden. Don't have permission to access this";
	public static final String MISSING_REQUIRED_FIELDS_OR_WRONG_FIELD_RANGE_VALUE = "Missing required fields or wrong field range value.";
	
	@ApiOperation(value = "Create a new dog")
	@ApiResponses(value = {
	            @ApiResponse(code = 201, message = NEW_DOG_SUCCESSFULLY_CREATED),
	            @ApiResponse(code = 400, message = MISSING_REQUIRED_FIELDS_OR_WRONG_FIELD_RANGE_VALUE),
	            @ApiResponse(code = 403, message = FORBIDDEN_DON_T_HAVE_PERMISSION_TO_ACCESS_THIS)
	    })
	public ResponseEntity<DogResponseDTO> create(DogRequestDTO obj);
	
	
	@ApiOperation(value = "Find a dog by Id")
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = DOG_SUCESSFULLY_FOUND),
            @ApiResponse(code = 403, message = FORBIDDEN_DON_T_HAVE_PERMISSION_TO_ACCESS_THIS),
            @ApiResponse(code = 404, message = DOG_WITH_GIVEN_ID_NOT_FOUND)
    })
	public ResponseEntity<DogResponseDTO> findById(Long id);
	
	
	@ApiOperation(value = "Returns a pageable list of all dogs")
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = PAGEABLE_LIST_OF_ALL_DOGS_REGISTERED_IN_THE_SYSTEM),
    		@ApiResponse(code = 403, message = FORBIDDEN_DON_T_HAVE_PERMISSION_TO_ACCESS_THIS),
    })
	public ResponseEntity<Page<DogResponseDTO>> findAll(Pageable pageable);
	
	
	@ApiOperation(value = "Update dog by a given Id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = DOG_SUCESSFULLY_UPDATED),
            @ApiResponse(code = 400, message = MISSING_REQUIRED_FIELDS_OR_WRONG_FIELD_RANGE_VALUE),
            @ApiResponse(code = 403, message = FORBIDDEN_DON_T_HAVE_PERMISSION_TO_ACCESS_THIS),
            @ApiResponse(code = 404, message = DOG_WITH_GIVEN_ID_NOT_FOUND)
    })
	public ResponseEntity<DogResponseDTO> update(Long id, DogRequestDTO obj);
	
	
	@ApiOperation(value = "Delete a dog found by a given valid Id")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = DOG_SUCCESSFULLY_DELETED),
            @ApiResponse(code = 400, message = FAILED_TO_DELETE_RESOURCE_ASSOCIATED_WITH_ANOTHER),
            @ApiResponse(code = 403, message = FORBIDDEN_DON_T_HAVE_PERMISSION_TO_ACCESS_THIS),
            @ApiResponse(code = 404, message = DOG_WITH_GIVEN_ID_NOT_FOUND)
    })
	public ResponseEntity<DogResponseDTO> delete(Long id);
}
