package br.com.gft.dto.appointment;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.gft.dto.dog.DogResponseDTO;
import br.com.gft.dto.veterinarian.VeterinarianResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AppointmentResponseDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSZ", shape = JsonFormat.Shape.STRING, timezone = "UTC")
	private ZonedDateTime appointmentTime;

	private String tutor;
	
	private String tutorCpf;
	
	private VeterinarianResponseDTO veterinarian;
	
	private DogResponseDTO dog;
	
	private Integer dogActualAge;
	
	private BigDecimal dogActualWeight;
	
	private String diagnostic;
	
	private String comments;

}
