package cu.cujae.pweb.los_tankes.controller.v1;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cu.cujae.pweb.los_tankes.domain.Role;
import cu.cujae.pweb.los_tankes.service.RoleService;

@RestController
@RequestMapping("/api/role")
public class RoleRestController {

	
	@Autowired
	private RoleService roleService;
	
	
	@GetMapping("/role")
	public List<Role> findAll(){
		return roleService.findAll();
	}
	
	@GetMapping("/count")
	public int count(){
		return roleService.count();
	}
	
	
	@GetMapping("/role/{roleId}")
	public Optional<Role> getRole(@PathVariable Long roleId){
		return roleService.findById(roleId);
	}
	
	
	@GetMapping("/rolename/{roleName}")
	public Role getRoleName(@PathVariable String roleName){
		return roleService.findByRoleName(roleName);
	}
	
	  
	@PostMapping("/role")
	public int saveRole(@RequestBody Role role) {
		return roleService.save(role);
	}
	
	
	  
	@PutMapping("/role")
	public int updateRole(@RequestBody Role role) {
		return roleService.update(role);
	}
	
 
	@DeleteMapping("/role/{roleId}")
	public int deteteRole(@PathVariable Long roleId) {
		return roleService.delete(roleId);
	}
}
