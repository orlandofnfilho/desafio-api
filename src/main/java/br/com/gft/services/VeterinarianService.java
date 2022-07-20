package br.com.gft.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.gft.entities.Veterinarian;
import br.com.gft.exceptions.BusinessRuleException;
import br.com.gft.exceptions.ResourceNotFoundException;
import br.com.gft.repositories.VeterinarianRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class VeterinarianService {

	private final VeterinarianRepository veterinarianRepository;

	public Veterinarian create(Veterinarian obj) {
		checkCrmv(obj);
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
		validUpdate(obj);
		return veterinarianRepository.save(obj);
	}

	public void checkCrmv(Veterinarian obj) {
		Optional<Veterinarian> optional = findByCrmv(obj.getCrmv());
		if(optional.isPresent()) {
			throw new BusinessRuleException("Veterináriio já cadastrado CRMV: "+obj.getCrmv());
		}
	}
	
	public void delete(Long id) {
		Veterinarian vetSaved = this.findById(id);
		veterinarianRepository.delete(vetSaved);
	}

	public Optional<Veterinarian> findByCrmv(String crmv) {
		return veterinarianRepository.findByCrmv(crmv);
	}
	
	public void validUpdate(Veterinarian obj) {
		Optional<Veterinarian> vetSaved = findByCrmv(obj.getCrmv());
		if(vetSaved.isPresent() && obj.getId() != vetSaved.get().getId()) {
			throw new BusinessRuleException("Veterináriio já cadastrado CRMV: "+obj.getCrmv());
		}
	}
}
