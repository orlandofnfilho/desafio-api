package br.com.gft.dto.dog;

import java.io.Serializable;
import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import br.com.gft.dto.breed.BreedRequestDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DogRequestDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@NotBlank(message = "Nome inválido")
	@ApiModelProperty(value = "Dog's name", position = 1)
	private String name;
	
	@NotNull(message = "Id do tutor inválido")
	@ApiModelProperty(value = "Dog's tutor Id", position = 2)
	private Long tutorId;
	
	@JsonFormat(pattern="yyyy-MM-dd", shape = Shape.STRING)
	@ApiModelProperty(value = "Dog's brithdate", position = 3)
	private LocalDate birthdate;
	
	@NotNull(message = "Raça inválida")
	@ApiModelProperty(value = "Dog's breed", position = 4)
	private BreedRequestDTO breed;
	
}
