package br.com.gft.controllers.docs;

import org.springframework.http.ResponseEntity;

import br.com.gft.dto.auth.AuthDTO;
import br.com.gft.dto.token.TokenDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api("Manages auth")
public interface AuthControllerDoc {

	
	public static final String AUTHENTICATION_FAILED_INVALID_CREDENTIALS = "Authentication failed or invalid credentials";
	public static final String AUTHENTICATION_SUCCESS = "Authentication success";
	public static final String MISSING_REQUIRED_FIELDS_OR_WRONG_FIELD_RANGE_VALUE = "Missing required fields or wrong field range value.";

	@ApiOperation(value = "Returns a JWT")
    @ApiResponses(value = {
    		@ApiResponse(code = 200, message = AUTHENTICATION_SUCCESS),
    		@ApiResponse(code = 400, message = MISSING_REQUIRED_FIELDS_OR_WRONG_FIELD_RANGE_VALUE),
    		@ApiResponse(code = 401, message = AUTHENTICATION_FAILED_INVALID_CREDENTIALS)
    })
	public ResponseEntity<TokenDTO> authenticate(AuthDTO authDTO);
}
