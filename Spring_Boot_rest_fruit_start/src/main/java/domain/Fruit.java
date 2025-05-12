package domain;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import utils.QualityDeserializer;
import utils.QualitySerializer;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "name")
@Getter
@Setter
//TODO
@JsonPropertyOrder({"fruit_id", "fruit_name", "quality"})
public class Fruit implements Serializable{
    
	private static final long serialVersionUID = 1L;
	
	//TODO
	@JsonProperty("fruit_id")
	private int id;
    
	//TODO
	@JsonProperty("fruit_name")
    private String name;
    
    //TODO
	@JsonSerialize(using = QualitySerializer.class)
	@JsonDeserialize(using = QualityDeserializer.class)
    private double quality;
      
}