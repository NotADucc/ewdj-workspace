package service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import domain.Campus;
import domain.Docent;
import domain.Werkruimte;
import repository.CampusRepository;
import repository.DocentRepository;
import repository.WerkruimteRepository;

public class SchoolServiceImpl implements SchoolService {
	@Autowired
	private DocentRepository docentRepository;
	@Autowired
	private CampusRepository campusRepository;
	@Autowired
	private WerkruimteRepository werkruimteRepository;

	// Deliberately chose not to use the join solution so that more repository
	// methods must be used
	public void changeWerkruimte(String lokaalCode, String campusNaam1, String campusNaam2) {

		Optional<Werkruimte> werkruimte = werkruimteRepository.findById(lokaalCode);

		Optional<Campus> campusA = campusRepository.findByCampusNaam(campusNaam1);

		Optional<Campus> campusB = campusRepository.findByCampusNaam(campusNaam2);

		if (campusA.isPresent() && campusB.isPresent() && werkruimte.isPresent()) {
			List<Docent> lijstDocenten = docentRepository
					.docentenInTweeCampussen(campusA.get(), campusB.get());
			lijstDocenten.forEach(docent -> docent.setWerkruimte(werkruimte.get()));
		}

	}
}
