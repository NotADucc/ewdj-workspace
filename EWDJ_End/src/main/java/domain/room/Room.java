// well it's technically a class but to avoid confusion I've named it room
package domain.room;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Room {
	private String name;
	private int capacity;
}
