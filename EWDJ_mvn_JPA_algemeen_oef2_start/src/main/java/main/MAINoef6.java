package main;

import domein.Campus;
import domein.Docent;
import domein.Werkruimte;

import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityManager;
import util.JPAUtil;

public class MAINoef6 {
	public static void main(String args[]) {
		EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
		entityManager.getTransaction().begin();

		Optional<Werkruimte> werkruimte = Optional.ofNullable(entityManager.find(Werkruimte.class, "SCH555"));

		Optional<Campus> campusAalst = entityManager.createNamedQuery("Campus.findByName", Campus.class)
				.setParameter("naam", "aalst").getResultStream().findFirst();

		Optional<Campus> campusGent = entityManager.createNamedQuery("Campus.findByName", Campus.class)
				.setParameter("naam", "gent").getResultStream().findFirst();

		if (campusAalst.isPresent() && campusGent.isPresent() && werkruimte.isPresent()) {
			List<Docent> docenten = entityManager.createNamedQuery("Docent.docentenInTweeCampussen", Docent.class)
					.setParameter("campusA", campusAalst.get()).setParameter("campusB", campusGent.get())
					.getResultList();
			docenten.forEach(x -> x.setWerkruimte(werkruimte.get()));
		}

		entityManager.getTransaction().commit();
		entityManager.close();
		JPAUtil.getEntityManagerFactory().close();
	}
}