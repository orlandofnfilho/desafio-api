package br.com.gft.dto.dog;

import java.io.Serializable;
import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import br.com.gft.dto.breed.BreedRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DogRequestDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@NotBlank(message = "Nome inválido")
	private String name;
	
	@NotNull(message = "Id do tutor inválido")
	private Long tutorId;
	
	@JsonFormat(pattern="yyyy-MM-dd", shape = Shape.STRING)
	private LocalDate birthdate;
	
	@NotNull(message = "Raça inválida")
	private BreedRequestDTO breed;
	
}
