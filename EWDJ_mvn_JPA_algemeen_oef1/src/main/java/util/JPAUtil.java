package util;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JPAUtil {
    
	@Getter 
	private final static EntityManagerFactory entityManagerFactory =
                            Persistence.createEntityManagerFactory("school");
    
}