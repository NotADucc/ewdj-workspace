package persistence.mapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import domain.event.Event;
import domain.user.User;
import jakarta.persistence.EntityManager;
import persistence.entity.EventEntity;
import persistence.entity.RoomEntity;
import persistence.entity.UserEntity;

public class UserMapper {
	public static UserEntity toEntity(User user, EntityManager em) {
		UserEntity entity = em.find(UserEntity.class, user.getId());

		if (entity == null) {
			entity = new UserEntity(
					user.getName(),
					user.getPassword(),
					user.getRole(),
					EventMapper.toEntity(user.getFavoriteEvents(), em).stream().collect(Collectors.toSet())
			);
		}

		return entity;
	}

	public static List<User> toDomain(Collection<UserEntity> eventEntities) {
		return eventEntities.stream().map(UserMapper::toDomain).collect(Collectors.toList());
	}

	public static User toDomain(UserEntity userEntity) {
		return new User(
				userEntity.getId(),
				userEntity.getName(),
				userEntity.getPassword(),
				userEntity.getRole(),
				EventMapper.toDomain(userEntity.getFavoriteEvents()).stream().collect(Collectors.toSet())
		);
	}
}
