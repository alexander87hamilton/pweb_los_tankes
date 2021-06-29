package cu.cujae.pweb.los_tankes.repository;

import java.util.List;
import java.util.Optional;

import cu.cujae.pweb.los_tankes.domain.Role;
import cu.cujae.pweb.los_tankes.domain.User;

public interface UserRepository {

	int count();
	
	Optional<User> findById(Long id);
	
	User findByUserName(String name);

    int save(User user);

    int update(User user);

    int deleteById(Long id);

    List<User> findAll();

    List<Role> findRolesByUserId(Long id);
    
    List<User> findByAttribute(String username, String full_name, String email);
	
}
