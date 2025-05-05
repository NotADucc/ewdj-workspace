package repository;

import org.springframework.data.jpa.repository.JpaRepository;

import domain.MyUser;

public interface UserRepository extends JpaRepository<MyUser, Long> {

	MyUser findByUsername(String name);
}

