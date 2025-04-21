package persistence.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(exclude = "eventId")
@Setter
public class EventEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int eventId;
	private String name;
	private String description;
	@ManyToOne
	private RoomEntity room;
	private LocalDateTime dateTime;
	private String beamercode;
	private Integer beamercheck;
	private Double price;
	@ElementCollection
	private List<String> speakers = new ArrayList<>();

	public EventEntity(
			String name, String description, RoomEntity room, LocalDateTime dateTime,
			String beamercode, Integer beamercheck, Double price, List<String> speakers
	) {
		this.name = name;
		this.description = description;
		this.room = room;
		this.dateTime = dateTime;
		this.beamercode = beamercode;
		this.beamercheck = beamercheck;
		this.price = price;
		this.speakers = speakers != null ? speakers : new ArrayList<>();
	}
}
