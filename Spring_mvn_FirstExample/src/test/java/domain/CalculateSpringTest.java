package domain;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

import configuration.FirstExampleConfiguration;
import spring_wiring.CalculateSpring;

@SpringJUnitWebConfig(FirstExampleConfiguration.class)
class CalculateSpringTest {

	@Autowired
	private CalculateSpring calculateSpring;

	@Test
    void test() {
		Operation op = calculateSpring.getOps();
		assertInstanceOf(OperationAdd.class, op);
		assertInstanceOf(ScreenWriter.class, calculateSpring.getWriter());
		assertEquals(300, op.operate(100, 200));
	}

}
