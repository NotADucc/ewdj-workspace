package domein;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter 
public class LichteVracht extends Vervoermiddel {
    
	private double massa;

    public LichteVracht(double massa, String nummerplaat) {
        super(nummerplaat);
        this.massa = massa;
    }

    @Override
    public double geefVerkeersbelasting() {
        throw new UnsupportedOperationException("Not supported yet.");
        //volgens maximale massa
    }
}
