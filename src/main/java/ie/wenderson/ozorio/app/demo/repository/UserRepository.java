package ie.wenderson.ozorio.app.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ie.wenderson.ozorio.app.demo.entity.Role;
import ie.wenderson.ozorio.app.demo.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	public boolean existsByIdAndRole(Long id, Role role);
	
	public boolean existsByUsername(String username);
	
	public User findByUsernameAndPassword(String username, String Password);
	

}
