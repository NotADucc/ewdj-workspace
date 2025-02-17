package domain;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import repository.BierDao;
import repository.GenericDao;

@ExtendWith(MockitoExtension.class)
public class DomeinTest {
	
	@Mock
    private GenericDao<Winkel> winkelRepo;
	@Mock
    private BierDao  bierRepo;
    @InjectMocks
    private Retail retail;
    
    //In Eclipse niet nodig, in STS wel:
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void voegBierBijWinkel() {
       final String BIERNAAM = "Duvel", WINKELNAAM = "Station";

       Winkel eenWinkel = new Winkel(WINKELNAAM);   
       Bier eenBier = new Bier(BIERNAAM, "Blond", 8.5, 9.9, "Moortgat");

       when(winkelRepo.findAll()).thenReturn(Arrays.asList(eenWinkel));
       when(bierRepo.getBierByName(BIERNAAM)).thenReturn(eenBier);
       
       assertFalse(eenWinkel.getBierSet().contains(eenBier));
       retail.voegBierBijWinkel(BIERNAAM, WINKELNAAM);
       assertTrue(eenWinkel.getBierSet().contains(eenBier));
       verify(winkelRepo).findAll();
       verify(bierRepo).getBierByName(BIERNAAM); 
    }


}