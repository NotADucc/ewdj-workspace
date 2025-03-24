package domain;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BankCustomer {

	@NotEmpty
	@Pattern(regexp="^[1-9]\\d{2}$", message="must be between 100 and 999")
	private String id;

	private String firstName, lastName;

	private double balance;

	public BankCustomer(String id) {
		this.id = id;
	}

	public double getBalanceNoSign() {
		return Math.abs(balance);
	}
	public boolean isBalancePositive() {
		return balance > 0;
	}
}