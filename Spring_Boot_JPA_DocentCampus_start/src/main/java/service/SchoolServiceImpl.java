package service;

import java.util.Optional;

import domain.Werkruimte;
import repository.CampusRepository;
import repository.DocentRepository;
import repository.WerkruimteRepository;

public class SchoolServiceImpl implements SchoolService{

	private DocentRepository docentRepository;

	private CampusRepository campusRepository;

	private WerkruimteRepository werkruimteRepository;

	// Deliberately chose not to use the join solution so that more repository methods must be used
	public void changeWerkruimte(String lokaalCode, String campusNaam1, String campusNaam2){
		
		/*
		Optional<Werkruimte> werkruimte = werkruimteRepository.

		Optional<Campus> campusA = campusRepository.

		Optional<Campus> campusB = campusRepository.

		if (campusA.isPresent() && campusB.isPresent() && werkruimte.isPresent()) {
			List<Docent> lijstDocenten = docentRepository.
			lijstDocenten.forEach(docent -> docent.setWerkruimte(werkruimte.get()));
		}
		*/
	}
}
