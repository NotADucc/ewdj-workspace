package domain;

import java.io.Serializable;
import java.util.Collections;
import java.util.Set;
import java.util.HashSet;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "naam")
@Getter
public class Winkel implements Serializable {

	private static final long serialVersionUID = 1L;
	
    @Id 
    private String naam;
    
    @ManyToMany
    private Set<Bier> bierSet = new HashSet<>();

    public Winkel(String naam) {
        this.naam = naam;
    }

    public Set<Bier> getBierSet() {
        return Collections.unmodifiableSet(bierSet);
    }
    
    public void addBier(Bier bier){
        bierSet.add(bier);
    }
    
    public void removeBier(Bier bier){
        bierSet.remove(bier);
    }

}
