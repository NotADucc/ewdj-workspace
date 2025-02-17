package domein;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public abstract class Vervoermiddel implements TebetalenTaks {
    
	private long id;
	
	@Getter @Setter private String nummerplaat;
	
    private final List<Onderhoudsbeurt> onderhoudsbeurten = new ArrayList<>();

    public Vervoermiddel(String nummerplaat) {
        this.nummerplaat = nummerplaat;
    }
    
    public List<Onderhoudsbeurt> getOnderhoudsbeurten() {
        //TODO
        return null;
    }
    
    public void addOnderhoudsbeurt(Onderhoudsbeurt ob){
        onderhoudsbeurten.add(ob);
    }

    @Override
    public String toString() {
        return "Vervoermiddel{nummerplaat=%s}%n".formatted(nummerplaat);
    }

}
