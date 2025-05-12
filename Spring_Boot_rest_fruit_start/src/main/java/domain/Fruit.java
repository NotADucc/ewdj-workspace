package domain;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "name")
@Getter
@Setter
//TODO
public class Fruit implements Serializable{
    
	private static final long serialVersionUID = 1L;
	
	//TODO
	private int id;
    
	//TODO
    private String name;
    
    //TODO
    private double quality;
      
}