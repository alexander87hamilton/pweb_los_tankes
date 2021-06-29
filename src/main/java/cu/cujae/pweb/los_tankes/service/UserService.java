package cu.cujae.pweb.los_tankes.service;

import java.util.List;
import java.util.Optional;

import cu.cujae.pweb.los_tankes.domain.User;

public interface UserService {

	public int count();
	
	public List<User> findAll();
	
	public Optional<User> findById(Long id);
	
	public User findByUserName(String username);
	
	public int save(User user);
	
	public int update(User user);
	
	public int delete(Long id);
	
	public List<User> findByAttribute(String username, String full_name, String email);
	
}
