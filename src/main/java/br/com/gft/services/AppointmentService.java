package br.com.gft.services;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.gft.entities.Appointment;
import br.com.gft.entities.Dog;
import br.com.gft.entities.Veterinarian;
import br.com.gft.exceptions.ResourceNotFoundException;
import br.com.gft.repositories.AppointmentRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class AppointmentService {

	private final AppointmentRepository appointmentRepository;
	private final DogService dogService;
	private final ClientService clientService;
	private final VeterinarianService veterinarianService;
	private final BreedService breedService;

	public Appointment create(Appointment obj) {
		obj.setAppointmentTime(ZonedDateTime.now(ZoneId.of("UTC")));
		Dog dogSaved = dogService.findById(obj.getDog().getId());
		Veterinarian veterinarianSaved = veterinarianService.findById(obj.getVeterinarian().getId());
		obj.setDog(dogSaved);
		obj.setVeterinarian(veterinarianSaved);
		obj.setTutor(dogSaved.getTutor().getName());
		obj.setTutorCpf(dogSaved.getTutor().getCpf());
		return appointmentRepository.save(obj);
	}

	@Transactional(readOnly = true)
	public Page<Appointment> findByDog(Pageable pageable, String regCod) {
		return appointmentRepository.findByDog_RegCodIgnoreCase(pageable, regCod);
	}
	@Transactional(readOnly = true)
	public Page<Appointment> findByVet(Pageable pageable, String crmv) {
		return appointmentRepository.findByVeterinarian_crmvIgnoreCase(pageable, crmv);
	}

	@Transactional(readOnly = true)
	public Page<Appointment> findAll(Pageable pageable) {
		return appointmentRepository.findAll(pageable);
	}

	@Transactional(readOnly = true)
	public Appointment findById(Long id) {
		Optional<Appointment> obj = appointmentRepository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException("Atendimento não encontrado id: " + id));
	}
	
	public Appointment update(Long id, Appointment obj) {
		Appointment appointmentSaved = findById(id);
		Dog dogSaved = dogService.findById(obj.getDog().getId());
		Veterinarian vetSaved = veterinarianService.findById(obj.getVeterinarian().getId());
		obj.setId(appointmentSaved.getId());
		obj.setAppointmentTime(appointmentSaved.getAppointmentTime());
		obj.setDog(dogSaved);
		obj.setVeterinarian(vetSaved);
		obj.setTutor(dogSaved.getTutor().getName());
		obj.setTutorCpf(dogSaved.getTutor().getCpf());
		return appointmentRepository.save(obj);
		
	}
	
	public void delete(Long id) {
		Appointment appointmentSaved = this.findById(id);
		appointmentRepository.delete(appointmentSaved);
	}

}
