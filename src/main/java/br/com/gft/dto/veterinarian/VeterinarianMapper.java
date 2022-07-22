package br.com.gft.dto.veterinarian;

import br.com.gft.entities.Veterinarian;

public class VeterinarianMapper {

	public static Veterinarian fromDTO(VeterinarianRequestDTO dto) {
		return new Veterinarian(null, dto.getName(), dto.getCrmv() ,dto.getPhone(), null);
	}

	public static VeterinarianResponseDTO fromEntity(Veterinarian obj) {
		return new VeterinarianResponseDTO(obj.getId(), obj.getName(), obj.getCrmv(), obj.getPhone());
	}
}
