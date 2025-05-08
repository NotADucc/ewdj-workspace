package persistence.repository;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import domain.IGenericRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class GenericRepository<T> implements IGenericRepository<T> {

	@PersistenceContext
	protected EntityManager em;
	private final Class<T> type;

	public GenericRepository(Class<T> type) {
		this.type = type;
	}

	@Override
	public void closePersistency() {
		em.close();
	}

	@Override
	public void startTransaction() {
	}

	@Override
	public void commitTransaction() {
	}

	@Override
	public void rollbackTransaction() {
	}

	@Override
	@Transactional(readOnly = true)
	public List<T> findAll() {
		return em.createQuery("select entity from %s entity".formatted(type.getName()), type)
				.getResultList();
	}

	@Override
	@Transactional(readOnly = true)
	public <U> T get(U id) {
		return em.find(type, id);
	}

	@Override
	@Transactional
	public T update(T object) {
		return em.merge(object);
	}

	@Override
	@Transactional
	public void delete(T object) {
		em.remove(em.merge(object));
	}

	@Override
	@Transactional
	public void insert(T object) {
		em.persist(object);
	}

	@Override
	@Transactional(readOnly = true)
	public <U> boolean exists(U id) {
		return em.find(type, id) != null;
	}
}