package persistence.repository;

import java.util.List;

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
		em.getTransaction().begin();
	}

	@Override
	public void commitTransaction() {
		em.getTransaction().commit();
	}

	@Override
	public void rollbackTransaction() {
		em.getTransaction().rollback();
	}

	@Override
	public List<T> findAll() {
		return em.createQuery("select entity from " + type.getName() + " entity", type)
				.getResultList();
	}

	@Override
	public <U> T get(U id) {
		return em.find(type, id);
	}

	@Override
	public T update(T object) {
		return em.merge(object);
	}

	@Override
	public void delete(T object) {
		em.remove(em.merge(object));
	}

	@Override
	public void insert(T object) {
		em.persist(object);
	}

	@Override
	public <U> boolean exists(U id) {
		return em.find(type, id) != null;
	}
}
