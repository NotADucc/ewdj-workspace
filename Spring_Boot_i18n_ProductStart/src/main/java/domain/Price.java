package domain;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Price {
    
    //private Integer percentIncrease;
    //+ getter en setter
    
    //drie @ 
    //het moet ingevuld zijn - foutboodschap wordt overschreven (zonder sleutel)
    //moet minstens 1 zijn - foutboodschap overschrijven (met sleutel)
    //hoogtens 50 - foutboodschap wordt overschreven (zonder sleutel)
    //foutboodschap NumberFormatException wordt overschreven
    
    private Integer percentIncrease;

    //private Integer percentDecrease;
    //twee @
    //het moet ingevuld zijn - foutboodschap wordt overschreven (zonder sleutel)
    //het moet liggen tussen 1 en 25 - foutboodschap overschrijven (met sleutel)
    //foutboodschap NumberFormatException wordt overschreven

    private Integer percentDecrease;
}








