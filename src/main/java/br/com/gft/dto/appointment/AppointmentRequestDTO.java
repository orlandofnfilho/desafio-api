package br.com.gft.dto.appointment;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AppointmentRequestDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotBlank(message = "Id do veterinário inválido")
	private Long veterinarianId;
	
	@NotBlank(message = "Id do cachorro inválido")
	private Long dogId;
	
	private Integer dogActualAge;
	
	@NotNull(message = "Peso atual inválido")
	@DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer=3, fraction=2)
	private BigDecimal dogActualWeight;
	
	private String diagnostic;
	
	private String comments;

}
