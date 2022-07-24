package br.com.gft.dto.client;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.br.CPF;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ClientRequestDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotBlank(message = "Nome do cliente inválido")
	private String name;

	@CPF(message = "CPF inválido")
	private String cpf;

	@NotBlank(message = "Telefone do cliente inválido")
	private String phone;

}
