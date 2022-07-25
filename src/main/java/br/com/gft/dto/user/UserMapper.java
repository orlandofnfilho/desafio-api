package br.com.gft.dto.user;

import br.com.gft.entities.User;

public class UserMapper {

	public static User fromDTO(UserRequestDTO dto) {
		return new User(null, dto.getEmail(), dto.getPassword(), null);
	}

	public static UserResponseDTO fromEntity(User obj) {
		return new UserResponseDTO(obj.getId(), obj.getEmail(), obj.getProfile().getRole());
	}

}
