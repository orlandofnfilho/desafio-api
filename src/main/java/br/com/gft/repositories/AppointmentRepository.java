package br.com.gft.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.gft.entities.Appointment;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long>{
	
	Page<Appointment> findAll(Pageable pageable);
	
	List<Appointment> findByDog_RegCodIgnoreCase(String regCod);
	
}
