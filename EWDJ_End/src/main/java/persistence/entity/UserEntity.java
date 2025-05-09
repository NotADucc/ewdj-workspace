package persistence.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import domain.event.Event;
import domain.user.UserRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "name")
@Setter
@Table(name = "users")
public class UserEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Setter(AccessLevel.NONE)
	private int id;
	private String name;
	private String password;
	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private UserRole role;
	@ManyToMany
	private Set<EventEntity> favoriteEvents = new HashSet<>();

	public UserEntity(
			String name, String password, UserRole role, Set<EventEntity> favoriteEvents
	) {
		this.name = name;
		this.password = password;
		this.role = role;
		this.favoriteEvents = favoriteEvents != null ? favoriteEvents : new HashSet<>();
	}

	public void addEvent(EventEntity event) {
		favoriteEvents.add(event);
	}

	public void removeEvent(EventEntity event) {
		favoriteEvents.remove(event);
	}
}
