package br.com.gft.dto.dog;

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import br.com.gft.dto.breed.BreedResponseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DogResponseDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "Dog's id", position = 1)
	private Long id;
	
	@ApiModelProperty(value = "Dog's name", position = 2)
	private String name;
	
	@ApiModelProperty(value = "Dog's regCod", position = 3)
	private String regCod;
	
	@JsonFormat(pattern="yyyy-MM-dd", shape = Shape.STRING)
	@ApiModelProperty(value = "Dog's birthdate", position = 4)
	private LocalDate birthdate;
	
	@ApiModelProperty(value = "Dog's tutor id", position = 5)
	private Long tutorId;
	
	@ApiModelProperty(value = "Dog's breed", position = 6)
	private BreedResponseDTO breed;
	
}
