package br.com.gft.services;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.gft.entities.Appointment;
import br.com.gft.entities.Dog;
import br.com.gft.entities.Veterinarian;
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
	public List<Appointment> findByDog(String regCod) {
		return appointmentRepository.findByDog_RegCodIgnoreCase(regCod);
	}

}
