package br.com.gft.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.gft.entities.Client;
import br.com.gft.exceptions.BusinessRuleException;
import br.com.gft.exceptions.ResourceNotFoundException;
import br.com.gft.repositories.ClientRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class ClientService {

	private final ClientRepository clientRepository;

	public Client create(Client obj) {
		obj.setPets(new ArrayList<>());
		checkCpf(obj);
		return clientRepository.save(obj);
	}

	@Transactional(readOnly = true)
	public Client findById(Long id) {
		Optional<Client> obj = clientRepository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado id: " + id));
	}

	@Transactional(readOnly = true)
	public Page<Client> findAll(Pageable pageable) {
		return clientRepository.findAll(pageable);
	}

	@Transactional(readOnly = true)
	public Optional<Client> findByCpf(Client obj) {
		return clientRepository.findByCpf(obj.getCpf());
	}

	public Client update(Long id, Client obj) {
		Client clientSaved = this.findById(id);
		obj.setId(clientSaved.getId());
		obj.setPets(clientSaved.getPets());
		validUpdate(obj);
		return clientRepository.save(obj);
	}

	public void delete(Long id) {
		Client clientSaved = this.findById(id);
		clientRepository.delete(clientSaved);
	}

	@Transactional(readOnly = true)
	public void checkCpf(Client obj) {
		Optional<Client> optional = findByCpf(obj);
		if (optional.isPresent()) {
			throw new BusinessRuleException("Cliente já cadastrado: " + obj.getCpf());
		}
	}

	@Transactional(readOnly = true)
	public void validUpdate(Client obj) {
		Optional<Client> clientSaved = findByCpf(obj);
		if (clientSaved.isPresent() && obj.getId() != clientSaved.get().getId()) {
			throw new BusinessRuleException("Cliente já cadastrado: " + obj.getCpf());
		}
	}

}
