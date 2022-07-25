package br.com.gft.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.gft.entities.Profile;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long>{

}
