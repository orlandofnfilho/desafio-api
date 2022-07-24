package br.com.gft.dto.breed;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BreedRequestDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotBlank(message = "Nome da raça inválido")
	private String name;

	private String life_span;

	private String temperament;

	private String origin;

}
