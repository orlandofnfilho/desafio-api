package br.com.gft.config;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import br.com.gft.entities.Appointment;
import br.com.gft.entities.Breed;
import br.com.gft.entities.Client;
import br.com.gft.entities.Dog;
import br.com.gft.entities.Profile;
import br.com.gft.entities.User;
import br.com.gft.entities.Veterinarian;
import br.com.gft.repositories.AppointmentRepository;
import br.com.gft.repositories.BreedRepository;
import br.com.gft.repositories.ClientRepository;
import br.com.gft.repositories.DogRepository;
import br.com.gft.repositories.ProfileRepository;
import br.com.gft.repositories.UserRepository;
import br.com.gft.repositories.VeterinarianRepository;

@Component
@Transactional
public class StartDb implements CommandLineRunner {

	@Autowired
	private ProfileRepository profileRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BreedRepository breedRepository;

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private DogRepository dogRepository;

	@Autowired
	private VeterinarianRepository veterinarianRepository;

	@Autowired
	private AppointmentRepository appointmentRepository;

	@Override
	public void run(String... args) throws Exception {

		Profile p1 = new Profile(null, "ADMIN");
		Profile p2 = new Profile(null, "USUARIO");
		List<Profile> listProfile = profileRepository.findAll();

		if (listProfile.isEmpty())
			profileRepository.saveAll(List.of(p1, p2));

		User u1 = new User(null, "admin@gft.com", new BCryptPasswordEncoder().encode("Gft@1234"), p1);
		User u2 = new User(null, "usuario@gft.com", new BCryptPasswordEncoder().encode("Gft@1234"), p2);
		List<User> listUser = userRepository.findAll();

		if (listUser.isEmpty())
			userRepository.saveAll(List.of(u1, u2));

		Veterinarian v1 = new Veterinarian(null, "Dr. John", "12345", "55999225566", new ArrayList<>());
		Veterinarian v2 = new Veterinarian(null, "Dra. Sylvia", "54321", "55999335577", new ArrayList<>());
		List<Veterinarian> listVeterinarian = veterinarianRepository.findAll();

		if (listVeterinarian.isEmpty())
			veterinarianRepository.saveAll(List.of(v1, v2));

		Client c1 = new Client(null, "Alex Green", "49828903075", "55999112233", new ArrayList<>());
		Client c2 = new Client(null, "Maria Brown", "76720387022", "55999114433", new ArrayList<>());
		List<Client> listClient = clientRepository.findAll();

		if (listClient.isEmpty())
			clientRepository.saveAll(List.of(c1, c2));

		Breed b1 = new Breed(null, "Alaskan Husky", "10 - 13 years", "Friendly, Energetic, Loyal, Gentle, Confident",
				"Unknown");
		Breed b2 = new Breed(null, "Poodle", "14 - 18 years",
				"Alert, Intelligent, Faithful, Active, Instinctual, Trainable", "Unknown");
		List<Breed> listBreed = breedRepository.findAll();

		if (listBreed.isEmpty())
			breedRepository.saveAll(List.of(b1, b2));

		Dog d1 = new Dog(null, "Milo", "A12X3G", LocalDate.of(2021, 01, 30), b1, c1, new ArrayList<>());
		Dog d2 = new Dog(null, "Daisy", "M98BWN", LocalDate.of(2021, 04, 15), b2, c2, new ArrayList<>());
		List<Dog> listDog = dogRepository.findAll();

		if (listDog.isEmpty())
			dogRepository.saveAll(List.of(d1, d2));

		Appointment a1 = new Appointment(null, ZonedDateTime.now(ZoneId.of("UTC")), d1.getTutor().getName(),
				d1.getTutor().getCpf(), v1, d1, 1, BigDecimal.valueOf(2), "blablabla", "blablalba");
		Appointment a2 = new Appointment(null, ZonedDateTime.now(ZoneId.of("UTC")), d2.getTutor().getName(),
				d2.getTutor().getCpf(), v2, d2, 1, BigDecimal.valueOf(1.0), "blablabla", "blablalba");
		List<Appointment> listAppointment = appointmentRepository.findAll();

		if (listAppointment.isEmpty())
			appointmentRepository.saveAll(List.of(a1, a2));

	}

}
