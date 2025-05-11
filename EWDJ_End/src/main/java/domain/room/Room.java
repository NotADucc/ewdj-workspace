// well it's technically a class but to avoid confusion I've named it room
package domain.room;

import org.hibernate.validator.constraints.Range;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"name"})
public class Room {
	@Pattern(regexp = "^[a-zA-Z]\\d{3}$")
	private String name;
	@Range(min = 1, max = 50)
	private Integer capacity;
}
