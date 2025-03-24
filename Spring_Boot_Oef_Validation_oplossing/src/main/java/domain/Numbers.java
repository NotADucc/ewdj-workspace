package domain;

//import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import validator.RangeNumbers;

@Getter @Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@RangeNumbers(range = 1000)
public class Numbers {
    
    /* amount
    	maximum is 5000.50 (grens niet inbegrepen)
	wordt steeds met twee cijfers na de komma afgebeeld
	initiële waarde = 2000.856
     */
    @DecimalMax(value = "5000.50", inclusive = false)
    @NumberFormat(pattern = "#.00")
    @Builder.Default
    private Double amount = 2000.856;
   
    /* number1
                moet ingevuld zijn
                moet liggen tussen 1 en 11000 (grenzen inbegrepen)
    	initiële waarde = 2000 (wordt afgebeeld als 2.000)
     */
        
    @NotNull
    @Min(1)
    @Max(11000)
    //OF
    //@Range(min = 1, max = 11000)
    @NumberFormat(style = Style.NUMBER)
    @Builder.Default
    private Integer number1 = 2000;
  
    /*     
    number2 
        moet ingevuld zijn
        initiële waarde = 1234566
     */
    
    @NumberFormat(style = Style.NUMBER)
    @NotNull
    @Builder.Default
    private Integer number2 = 1234566;

}
