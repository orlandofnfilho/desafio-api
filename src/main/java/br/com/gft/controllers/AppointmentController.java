package br.com.gft.controllers;

import java.net.URI;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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

	private static final String ID = "/{id}";
	private final AppointmentService appointmentService;

	@PostMapping
	public ResponseEntity<AppointmentResponseDTO> create(@RequestBody AppointmentRequestDTO dto) {
		Appointment appointment = appointmentService.create(AppointmentMapper.fromDTO(dto));
		AppointmentResponseDTO response = AppointmentMapper.fromEntity(appointment);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path(ID).buildAndExpand(response.getId()).toUri();
		return ResponseEntity.created(uri).body(response);

	}
	
	@GetMapping(ID)
	public ResponseEntity<AppointmentResponseDTO> findById(@PathVariable Long id){
		Appointment appointment = appointmentService.findById(id);
		AppointmentResponseDTO response = AppointmentMapper.fromEntity(appointment);
		return ResponseEntity.ok().body(response);
	}

	@GetMapping
	public ResponseEntity<Page<AppointmentResponseDTO>> findAll(@PageableDefault(size = 10) Pageable pageable) {
		Page<AppointmentResponseDTO> response = appointmentService.findAll(pageable).map(AppointmentMapper::fromEntity);
		return ResponseEntity.ok().body(response);
	}

	@GetMapping("/dog")
	public ResponseEntity<Page<AppointmentResponseDTO>> findByDog(@PageableDefault(size = 10) Pageable pageable,
			@RequestParam String dogRegCod) {
		Page<AppointmentResponseDTO> response = appointmentService.findByDog(pageable, dogRegCod)
				.map(AppointmentMapper::fromEntity);
		return ResponseEntity.ok().body(response);
	}

	@GetMapping("/vet")
	public ResponseEntity<Page<AppointmentResponseDTO>> findByVet(@PageableDefault(size = 10) Pageable pageable,
			@RequestParam String crmv) {
		Page<AppointmentResponseDTO> response = appointmentService.findByVet(pageable, crmv)
				.map(AppointmentMapper::fromEntity);
		return ResponseEntity.ok().body(response);
	}
	
	@PutMapping(ID)
	public ResponseEntity<AppointmentResponseDTO> update(@PathVariable Long id, @RequestBody AppointmentRequestDTO obj) {
		Appointment appointment = appointmentService.update(id, AppointmentMapper.fromDTO(obj));
		AppointmentResponseDTO response = AppointmentMapper.fromEntity(appointment);
		return ResponseEntity.ok().body(response);
	}
	
	@DeleteMapping(ID)
	public ResponseEntity<AppointmentResponseDTO> delete(@PathVariable Long id){
		appointmentService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
