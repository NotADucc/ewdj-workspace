// well it's technically a class but to avoid confusion I've named it room
package domain.room;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Room {
	private String name;
	private Integer capacity;
}
