package br.com.gft.dto.appointment;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AppointmentRequestDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long veterinarianId;
	private Long dogId;
	private Integer dogActualAge;
	private BigDecimal dogActualWeight;
	private String diagnostic;
	private String comments;

}
