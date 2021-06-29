package cu.cujae.pweb.los_tankes.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cu.cujae.pweb.los_tankes.domain.Role;
import cu.cujae.pweb.los_tankes.repository.RoleRepository;
@Service
@Transactional
public class RoleServiceImpl implements RoleService{
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Override
	public int count() {
		int cout = roleRepository.count();
		return cout;
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Role> findById(Long id) {
		return roleRepository.findById(id);
	}
	
	@Override
	public int save(Role role) {
		
		return roleRepository.save(role);
	}

	@Override
	public int update(Role role) {
		return roleRepository.update(role);
	}

	@Override
	public int delete(Long id) {
		return roleRepository.deleteById(id);
	}
	
	@Override
	public List<Role> findAll() {
		List<Role> listRoles= roleRepository.findAll();
		return listRoles;
	}
	@Override
	public Role findByRoleName(String roleName) {
		return roleRepository.findByRoleName(roleName);
	}
}
