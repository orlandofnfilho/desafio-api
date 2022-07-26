package br.com.gft.dto.vote;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class VoteDeleteResponseDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String message;

}
