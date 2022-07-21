package br.com.gft.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.gft.entities.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long>{

	  Optional<Client> findByCpf(String cpf);
	  
	  Page<Client> findAll(Pageable pageable);
}
