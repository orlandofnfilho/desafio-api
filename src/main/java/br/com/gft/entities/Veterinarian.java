package br.com.gft.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_veterinarian")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Veterinarian implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "Não pode ser vázio")
	private String name;
	
	@NotBlank
	@Column(length = 3)
	private String crmv;
	
	@NotBlank
	private String phone;
	
}
