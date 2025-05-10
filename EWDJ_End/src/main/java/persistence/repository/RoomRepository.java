package persistence.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import domain.room.IRoomRepository;
import domain.room.Room;
import persistence.entity.EventEntity;
import persistence.entity.RoomEntity;
import persistence.mapper.RoomMapper;

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

		var res = em.createQuery(jpql, EventEntity.class)
				.setParameter("room", RoomMapper.toEntity(room, em))
				.setParameter("dateTime", dateTime)
				.getResultList();

		return res.size() > 0;
	}

	@Override
	public boolean existsByName(String name) {
		return this.exists(name);
	}

	@Override
	@Transactional
	public void AddRoom(Room room) {
		RoomEntity entity = RoomMapper.toEntity(room, em);
		insert(entity);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Room> giveRooms() {
		return RoomMapper.toDomain(findAll());
	}
}
