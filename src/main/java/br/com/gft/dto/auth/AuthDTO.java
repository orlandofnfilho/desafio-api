package br.com.gft.dto.auth;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthDTO {

	@ApiModelProperty(value = "User email", position = 1)
	private String email;
	
	@ApiModelProperty(value = "User password", position = 2)
	private String password;

}
