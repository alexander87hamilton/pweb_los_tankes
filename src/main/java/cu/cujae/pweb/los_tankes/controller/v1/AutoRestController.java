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

import cu.cujae.pweb.los_tankes.domain.Auto;
import cu.cujae.pweb.los_tankes.service.AutoService;


@RestController
@RequestMapping("/api/v8") 
public class AutoRestController {
	
	@Autowired
	private AutoService autoService;
	
	
	@GetMapping("/auto")
	public List<Auto> findAll(){
		return autoService.findAll();
	}
	
	@GetMapping("/count")
	public int count(){
		return autoService.count();
	}
	
	
	@GetMapping("/auto/{placa}")
	public Optional<Auto> getAuto(@PathVariable String placa){
		return autoService.findById(placa);
	}
	
	
	@PostMapping("/auto")
	public int saveAuto(@RequestBody Auto auto) {
		return autoService.save(auto);
	}
	
	
	
	@PutMapping("/auto")
	public int updateAuto(@RequestBody Auto auto) {
		return autoService.update(auto);
	}
	
	  
	@DeleteMapping("/auto/{placa}")
	public int deteteAuto(@PathVariable String placa) {
		return autoService.delete(placa);
	}
	

}
