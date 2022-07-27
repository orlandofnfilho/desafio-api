package br.com.gft.dto.breed;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BreedRequestDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotBlank(message = "Nome da raça inválido")
	@ApiModelProperty(value = "Breed's name", position = 1)
	private String name;

	@ApiModelProperty(value = "Breed's life span", position = 2)
	private String life_span;

	@ApiModelProperty(value = "Breed's temperament", position = 3)
	private String temperament;

	@ApiModelProperty(value = "Breed's origin", position = 4)
	private String origin;

}
