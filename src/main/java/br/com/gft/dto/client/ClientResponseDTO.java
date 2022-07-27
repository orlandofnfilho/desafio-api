package br.com.gft.dto.client;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.gft.dto.dog.DogResponseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ClientResponseDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "Client Id", position = 1)
	private Long id;
	
	@ApiModelProperty(value = "Client's name", position = 2)
	private String name;
	
	@ApiModelProperty(value = "Client's CPF", position = 3)
	private String cpf;
	
	@ApiModelProperty(value = "Client's phone", position = 4)
	private String phone;
	
	@ApiModelProperty(value = "Client's pets list", position = 5)
	private List<DogResponseDTO> pets = new ArrayList<>();

}
