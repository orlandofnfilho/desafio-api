package br.com.gft.dto.dog;

import br.com.gft.dto.breed.BreedMapper;
import br.com.gft.entities.Client;
import br.com.gft.entities.Dog;

public class DogMapper {

	public static Dog fromDTO(DogRequestDTO dto) {
		Client client = new Client();
		client.setId(dto.getGuardianId());
		return new Dog(null, dto.getName(), null, dto.getBirthdate(), BreedMapper.fromDTO(dto.getBreed()), client);

	}

	public static DogResponseDTO fromEntity(Dog obj) {
		return new DogResponseDTO(obj.getId(), obj.getName(), obj.getReg_cod(), obj.getBirthdate(),
				obj.getGuardian().getId(), BreedMapper.fromEntity(obj.getBreed()));
	}

}
