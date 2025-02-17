package domein;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter 
@Entity
public class Onderhoudsbeurt {

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
