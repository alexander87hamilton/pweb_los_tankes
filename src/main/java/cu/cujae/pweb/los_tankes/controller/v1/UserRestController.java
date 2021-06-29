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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cu.cujae.pweb.los_tankes.domain.User;
import cu.cujae.pweb.los_tankes.service.UserService;


@RestController
@RequestMapping("/api/v1") 
public class UserRestController {
	
	@Autowired
	private UserService userService;
	
	
	@GetMapping("/user")
	public List<User> findAll(){
		return userService.findAll();
	}
	
	@GetMapping("/count")
	public int count(){
		return userService.count();
	}
	
	
	@GetMapping("/user/{userId}")
	public Optional<User> getUser(@PathVariable Long userId){
		return userService.findById(userId);
	}
	
	  
	@PostMapping("/user")
	public int saveUser(@RequestBody User user) {
		return userService.save(user);
	}
	
	
	 
	@PutMapping("/user")
	public int updateUser(@RequestBody User user) {
		return userService.update(user);
	}
	
	 
	@DeleteMapping("/user/{userId}")
	public int deteteUser(@PathVariable Long userId) {
		return userService.delete(userId);
	}
	
	
	@RequestMapping(value = "user", params = { "username", "full_name", "email" }, method = RequestMethod.GET)
	@ResponseBody
	public List<User> findByAttributes(@RequestParam("username") String username, 
			@RequestParam("full_name") String full_name, 
			@RequestParam("email") String email){
			return userService.findByAttribute(username, full_name, email);
	}
}
