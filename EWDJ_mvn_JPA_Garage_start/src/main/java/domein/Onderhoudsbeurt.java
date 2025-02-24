package domein;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(exclude = "id")
@NamedQueries({ @NamedQuery(name = "Onderhoudsbeurt.opDatum", query = """
		SELECT o
		FROM Onderhoudsbeurt o
		WHERE :datum BETWEEN o.begindatum AND o.einddatum
		"""), })
public class Onderhoudsbeurt implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	private long id;
	private LocalDate begindatum;

	private LocalDate einddatum;
	@ManyToOne
	private Vervoermiddel vervoermiddel;

	public Onderhoudsbeurt(LocalDate begindatum, LocalDate einddatum, Vervoermiddel vervoermiddel) {
		this.begindatum = begindatum;
		this.einddatum = einddatum;
		this.vervoermiddel = vervoermiddel;
	}

}
