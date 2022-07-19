package br.com.gft.dto.veterinarian;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class VeterinarianRequestDTO {
	
	private String name;
	private String crmv;
	private String phone;

}
