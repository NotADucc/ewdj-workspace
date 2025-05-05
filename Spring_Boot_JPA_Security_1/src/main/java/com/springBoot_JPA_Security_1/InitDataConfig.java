package com.springBoot_JPA_Security_1;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import domain.MyUser;
import domain.Role;
import repository.UserRepository;

@Component
public class InitDataConfig implements CommandLineRunner {

	private PasswordEncoder encoder = new BCryptPasswordEncoder();

	private static final String BCRYPTED_PASWOORD =
		      "$2a$12$JYQJAl6IMCyGKVUOGJbdlu8MV2kwRs7m2nlDUUUVhNSRbYLZkh2cS";
		       //string 'paswoord': https://bcrypt-generator.com
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public void run(String... args) {
	
		var user =
				MyUser.builder()
	                .username("nameUser")
	                .role(Role.USER)
	                .password(BCRYPTED_PASWOORD)
	                .city("Ghent")
	                .build();
	        var admin =
	        		MyUser.builder()
	                .username("admin")
	                .role(Role.ADMIN)
	                .password(encoder.encode("admin"))
	                .build();
	        
		List<MyUser> userList =  Arrays.asList(admin, user);
		userRepository.saveAll(userList);
	}

}
