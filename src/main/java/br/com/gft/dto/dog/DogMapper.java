package br.com.gft.dto.dog;

import br.com.gft.dto.breed.BreedMapper;
import br.com.gft.entities.Client;
import br.com.gft.entities.Dog;

public class DogMapper {

	public static Dog fromDTO(DogRequestDTO dto) {
		Client client = new Client();
		client.setId(dto.getTutorId());
		return new Dog(null, dto.getName(), null, dto.getBirthdate(),BreedMapper.fromDTO(dto.getBreed()), client, null);

	}

	public static DogResponseDTO fromEntity(Dog obj) {
		return new DogResponseDTO(obj.getId(), obj.getName(), obj.getRegCod(), obj.getBirthdate(),
				obj.getTutor().getId(), BreedMapper.fromEntity(obj.getBreed()));
	}

}
