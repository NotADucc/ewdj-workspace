package domein;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "campusNaam")
@NamedQueries({ @NamedQuery(name = "Campus.findAll", query = "SELECT c FROM Campus c"),
		@NamedQuery(name = "Campus.findByName", query = """
					SELECT c
					FROM Campus c
					WHERE c.campusNaam = :naam
				"""), })
public class Campus implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int campusID;

	@Getter
	@Setter
	private String campusNaam;

	@ManyToMany(mappedBy = "campussen")
//	@ManyToMany(mappedBy = "campussen", fetch = FetchType.EAGER)
	private final Set<Docent> docenten = new HashSet<>();

	public Campus(String campusNaam) {
		this.campusNaam = campusNaam;
	}

	public Set<Docent> getDocenten() {
		return Collections.unmodifiableSet(docenten);
	}

	public void addDocent(Docent docent) {
		docenten.add(docent);
	}

	public void removeDocent(Docent docent) {
		docenten.remove(docent);
	}

	@Override
	public String toString() {
		return "%d %s".formatted(campusID, campusNaam);
	}

}