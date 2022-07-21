package br.com.gft.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.gft.entities.Veterinarian;

@Repository
public interface VeterinarianRepository extends JpaRepository<Veterinarian, Long>{
	
  Optional<Veterinarian> findByCrmv(String crmv);
  
  Page<Veterinarian> findAll(Pageable pageable);

}
