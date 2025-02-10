package main;

import domein.Docent;
import java.math.BigDecimal;
import jakarta.persistence.EntityManager;
import util.JPAUtil;

public class MAINoef2 {

    public static void main(String args[]) {

        //vraag aan de factory een entityManager
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();

        ////start een transactie
        entityManager.getTransaction().begin();

        ////persisteer de 3 objecten
        var docent = entityManager.find(Docent.class, 2L);
        
        if (docent == null) {
        	System.out.println("Niet gevonden");
        } else {
        	docent.opslag(new BigDecimal(200));
        }
        
        //commit
        entityManager.getTransaction().commit();
        
        //sluit de entityManager
        entityManager.close();
        
        //sluit de factory
        JPAUtil.getEntityManagerFactory().close();
    }

}