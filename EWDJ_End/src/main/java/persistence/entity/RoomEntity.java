package persistence.entity;

import java.io.Serializable;

import jakarta.persistence.Entity;
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
@EqualsAndHashCode(exclude = "roomId")
@Setter
public class RoomEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	private int roomId;
}
