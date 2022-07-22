package br.com.gft.dto.dog;

import java.io.Serializable;
import java.time.LocalDate;

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
	
	private String name;
	
	private Long tutorId;
	
	@JsonFormat(pattern="yyyy-MM-dd", shape = Shape.STRING)
	private LocalDate birthdate;
	
	private BreedRequestDTO breed;
	
}
