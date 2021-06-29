package cu.cujae.pweb.los_tankes.service;

import java.util.List;
import java.util.Optional;

import cu.cujae.pweb.los_tankes.domain.Role;

public interface RoleService {
	public int count();
	
	public List<Role> findAll();
	
	public Optional<Role> findById(Long id);
	
	public Role findByRoleName(String rolename);
	
	public int save(Role role);
	
	public int update(Role role);
	
	public int delete(Long id);
}
