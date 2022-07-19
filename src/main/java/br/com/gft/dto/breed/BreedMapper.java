package br.com.gft.dto.breed;

import br.com.gft.entities.Breed;

public class BreedMapper {

	public static Breed fromDTO(BreedRequestDTO dto) {
		return new Breed(null, dto.getName(), dto.getLife_span(), dto.getTemperament(), dto.getOrigin());
	}

	public static BreedResponseDTO fromEntity(Breed obj) {
		return new BreedResponseDTO(obj.getId(), obj.getName(), obj.getLife_span(), obj.getTemperament(), obj.getOrigin());
	}

}
