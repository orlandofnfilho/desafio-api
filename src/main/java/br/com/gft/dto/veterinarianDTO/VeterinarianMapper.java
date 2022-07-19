package br.com.gft.dto.veterinarianDTO;

import br.com.gft.entities.Veterinarian;

public class VeterinarianMapper {

	public static Veterinarian fromDTO(VeterinarianRequestDTO dto) {
		return new Veterinarian(null, dto.getName(), dto.getCrmv() ,dto.getPhone());
	}

	public static VeterinarianResponseDTO fromEntity(Veterinarian obj) {
		return new VeterinarianResponseDTO(obj.getId(), obj.getName(), obj.getCrmv(), obj.getPhone());
	}
}