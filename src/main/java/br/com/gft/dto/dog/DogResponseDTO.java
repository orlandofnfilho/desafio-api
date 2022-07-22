package br.com.gft.dto.dog;

import java.io.Serializable;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

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
	
	private String regCod;
	
	@JsonFormat(pattern="yyyy-MM-dd", shape = Shape.STRING)
	private LocalDate birthdate;
	
	private Long tutorId;
	
	private BreedResponseDTO breed;
	
}
