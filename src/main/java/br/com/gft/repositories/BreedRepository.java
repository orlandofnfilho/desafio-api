package br.com.gft.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.gft.entities.Breed;

@Repository
public interface BreedRepository extends JpaRepository<Breed, Long>{

	Optional<Breed> findByNameIgnoreCase(String name);
	
	Page<Breed> findAll(Pageable pageable);
	
}
