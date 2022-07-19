package br.com.gft.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.gft.entities.Veterinarian;
import br.com.gft.exceptions.DataIntegrityViolationException;
import br.com.gft.exceptions.ResourceNotFoundException;
import br.com.gft.repositories.VeterinarianRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class VeterinarianService {

	private final VeterinarianRepository veterinarianRepository;

	public Veterinarian create(Veterinarian obj) {
		Optional<Veterinarian> vetSaved = this.findByCrmv(obj.getCrmv());
		return veterinarianRepository.save(obj);
	}

	public Veterinarian findById(Long id) {
		Optional<Veterinarian> obj = veterinarianRepository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException("Veterinário não encontrado id: " + id));
	}

	public List<Veterinarian> findAll() {
		return veterinarianRepository.findAll();
	}
	
	public Veterinarian update(Long id, Veterinarian obj) {
		Veterinarian vetSaved = this.findById(id);
		obj.setId(vetSaved.getId());
		this.findByCrmv(obj.getCrmv());
		return veterinarianRepository.save(obj);
	}
	
	public void delete(Long id) {
		Veterinarian vetSaved = this.findById(id);
		veterinarianRepository.delete(vetSaved);
	}

	public Optional<Veterinarian> findByCrmv(String crmv) {
		Optional<Veterinarian> vetSaved = veterinarianRepository.findByCrmv(crmv);
		if (vetSaved.isPresent()) {
			throw new DataIntegrityViolationException("Veterinário já cadastrado CRMV: " + crmv);
		}
		return vetSaved;
	}

}
