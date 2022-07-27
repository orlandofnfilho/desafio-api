package br.com.gft.dto.client;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.br.CPF;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ClientRequestDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotBlank(message = "Nome do cliente inválido")
	@ApiModelProperty(value = "Client's name", position = 1)
	private String name;

	@CPF(message = "CPF inválido")
	@ApiModelProperty(value = "Client's CPF", position = 2)
	private String cpf;

	@NotBlank(message = "Telefone do cliente inválido")
	@ApiModelProperty(value = "Client's phone", position = 3)
	private String phone;

}
