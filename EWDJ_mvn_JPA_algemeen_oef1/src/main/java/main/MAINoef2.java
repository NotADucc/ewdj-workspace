package main;

import domein.Docent;
import java.math.BigDecimal;
import java.util.Optional;

import jakarta.persistence.EntityManager;
import util.JPAUtil;

public class MAINoef2 {

    public static void main(String args[]) {

        //vraag aan de factory een entityManager
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();

        ////start een transactie
        entityManager.getTransaction().begin();

        ////persisteer de 3 objecten
        Optional<Docent> docent = Optional.ofNullable(entityManager.find(Docent.class, 2L));
        if (docent.isPresent())
        	docent.get().opslag(new BigDecimal(200));
        else 
        	System.out.println("Niet gevonden");
        
//        if (docent == null) {
//        	System.out.println("Niet gevonden");
//        } else {
//        	docent.opslag(new BigDecimal(200));
//        }
        
        //commit
        entityManager.getTransaction().commit();
        
        //sluit de entityManager
        entityManager.close();
        
        //sluit de factory
        JPAUtil.getEntityManagerFactory().close();
    }

}