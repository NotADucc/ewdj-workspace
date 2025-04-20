package domain.event;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.NumberFormat;

import domain.IHasRoom;
import domain.IHasSpeakers;
import domain.room.Room;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import persistence.entity.RoomEntity;
import validator.BeamerChecksum;
import validator.HasDuplicateSpeakers;

@Getter
@BeamerChecksum(divisor = 97)
@HasDuplicateSpeakers
public class Event implements IHasSpeakers, IHasRoom {
	private int id;
	@NotEmpty
	@Pattern(regexp = "^[a-zA-Z](\\w|\\W)*$")
	private String name;
	private String description;
	@NotEmpty
	private Room room;
	@NotEmpty
	private LocalDateTime dateTime;
	@NotEmpty
	@Pattern(regexp = "^\\d{4}$")
	private String beamercode;
	@NotEmpty
	private Integer beamercheck;
	@NotEmpty
	@DecimalMin(value = "9.99")
	@DecimalMax(value = "100.00", inclusive = false)
	@NumberFormat(pattern = "#.00")
	private Double price;
	@Size(min = 1, max = 3, message = "{event.Size.message}")
	private List<String> speakers = new ArrayList<>();
	
	public Event(
			String name, String description, Room room, LocalDateTime dateTime,
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
