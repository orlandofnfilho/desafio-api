package br.com.gft.dto.dog;

import java.io.Serializable;

import br.com.gft.dto.breed.BreedRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DogRequestDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String name;
	private Long guardianId;
	private BreedRequestDTO breed;
	
}
