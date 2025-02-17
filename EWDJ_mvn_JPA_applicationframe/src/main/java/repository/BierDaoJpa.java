package repository;

import domain.Bier;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;

public class BierDaoJpa extends GenericDaoJpa<Bier> implements BierDao  {
	
    public BierDaoJpa() {
        super(Bier.class);
    }

    @Override
    public Bier getBierByName(String name) throws EntityNotFoundException {
        try {
            return em.createNamedQuery("Bier.findByName", Bier.class)
                 .setParameter("bierNaam", name)
                 .getSingleResult();
        } catch (NoResultException ex) {
        	throw new IllegalArgumentException("No beer found with name: %s".formatted(name));
            //OF throw new EntityNotFoundException("No beer found with name: %s".formatted(name));
        } catch (NonUniqueResultException ex) {
        	throw new IllegalArgumentException("Multiple beers found with name: %s".formatted(name));
            //OF throw new NonUniqueResultException("Multiple beers found with name: %s".formatted(name));
        }
    }
}
