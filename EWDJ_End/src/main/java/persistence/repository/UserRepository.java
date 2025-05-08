package persistence.repository;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import domain.LocaleException;
import domain.user.IUserRepository;
import domain.user.User;
import domain.user.UserRole;
import persistence.entity.UserEntity;
import persistence.mapper.UserMapper;

public class UserRepository extends GenericRepository<UserEntity>
		implements IUserRepository, UserDetailsService {

	public UserRepository() {
		super(UserEntity.class);
	}

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		var user = getUserByUsername(username);
		if (user == null) {
			throw new LocaleException(
					"persistence.repository.UserRepository.loadUserByUsername",
					new Object[] { username }
			);
		}
		return new org.springframework.security.core.userdetails.User(
				user.getName(),
				user.getPassword(),
				convertAuthorities(user.getRole())
		);
	}

	private Collection<? extends GrantedAuthority> convertAuthorities(UserRole role) {
		return Collections.singletonList(new SimpleGrantedAuthority(role.toString()));
	}

	@Override
	@Transactional(readOnly = true)
	public User getUserByUsername(String username) {
		String jpql = """
				SELECT e
				FROM UserEntity e
				WHERE e.name = :name
				""";

		var res = em.createQuery(jpql, UserEntity.class)
				.setParameter("name", username)
				.getResultList();
		return res.size() == 0 ? null : UserMapper.toDomain(res.getFirst());
	}

	@Override
	@Transactional
	public void addUser(User user) {
		UserEntity entity = UserMapper.toEntity(user, em);
		insert(entity);
	}
}
