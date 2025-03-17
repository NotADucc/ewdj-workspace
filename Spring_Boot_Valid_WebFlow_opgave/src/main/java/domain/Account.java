package domain;

import java.math.BigDecimal;

import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Account {

	//moet ingevuld zijn
	//minstens 10000
    @NumberFormat(pattern = "#,##0.00")
    @Builder.Default
    private BigDecimal balance = new BigDecimal("20003000.2599");

    //>= 0 en  <60%  //>=0.0   en  <0.6
                  //"must be greater than or equal to 0%"
                  //message = "must be less than 60%"
    @NumberFormat(style = Style.PERCENT)
    @Builder.Default
    private double percent = 0.25;

    @Setter(AccessLevel.NONE)
    private BigDecimal balance2;
    @Setter(AccessLevel.NONE)
    private double percent2;

    //moet ingevuld zijn
    //moet geldige email zijn   @
    private String email;

    public void simpleExample() {
        balance2 = new BigDecimal("20003000.2599");
        percent2 = percent;
    }
    
}
