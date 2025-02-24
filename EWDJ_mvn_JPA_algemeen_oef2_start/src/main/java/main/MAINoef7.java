package main;

import java.util.List;
import java.util.Optional;

import domein.Docent;
import domein.Werkruimte;
import jakarta.persistence.EntityManager;
import util.JPAUtil;

public class MAINoef7 {
	
	public static void main(String args[]) {
	
		EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
		entityManager.getTransaction().begin();

		Optional<Werkruimte> werkruimte = 
				Optional.ofNullable(entityManager.find(Werkruimte.class, "AA222"));
		
		if (werkruimte.isPresent()) {
			
			List<Docent> lijstDocenten = entityManager.createNamedQuery(
					"Docent.docentenInTweeCampussen2", Docent.class)
					.setParameter("campusNaam1", "gent")
					.setParameter("campusNaam2", "aalst").getResultList();

			lijstDocenten.forEach(docent -> docent.setWerkruimte(werkruimte.get()));
		}
		
		entityManager.getTransaction().commit();
		entityManager.close();
		JPAUtil.getEntityManagerFactory().close();
	}
}