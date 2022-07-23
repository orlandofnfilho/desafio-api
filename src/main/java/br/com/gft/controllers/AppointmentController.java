package br.com.gft.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.gft.dto.appointment.AppointmentMapper;
import br.com.gft.dto.appointment.AppointmentRequestDTO;
import br.com.gft.dto.appointment.AppointmentResponseDTO;
import br.com.gft.entities.Appointment;
import br.com.gft.services.AppointmentService;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/appointments")
public class AppointmentController {

	private final AppointmentService appointmentService;

	@PostMapping
	public ResponseEntity<AppointmentResponseDTO> create(@RequestBody AppointmentRequestDTO dto) {
		Appointment appointment = appointmentService.create(AppointmentMapper.fromDTO(dto));
		AppointmentResponseDTO response = AppointmentMapper.fromEntity(appointment);
		return ResponseEntity.ok().body(response);

	}

	@GetMapping
	public ResponseEntity<Page<AppointmentResponseDTO>> findAll(@PageableDefault(size = 10) Pageable pageable) {
		Page<AppointmentResponseDTO> response = appointmentService.findAll(pageable).map(AppointmentMapper::fromEntity);
		return ResponseEntity.ok().body(response);
	}

	@GetMapping("/search")
	public ResponseEntity<List<AppointmentResponseDTO>> findByDog(@RequestParam String dogRegCod) {
		List<AppointmentResponseDTO> response = appointmentService.findByDog(dogRegCod).stream()
				.map(AppointmentMapper::fromEntity).collect(Collectors.toList());
		return ResponseEntity.ok().body(response);
	}

}
