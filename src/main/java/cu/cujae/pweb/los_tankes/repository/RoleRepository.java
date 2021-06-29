package cu.cujae.pweb.los_tankes.repository;

import java.util.List;
import java.util.Optional;

import cu.cujae.pweb.los_tankes.domain.Role;

public interface RoleRepository {
int count();
	
	Optional<Role> findById(Long id);
	
	Role findByRoleName(String name);

    int save(Role role);

    int update(Role role);

    int deleteById(Long id);

    List<Role> findAll();

    List<Role> findRolesByRoleId(Long id);
}
