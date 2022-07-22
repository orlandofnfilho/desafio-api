package br.com.gft.dto.appointment;

import br.com.gft.entities.Appointment;
import br.com.gft.entities.Dog;
import br.com.gft.entities.Veterinarian;

public class AppointmentMapper {

	public static Appointment fromDTO(AppointmentRequestDTO dto) {
		Veterinarian vet = new Veterinarian();
		vet.setId(dto.getVeterinarianId());
		Dog dog = new Dog();
		dog.setId(dto.getDogId());
		return new Appointment(null, null, vet, dog, dog.getTutor().getName(), dog.getTutor().getCpf(),
				dto.getDogActualAge(), dto.getDogActualWeight(), dto.getDiagnostic(), dto.getComments());
	}

	public static AppointmentResponseDTO fromEntity(Appointment obj) {
		return new AppointmentResponseDTO(obj.getId(), obj.getAppointmentTime(), obj.getVeterinarian().getName(),
				obj.getVeterinarian().getCrmv(), obj.getTutor(), obj.getTutorCpf(), obj.getDog(), obj.getActualAge(),
				obj.getActualWeight(), obj.getDiagnostic(), obj.getComments());
	}
}
