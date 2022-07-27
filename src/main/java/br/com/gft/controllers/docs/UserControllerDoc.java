package br.com.gft.controllers.docs;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import br.com.gft.dto.user.UserRequestDTO;
import br.com.gft.dto.user.UserResponseDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api("Manages users")
public interface UserControllerDoc {

	public static final String FAILED_TO_DELETE_RESOURCE_ASSOCIATED_WITH_ANOTHER = "Failed to delete, resource associated with another";
	public static final String USER_SUCCESSFULLY_DELETED = "User successfully deleted";
	public static final String USER_SUCESSFULLY_FOUND = "User sucessfully found";
	public static final String CONFLICT_USERS_IS_ALREADY_SAVED = "Conflict, the User is already saved.";
	public static final String NEW_USER_SUCCESSFULLY_CREATED = "New User successfully created";
	public static final String USER_WITH_GIVEN_ID_NOT_FOUND = "User with given id not found.";
	public static final String USER_SUCESSFULLY_UPDATED = "User successfully updated";
	public static final String PAGEABLE_LIST_OF_ALL_USERS_REGISTERED_IN_THE_SYSTEM = "Pageable list of all Users registered in the system";
	public static final String FORBIDDEN_DON_T_HAVE_PERMISSION_TO_ACCESS_THIS = "Forbidden. Don't have permission to access this";
	public static final String MISSING_REQUIRED_FIELDS_OR_WRONG_FIELD_RANGE_VALUE = "Missing required fields or wrong field range value.";
	
	
	@ApiOperation(value = "Create a new User")
	@ApiResponses(value = {
	            @ApiResponse(code = 201, message = NEW_USER_SUCCESSFULLY_CREATED),
	            @ApiResponse(code = 400, message = MISSING_REQUIRED_FIELDS_OR_WRONG_FIELD_RANGE_VALUE),
	            @ApiResponse(code = 403, message = FORBIDDEN_DON_T_HAVE_PERMISSION_TO_ACCESS_THIS),
	            @ApiResponse(code = 409, message = CONFLICT_USERS_IS_ALREADY_SAVED)
	})
	public ResponseEntity<UserResponseDTO> create(UserRequestDTO dto);
	
	
	@ApiOperation(value = "Find a User by Id")
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = USER_SUCESSFULLY_FOUND),
            @ApiResponse(code = 403, message = FORBIDDEN_DON_T_HAVE_PERMISSION_TO_ACCESS_THIS),
            @ApiResponse(code = 404, message = USER_WITH_GIVEN_ID_NOT_FOUND)
    })
	public ResponseEntity<UserResponseDTO> findById(Long id);
	
	@ApiOperation(value = "Returns a pageable list of all Users")
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = PAGEABLE_LIST_OF_ALL_USERS_REGISTERED_IN_THE_SYSTEM),
    		@ApiResponse(code = 403, message = FORBIDDEN_DON_T_HAVE_PERMISSION_TO_ACCESS_THIS),
    })
	public ResponseEntity<Page<UserResponseDTO>> findAll(Pageable pageable);


	@ApiOperation(value = "Update User by a given Id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = USER_SUCESSFULLY_UPDATED),
            @ApiResponse(code = 400, message = MISSING_REQUIRED_FIELDS_OR_WRONG_FIELD_RANGE_VALUE),
            @ApiResponse(code = 403, message = FORBIDDEN_DON_T_HAVE_PERMISSION_TO_ACCESS_THIS),
            @ApiResponse(code = 404, message = USER_WITH_GIVEN_ID_NOT_FOUND),
            @ApiResponse(code = 409, message = CONFLICT_USERS_IS_ALREADY_SAVED)
    })
	public ResponseEntity<UserResponseDTO> update(Long id, UserRequestDTO dto);
	
	
	@ApiOperation(value = "Update User by a given Id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = USER_SUCESSFULLY_UPDATED),
            @ApiResponse(code = 400, message = MISSING_REQUIRED_FIELDS_OR_WRONG_FIELD_RANGE_VALUE),
            @ApiResponse(code = 403, message = FORBIDDEN_DON_T_HAVE_PERMISSION_TO_ACCESS_THIS),
            @ApiResponse(code = 404, message = USER_WITH_GIVEN_ID_NOT_FOUND)
    })
	public ResponseEntity<UserResponseDTO> changeProfile(Long id,Long profileId);
	
	@ApiOperation(value = "Delete a User found by a given valid Id")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = USER_SUCCESSFULLY_DELETED),
            @ApiResponse(code = 400, message = FAILED_TO_DELETE_RESOURCE_ASSOCIATED_WITH_ANOTHER),
            @ApiResponse(code = 403, message = FORBIDDEN_DON_T_HAVE_PERMISSION_TO_ACCESS_THIS),
            @ApiResponse(code = 404, message = USER_WITH_GIVEN_ID_NOT_FOUND)
    })
	public ResponseEntity<UserResponseDTO> delete(Long id);
}
