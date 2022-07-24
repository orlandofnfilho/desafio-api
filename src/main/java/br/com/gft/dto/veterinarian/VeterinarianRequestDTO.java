package br.com.gft.dto.veterinarian;

import javax.validation.constraints.NotBlank;

import br.com.gft.validation.CRMV;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class VeterinarianRequestDTO {

	@NotBlank(message = "Nome do veterinário inválido")
	private String name;

	@CRMV
	private String crmv;

	@NotBlank(message = "Telefone do Veterinário inválido")
	private String phone;

}
