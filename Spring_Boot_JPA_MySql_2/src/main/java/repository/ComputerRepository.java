package repository;

import org.springframework.data.repository.CrudRepository;

import domain.Computer;
import domain.ComputerId;

public interface ComputerRepository extends CrudRepository<Computer, ComputerId> {

}
