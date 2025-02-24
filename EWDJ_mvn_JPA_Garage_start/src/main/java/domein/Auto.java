package domein;

import jakarta.persistence.Entity;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@NamedQueries({ @NamedQuery(name = "Auto.alleAutosZonderOnderhoud", query = """
			SELECT a
			FROM Auto a
			WHERE SIZE(a.onderhoudsbeurten) = 0
		"""), @NamedQuery(name = "Auto.alleAutosMetOnderhoud", query = """
			SELECT a
			FROM Auto a
			WHERE SIZE(a.onderhoudsbeurten) > 0
		"""), })
public class Auto extends Vervoermiddel {

	public Auto(String nummerplaat) {
		super(nummerplaat);
	}

	@Override
	public double geefVerkeersbelasting() {
		return 77.75;
		// volgens cilinderinhoud
	}
}
