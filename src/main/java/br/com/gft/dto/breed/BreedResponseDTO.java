package br.com.gft.dto.breed;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BreedResponseDTO {
	
	private Long id;
	private String name;
	private String life_span;
	private String temperament;
	private String origin;

}
