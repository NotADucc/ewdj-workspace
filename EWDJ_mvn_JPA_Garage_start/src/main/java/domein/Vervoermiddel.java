package domein;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "nummerplaat")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Vervoermiddel implements TebetalenTaks {
    
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Getter @Setter private String nummerplaat;
	
	@OneToMany(mappedBy = "vervoermiddel", cascade = CascadeType.ALL)
    private final List<Onderhoudsbeurt> onderhoudsbeurten = new ArrayList<>();

    public Vervoermiddel(String nummerplaat) {
        this.nummerplaat = nummerplaat;
    }
    
    public List<Onderhoudsbeurt> getOnderhoudsbeurten() {
        return Collections.unmodifiableList(onderhoudsbeurten);
    }
    
    public void addOnderhoudsbeurt(Onderhoudsbeurt ob){
        onderhoudsbeurten.add(ob);
    }

    @Override
    public String toString() {
        return "Vervoermiddel{nummerplaat=%s}%n".formatted(nummerplaat);
    }

}
