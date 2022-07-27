package br.com.gft.dto.appointment;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.gft.dto.dog.DogResponseDTO;
import br.com.gft.dto.veterinarian.VeterinarianResponseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AppointmentResponseDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "Appointment Id", position = 1)
	private Long id;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSZ", shape = JsonFormat.Shape.STRING, timezone = "UTC")
	@ApiModelProperty(value = "Appointment datetime", position = 2)
	private ZonedDateTime appointmentTime;

	@ApiModelProperty(value = "Dog's tutor name", position = 3)
	private String tutor;
	
	@ApiModelProperty(value = "Dog's tutor CPF", position = 4)
	private String tutorCpf;
	
	@ApiModelProperty(value = "Appointment's vet", position = 5)
	private VeterinarianResponseDTO veterinarian;
	
	@ApiModelProperty(value = "Appointment's dog", position = 6)
	private DogResponseDTO dog;
	
	@ApiModelProperty(value = "Dog's actual age", position = 7)
	private Integer dogActualAge;
	
	@ApiModelProperty(value = "Dog's actual weight", position = 8)
	private BigDecimal dogActualWeight;
	
	@ApiModelProperty(value = "Appointment's diagnostic", position = 9)
	private String diagnostic;
	
	@ApiModelProperty(value = "Appointment's comments", position = 10)
	private String comments;

}
