package domain;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NamedQueries({
    @NamedQuery(name = "Bier.findByName",
                         query = """
                         		select b 
                         		from Bier b 
                         		where b.naam = :bierNaam
                         		""")            
})
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(exclude = "bierID")
@Setter
public class Bier implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Setter(AccessLevel.NONE)
    private int bierID;
	
    private String naam;
    private String soort;
    private String brouwerij;
    private double alcoholgehalte;
    private double beoordeling;

    public Bier(String naam, String soort,  double alcoholgehalte, double beoordeling, String brouwerij) {
        this.naam = naam;
        this.soort = soort;
        this.alcoholgehalte = alcoholgehalte;
        this.beoordeling = beoordeling;
        this.brouwerij = brouwerij;
    }

}
