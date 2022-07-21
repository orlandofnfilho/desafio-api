package br.com.gft.dto.client;

import java.util.stream.Collectors;

import br.com.gft.dto.dog.DogMapper;
import br.com.gft.entities.Client;

public class ClientMapper {

	public static Client fromDTO(ClientRequestDTO dto) {
		return new Client(null, dto.getName(), dto.getCpf(), dto.getPhone(), null);

	}

	public static ClientResponseDTO fromEntity(Client obj) {
		return new ClientResponseDTO(obj.getId(), obj.getName(), obj.getCpf(), obj.getPhone(),
				obj.getPets().stream().map(DogMapper::fromEntity).collect(Collectors.toList()));
	}

}
