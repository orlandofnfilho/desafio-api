package br.com.gft.dto.vote;

import java.io.Serializable;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data 
public class VoteRequestDTO implements Serializable{
	private static final long serialVersionUID = 1L;

	@NotBlank(message = "Id da imagem inválido")
	private String image_id;
	
	@NotBlank(message = "Id do sub inválido")
	private String sub_id;
	
	@Min(0)
	@Max(1)
	@NotNull(message = "Value inválido, 1 para upVote e 0 para downVote")
	private Integer value;

}
