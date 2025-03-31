package repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import domain.Campus;

public interface CampusRepository extends CrudRepository<Campus, Integer> {
	// TODO
	Optional<Campus> findByCampusNaam(String campusNaam);
}
