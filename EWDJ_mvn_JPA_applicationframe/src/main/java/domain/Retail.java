package domain;

import java.util.List;
import java.util.Optional;

import repository.BierDao;
import repository.GenericDao;

public class Retail {
	
	private GenericDao<Winkel> winkelRepo;
	private BierDao bierRepo;
	private List<Winkel> winkelList;
	
	public Retail(GenericDao<Winkel> winkelDao, BierDao bierDao) {
		winkelRepo = winkelDao;
		bierRepo = bierDao;
	}
	
	public List<Winkel> getWinkelList() {
		if (winkelList == null) {
			winkelList = winkelRepo.findAll();
		}
		return winkelList;
	}

	public void voegBierBijWinkel(String bierNaam, String winkelNaam) {
		Optional<Winkel> winkel = getWinkelList().stream().filter(w -> w.getNaam().equalsIgnoreCase(winkelNaam))
				.findFirst();
		
		if (!winkel.isPresent()) {
			throw new IllegalArgumentException("winkel %s komt niet voor".formatted(winkelNaam));
		}
		Bier bier = bierRepo.getBierByName(bierNaam);

		winkelRepo.startTransaction();
		winkel.get().addBier(bier);
		winkelRepo.commitTransaction();
		
	}

	public void closePersistency() {
		winkelRepo.closePersistency();		
	}

}
