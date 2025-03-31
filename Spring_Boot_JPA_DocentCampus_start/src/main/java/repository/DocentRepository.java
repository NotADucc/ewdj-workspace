package repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import domain.Campus;
import domain.Docent;

public interface DocentRepository extends CrudRepository<Docent, Long> {
	// TODO
	List<Docent> docentenInTweeCampussen(
			@Param("campusA") Campus campusA,
			@Param("campusB") Campus campusB
	);
}
