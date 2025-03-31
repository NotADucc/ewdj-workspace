package domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NamedQueries({ @NamedQuery(name = "Docent.docentenInTweeCampussen", query = """
		SELECT d
		FROM Docent d
		WHERE :campusA MEMBER OF d.campussen AND :campusB MEMBER OF d.campussen
		""") })
@EqualsAndHashCode(of = "docentNr")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Docent implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter(AccessLevel.NONE)
	private long id;

	private int docentNr;

	private String voornaam;
	private String familienaam;
	@Setter
	private BigDecimal wedde;

	@ManyToMany
	private Set<Campus> campussen = new HashSet<>();

	@ManyToOne
	@Setter
	private Werkruimte werkruimte;

	public Docent(int docentNr, String voornaam, String familienaam, BigDecimal wedde) {
		this.docentNr = docentNr;
		this.voornaam = voornaam;
		this.familienaam = familienaam;
		this.wedde = wedde;
	}

	public Set<Campus> getCampussen() {
		return Collections.unmodifiableSet(campussen);
	}

	public void opslag(BigDecimal bedrag) {
		wedde = wedde.add(bedrag);
	}

	public void addCampus(Campus campus) {
		campussen.add(campus);
	}

	public void removeCampus(Campus campus) {
		campussen.remove(campus);
	}

	public String toString() {
		return "%s %s %.2f Campus: %s Werkruimte: %s%n"
				.formatted(familienaam, voornaam, wedde, campussen, werkruimte);
	}
}