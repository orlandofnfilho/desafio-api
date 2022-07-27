package br.com.gft.controllers.docs;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import br.com.gft.dto.client.ClientRequestDTO;
import br.com.gft.dto.client.ClientResponseDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api("Manages clients")
public interface ClientControllerDoc {
	
	public static final String FAILED_TO_DELETE_RESOURCE_ASSOCIATED_WITH_ANOTHER = "Failed to delete, resource associated with another";
	public static final String CLIENT_SUCCESSFULLY_DELETED = "Client successfully deleted";
	public static final String CLIENT_SUCESSFULLY_FOUND = "Client sucessfully found";
	public static final String CONFLICT_CLIENT_IS_ALREADY_SAVED = "Conflict, the Client is already saved.";
	public static final String NEW_CLIENT_SUCCESSFULLY_CREATED = "New Client successfully created";
	public static final String CLIENT_WITH_GIVEN_ID_NOT_FOUND = "Client with given id not found.";
	public static final String CLIENT_SUCESSFULLY_UPDATED = "Client successfully updated";
	public static final String PAGEABLE_LIST_OF_ALL_CLIENTS_REGISTERED_IN_THE_SYSTEM = "Pageable list of all Clients registered in the system";
	public static final String FORBIDDEN_DON_T_HAVE_PERMISSION_TO_ACCESS_THIS = "Forbidden. Don't have permission to access this";
	public static final String MISSING_REQUIRED_FIELDS_OR_WRONG_FIELD_RANGE_VALUE = "Missing required fields or wrong field range value.";
	
	
	@ApiOperation(value = "Create a new Client")
	@ApiResponses(value = {
	            @ApiResponse(code = 201, message = NEW_CLIENT_SUCCESSFULLY_CREATED),
	            @ApiResponse(code = 400, message = MISSING_REQUIRED_FIELDS_OR_WRONG_FIELD_RANGE_VALUE),
	            @ApiResponse(code = 403, message = FORBIDDEN_DON_T_HAVE_PERMISSION_TO_ACCESS_THIS),
	            @ApiResponse(code = 409, message = CONFLICT_CLIENT_IS_ALREADY_SAVED)
	})
	public ResponseEntity<ClientResponseDTO> create(ClientRequestDTO obj);
	
	
	@ApiOperation(value = "Find a Client by Id")
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = CLIENT_SUCESSFULLY_FOUND),
            @ApiResponse(code = 403, message = FORBIDDEN_DON_T_HAVE_PERMISSION_TO_ACCESS_THIS),
            @ApiResponse(code = 404, message = CLIENT_WITH_GIVEN_ID_NOT_FOUND)
    })
	public ResponseEntity<ClientResponseDTO> findById(Long id);
	
	
	@ApiOperation(value = "Returns a pageable list of all Clients")
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = PAGEABLE_LIST_OF_ALL_CLIENTS_REGISTERED_IN_THE_SYSTEM),
    		@ApiResponse(code = 403, message = FORBIDDEN_DON_T_HAVE_PERMISSION_TO_ACCESS_THIS),
    })
	public ResponseEntity<Page<ClientResponseDTO>> findAll(Pageable pageable);

	
	@ApiOperation(value = "Update Client by a given Id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = CLIENT_SUCESSFULLY_UPDATED),
            @ApiResponse(code = 400, message = MISSING_REQUIRED_FIELDS_OR_WRONG_FIELD_RANGE_VALUE),
            @ApiResponse(code = 403, message = FORBIDDEN_DON_T_HAVE_PERMISSION_TO_ACCESS_THIS),
            @ApiResponse(code = 404, message = CLIENT_WITH_GIVEN_ID_NOT_FOUND),
            @ApiResponse(code = 409, message = CONFLICT_CLIENT_IS_ALREADY_SAVED)
    })
	public ResponseEntity<ClientResponseDTO> update(Long id,ClientRequestDTO obj);
	
	
	@ApiOperation(value = "Delete a Client found by a given valid Id")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = CLIENT_SUCCESSFULLY_DELETED),
            @ApiResponse(code = 400, message = FAILED_TO_DELETE_RESOURCE_ASSOCIATED_WITH_ANOTHER),
            @ApiResponse(code = 403, message = FORBIDDEN_DON_T_HAVE_PERMISSION_TO_ACCESS_THIS),
            @ApiResponse(code = 404, message = CLIENT_WITH_GIVEN_ID_NOT_FOUND)
    })
	public ResponseEntity<ClientResponseDTO> delete(Long id);
}
