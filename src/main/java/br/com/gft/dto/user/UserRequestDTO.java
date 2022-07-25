package br.com.gft.dto.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserRequestDTO {

	@Email(message = "E-mail inválido")
	private String email;
	
	@NotBlank(message = "Senha inválida")
	private String password;

}
