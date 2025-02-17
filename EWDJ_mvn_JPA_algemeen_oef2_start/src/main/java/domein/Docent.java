package domein;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "docenten")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "docentNr")
@ToString(exclude = "id")
@Getter
@NamedQueries({
@NamedQuery(name = "Docent.findAll", query = "SELECT d FROM Docent d")
})
public class Docent implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter(AccessLevel.NONE)
	private long id;
	@Column(name = "PERSONEELSNR")
//	@Getter
	private int docentNr;
//	@Getter
	private String voornaam;
//	@Getter
	private String familienaam;
//	@Getter
	@Setter
	private BigDecimal wedde;
	@ManyToOne
	@Setter
	private Werkruimte werkruimte;
	@ManyToMany
	private final Set<Campus> campussen = new HashSet<>();

	public Docent(int docentNr, String voornaam, String familienaam, BigDecimal wedde) {
		this.docentNr = docentNr;
		this.voornaam = voornaam;
		this.familienaam = familienaam;
		this.wedde = wedde;
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

	public Set<Campus> getCampussen() {
		return Collections.unmodifiableSet(campussen);
	}

}