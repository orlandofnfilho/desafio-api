package br.com.gft.dto.breed;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BreedResponseDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "Breed Id", position = 1)
	private Long id;
	
	@ApiModelProperty(value = "Breed's name", position = 2)
	private String name;
	
	@ApiModelProperty(value = "Breed's life span", position = 3)
	private String life_span;
	
	@ApiModelProperty(value = "Breed's temperament", position = 4)
	private String temperament;
	
	@ApiModelProperty(value = "Breed's origin", position = 5)
	private String origin;

}
