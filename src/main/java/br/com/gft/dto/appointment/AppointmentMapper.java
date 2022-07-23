package br.com.gft.dto.appointment;

import br.com.gft.dto.dog.DogMapper;
import br.com.gft.dto.veterinarian.VeterinarianMapper;
import br.com.gft.entities.Appointment;
import br.com.gft.entities.Dog;
import br.com.gft.entities.Veterinarian;

public class AppointmentMapper {

	public static Appointment fromDTO(AppointmentRequestDTO dto) {
		Veterinarian vet = new Veterinarian();
		vet.setId(dto.getVeterinarianId());
		Dog dog = new Dog();
		dog.setId(dto.getDogId());
		return new Appointment(null, null, null, null,vet, dog, dto.getDogActualAge(), dto.getDogActualWeight(),
				dto.getDiagnostic(), dto.getComments());
	}

	public static AppointmentResponseDTO fromEntity(Appointment obj) {
		return new AppointmentResponseDTO(obj.getId(), obj.getAppointmentTime(), obj.getDog().getTutor().getName(), obj.getDog().getTutor().getCpf(),
				VeterinarianMapper.fromEntity(obj.getVeterinarian()), DogMapper.fromEntity(obj.getDog()),
				obj.getActualAge(), obj.getActualWeight(), obj.getDiagnostic(), obj.getComments());
	}
}
