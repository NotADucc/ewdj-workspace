package service;

import jakarta.transaction.Transactional;

public interface SchoolService {

	//TODO
	@Transactional
	public void changeWerkruimte(String lokaalCode, String campusNaam1, String campusNaam2);
}
