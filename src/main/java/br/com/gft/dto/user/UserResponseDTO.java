package br.com.gft.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data 
public class UserResponseDTO {

	private Long id;
	private String email;
	private String Profile;
}
