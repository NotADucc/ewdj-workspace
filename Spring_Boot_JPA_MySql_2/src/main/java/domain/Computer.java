package domain;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "id")
@ToString
public class Computer {

	@EmbeddedId
	private ComputerId id;

	@Getter
	private String brand;

	public Computer(String code, int number, String brand) {
		this.id = new ComputerId(code, number);
		this.brand = brand;
	}

}
