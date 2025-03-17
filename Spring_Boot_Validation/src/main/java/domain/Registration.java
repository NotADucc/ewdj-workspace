package domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
//@ValidPasswords
public class Registration {

	@Pattern(regexp = "^[a-zA-Z]+", 
			message = "username must be alphanumeric with no spaces")
	@Size(min = 4, max = 15)
    private String userName;

	@NotBlank
	@Size(min = 4, max = 20)
    private String password;

    @NotBlank
    private String confirmPassword;

    @NotBlank
    @Email
    //@ValidEmail
    private String email;

}

