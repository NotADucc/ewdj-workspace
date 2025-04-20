package persistence.repository;

import java.time.LocalDateTime;

import org.springframework.stereotype.Repository;

import domain.room.IRoomRepository;
import domain.room.Room;
import persistence.entity.RoomEntity;

@Repository
public class RoomRepository extends GenericRepository<RoomEntity> implements IRoomRepository {

	public RoomRepository() {
		super(RoomEntity.class);
	}

	@Override
	public boolean isRoomBooked(Room room, LocalDateTime dateTime) {
		String jpql = """
				SELECT e
				FROM EventEntity e
				WHERE e.room = :room AND e.dateTime = :dateTime
				""";

		var res = em.createQuery(jpql, RoomEntity.class)
				.setParameter("room", room)
				.setParameter("dateTime", dateTime)
				.getResultList();

		return res.size() > 0;
	}

	@Override
	public boolean existsByName(String name) {
		return this.exists(name);
	}
}
