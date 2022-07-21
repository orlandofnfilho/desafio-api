package br.com.gft.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.gft.entities.Veterinarian;
import br.com.gft.exceptions.BusinessRuleException;
import br.com.gft.exceptions.ResourceNotFoundException;
import br.com.gft.repositories.VeterinarianRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class VeterinarianService {

	private final VeterinarianRepository veterinarianRepository;

	public Veterinarian create(Veterinarian obj) {
		checkCrmv(obj);
		return veterinarianRepository.save(obj);
	}

	@Transactional(readOnly = true)
	public Veterinarian findById(Long id) {
		Optional<Veterinarian> obj = veterinarianRepository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException("Veterinário não encontrado id: " + id));
	}

	@Transactional(readOnly = true)
	public Page<Veterinarian> findAll(Pageable pageable) {
		return veterinarianRepository.findAll(pageable);
	}

	public Veterinarian update(Long id, Veterinarian obj) {
		Veterinarian vetSaved = this.findById(id);
		obj.setId(vetSaved.getId());
		validUpdate(obj);
		return veterinarianRepository.save(obj);
	}

	@Transactional(readOnly = true)
	public void checkCrmv(Veterinarian obj) {
		Optional<Veterinarian> optional = findByCrmv(obj);
		if (optional.isPresent()) {
			throw new BusinessRuleException("Veterináriio já cadastrado CRMV: " + obj.getCrmv());
		}
	}

	public void delete(Long id) {
		Veterinarian vetSaved = this.findById(id);
		veterinarianRepository.delete(vetSaved);
	}

	@Transactional(readOnly = true)
	public Optional<Veterinarian> findByCrmv(Veterinarian obj) {
		return veterinarianRepository.findByCrmv(obj.getCrmv());
	}

	@Transactional(readOnly = true)
	public void validUpdate(Veterinarian obj) {
		Optional<Veterinarian> vetSaved = findByCrmv(obj);
		if (vetSaved.isPresent() && obj.getId() != vetSaved.get().getId()) {
			throw new BusinessRuleException("Veterináriio já cadastrado CRMV: " + obj.getCrmv());
		}
	}
}
