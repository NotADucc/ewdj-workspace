package domain;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import repository.BierDaoJpa;
import repository.GenericDaoJpa;

public class DomeinController {
	
	private Retail retail;

	public DomeinController() {
		this(false);
	}

	public DomeinController(boolean withInit) {
		retail = new Retail(new GenericDaoJpa<Winkel>(Winkel.class), new BierDaoJpa());
		if (withInit) {
			new PopulateDB().run();
		}
	}

	public List<String> geefWinkelList() {
		return retail.getWinkelList().stream().map(Winkel::getNaam).collect(Collectors.toList());
	}

	public void voegBierBijWinkel(String bierNaam, String winkelNaam) throws IllegalArgumentException {
		retail.voegBierBijWinkel(bierNaam, winkelNaam);
	}

	public List<String> geefBierLijst(Winkel winkel) {
		Set<Bier> bierSet = winkel.getBierSet();
		return bierSet.stream().map(Bier::toString).collect(Collectors.toList());
	}

	public void close() {
		retail.closePersistency();
	}

}
