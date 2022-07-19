package br.com.gft.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.gft.entities.Breed;

@Repository
public interface BreedRepository extends JpaRepository<Breed, Long>{

	Optional<Breed> findByNameIgnoreCase(String name);
	
}
