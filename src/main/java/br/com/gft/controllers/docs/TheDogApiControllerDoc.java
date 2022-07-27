package br.com.gft.controllers.docs;

import java.util.List;

import org.springframework.http.ResponseEntity;

import br.com.gft.dto.breed.BreedResponseDTO;
import br.com.gft.dto.vote.VoteDeleteResponseDTO;
import br.com.gft.dto.vote.VoteRequestDTO;
import br.com.gft.dto.vote.VoteResponseDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api("Manages clients")
public interface TheDogApiControllerDoc {
	
	public static final String THE_VOTE_WAS_SUCCESSFULLY_CREATED = "The Vote was successfully created";
	public static final String LIST_ALL_BREEDS_SAVED_ON_THE_DOG_API = "List all Breeds saved on TheDogApi";
	public static final String FORBIDDEN_DON_T_HAVE_PERMISSION_TO_ACCESS_THIS = "Forbidden. Don't have permission to access this";
	public static final String MISSING_REQUIRED_FIELDS_OR_WRONG_FIELD_RANGE_VALUE = "Missing required fields or wrong field range value.";
	
	@ApiOperation(value = "Returns a list of all Breeds saved")
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = LIST_ALL_BREEDS_SAVED_ON_THE_DOG_API),
    		@ApiResponse(code = 403, message = FORBIDDEN_DON_T_HAVE_PERMISSION_TO_ACCESS_THIS),
    })
	public ResponseEntity<List<BreedResponseDTO>> findAll();
	
	
	@ApiOperation(value = "Create a new Vote")
	@ApiResponses(value = {
	            @ApiResponse(code = 200, message = THE_VOTE_WAS_SUCCESSFULLY_CREATED),
	            @ApiResponse(code = 400, message = MISSING_REQUIRED_FIELDS_OR_WRONG_FIELD_RANGE_VALUE),
	            @ApiResponse(code = 403, message = FORBIDDEN_DON_T_HAVE_PERMISSION_TO_ACCESS_THIS)
	})
	public ResponseEntity<VoteResponseDTO> createVote(VoteRequestDTO obj);

	
	@ApiOperation(value = "Delete a Vote by vote_id")
	@ApiResponses(value = {
	            @ApiResponse(code = 200, message = THE_VOTE_WAS_SUCCESSFULLY_CREATED),
	            @ApiResponse(code = 400, message = MISSING_REQUIRED_FIELDS_OR_WRONG_FIELD_RANGE_VALUE),
	            @ApiResponse(code = 403, message = FORBIDDEN_DON_T_HAVE_PERMISSION_TO_ACCESS_THIS)
	})
	public ResponseEntity<VoteDeleteResponseDTO> deleteVote(String vote_id);
}
