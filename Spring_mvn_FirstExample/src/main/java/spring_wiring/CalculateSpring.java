package spring_wiring;

import domain.Operation;
import domain.ResultWriter;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CalculateSpring {

	private Operation ops;
	private ResultWriter writer;

	public void execute(String[] numbers) {
		long op1 = Long.parseLong(numbers[0]);
		long op2 = Long.parseLong(numbers[1]);
		writer.showResult(
				"The result of %s%s%s is %s!"
						.formatted(op1, ops.getName(), op2, ops.operate(op1, op2))
		);
	}
}