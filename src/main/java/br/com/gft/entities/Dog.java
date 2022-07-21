package br.com.gft.entities;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "tb_dog")
public class Dog implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	private String reg_cod;
	
	@JsonFormat(pattern="dd/MM/yyyy", shape = Shape.STRING)
    private LocalDate birthdate;
	
	@OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private Breed breed;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Client guardian;
	
	
}
