package br.com.gft.dto.breed;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BreedRequestDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String name;
	private String life_span;
	private String temperament;
	private String origin;

}
