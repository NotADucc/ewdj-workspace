package persistence.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode(of = { "name" })
@Setter
public class RoomEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String name;
	private int capacity;
	@OneToMany
	private Set<EventEntity> events = new HashSet<>();

	public void AddEvent(EventEntity event) {
		events.add(event);
	}

	public void RemoveEvent(EventEntity event) {
		events.remove(event);
	}
}
