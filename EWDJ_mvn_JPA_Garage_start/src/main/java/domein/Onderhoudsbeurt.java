package domein;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter 
public class Onderhoudsbeurt {

    private LocalDate begindatum;
    
    private LocalDate einddatum;
    
    private Vervoermiddel vervoermiddel;

    public Onderhoudsbeurt(LocalDate begindatum, LocalDate einddatum, Vervoermiddel vervoermiddel) {
        this.begindatum = begindatum;
        this.einddatum = einddatum;
        this.vervoermiddel = vervoermiddel;
    }

}
