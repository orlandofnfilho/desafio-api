package br.com.gft.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "tb_appointment")
public class Appointment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSSZZZZ", shape = JsonFormat.Shape.STRING, timezone = "UTC")
	private ZonedDateTime appointmentTime;

	@ManyToOne(fetch = FetchType.LAZY)
	private Veterinarian veterinarian;

	@ManyToOne(fetch = FetchType.LAZY)
	private Dog dog;
	
	private String tutor;
	
	private String tutorCpf;
	
	private Integer actualAge;

	private BigDecimal actualWeight;

	private String diagnostic;

	private String comments;

}
