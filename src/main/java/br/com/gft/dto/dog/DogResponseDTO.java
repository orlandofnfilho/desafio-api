package br.com.gft.dto.dog;

import java.io.Serializable;

import br.com.gft.dto.breed.BreedResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DogResponseDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String name;
	private String reg_cod;
	private Long guardianId;
	private BreedResponseDTO breed;
	
}
