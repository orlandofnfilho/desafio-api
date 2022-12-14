package br.com.gft.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.gft.entities.Dog;

@Repository
public interface DogRepository extends JpaRepository<Dog, Long>{
	
	Page<Dog> findAll(Pageable pageable);
	
	Optional<Dog> findByRegCodIgnoreCase(String regCod);
}
