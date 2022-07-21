package br.com.gft.dto.client;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.gft.dto.dog.DogResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ClientResponseDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String name;
	private String cpf;
	private String phone;
	
	private List<DogResponseDTO> pets = new ArrayList<>();

}
