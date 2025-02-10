package domein;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "lokaalcode")
@Getter
@Setter
public class Werkruimte implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@Setter(AccessLevel.PRIVATE)
	private String lokaalcode;

	private String naam;
	private int aantalStoelen;
	private int aantalComputers;
	@OneToMany
	private Set<Docent> docenten = new HashSet<>();
	
	public Werkruimte(String lokaalcode, String naam, int aantalStoelen, int aantalComputers) {
		setLokaalcode(lokaalcode);
		setNaam(naam);
		setAantalStoelen(aantalStoelen);
		setAantalComputers(aantalComputers);
	}

	@Override
	public String toString() {
		return "%s %s %d %d".formatted(lokaalcode, naam, aantalStoelen, aantalComputers);
	}

}