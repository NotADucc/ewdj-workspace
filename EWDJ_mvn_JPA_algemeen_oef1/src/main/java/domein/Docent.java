package domein;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "docenten")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "docentNr")
@ToString(exclude = "id")
public class Docent implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name = "PERSONEELSNR")
	@Getter
	private int docentNr;
	@Getter
	private String voornaam;
	@Getter
	private String familienaam;
	@Getter
	@Setter
	private BigDecimal wedde;

	public Docent(int docentNr, String voornaam, String familienaam, BigDecimal wedde) {
		this.docentNr = docentNr;
		this.voornaam = voornaam;
		this.familienaam = familienaam;
		this.wedde = wedde;
	}

	public void opslag(BigDecimal bedrag) {
		wedde = wedde.add(bedrag);
	}

//	@Override
//	public String toString() {
//		return "%d %s %s %s".formatted(docentNr, voornaam, familienaam, wedde);
//	}

}