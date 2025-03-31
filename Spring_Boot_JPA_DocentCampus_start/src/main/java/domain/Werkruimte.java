package domain;

import java.io.Serializable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "lokaalcode")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Werkruimte implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String lokaalcode;
	private String naam;
	private int aantalStoelen;
	private int aantalComputers;

	@Override
	public String toString() {
		return String.format("%s %s %d %d", lokaalcode, naam, aantalStoelen, aantalComputers);
	}

}
