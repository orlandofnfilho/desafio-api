package br.com.gft.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "tb_dog")
public class Dog implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	private String regCod;

	@JsonFormat(pattern = "yyyy-MM-dd", shape = Shape.STRING)
	private LocalDate birthdate;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Breed breed;

	@ManyToOne(fetch = FetchType.LAZY)
	private Client tutor;

	@JsonIgnore
	@OneToMany(mappedBy = "dog", fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	private List<Appointment> appointments;

}
