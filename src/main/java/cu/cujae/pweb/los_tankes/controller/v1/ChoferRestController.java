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

import cu.cujae.pweb.los_tankes.domain.Chofer;
import cu.cujae.pweb.los_tankes.service.ChoferServiceImpl;


@RestController
@RequestMapping("/api/v6") 
public class ChoferRestController {
	
	
	@Autowired
	private ChoferServiceImpl choferService;
	
	
	
	@GetMapping("/chofer")
	public List<Chofer> findAll(){
		return choferService.findAll();
	}
	
	@GetMapping("/count")
	public int count(){
		return choferService.count();
	}
	
	
	@GetMapping("/chofer/{choferId}")
	public Optional<Chofer> getChofer(@PathVariable String choferId){
		return choferService.findById(choferId);
	}
	
	  
	@PostMapping("/chofer")
	public int saveChofer(@RequestBody Chofer chofer) {
		return choferService.save(chofer);
	}
	
	
	  
	@PutMapping("/chofer")
	public int updateChofer(@RequestBody Chofer chofer) {
		return choferService.update(chofer);
	}
	
	 
	@DeleteMapping("chofer/{choferId}")
	public int deteteChofer(@PathVariable String choferId) {
		return choferService.delete(choferId);
	}
	

}
