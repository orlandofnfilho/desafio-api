package br.com.gft.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.gft.entities.Dog;

@Repository
public interface DogRepository extends JpaRepository<Dog, Long>{

	
}
