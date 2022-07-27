package br.com.gft.dto.appointment;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AppointmentRequestDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotBlank(message = "Id do veterinário inválido")
	@ApiModelProperty(value = "Veterinarian Id", position = 1)
	private Long veterinarianId;
	
	@NotBlank(message = "Id do cachorro inválido")
	@ApiModelProperty(value = "Dog Id", position = 2)
	private Long dogId;
	
	@ApiModelProperty(value = "Dog actual age", position = 3)
	private Integer dogActualAge;
	
	@NotNull(message = "Peso atual inválido")
	@DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer=3, fraction=2)
	@ApiModelProperty(value = "Dog actual", position = 4)
	private BigDecimal dogActualWeight;
	
	@ApiModelProperty(value = "Appointment diagnostic", position = 5)
	private String diagnostic;
	
	@ApiModelProperty(value = "Appointment comments", position = 6)
	private String comments;

}
