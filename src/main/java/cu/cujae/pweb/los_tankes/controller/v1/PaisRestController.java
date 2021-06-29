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

import cu.cujae.pweb.los_tankes.domain.Pais;
import cu.cujae.pweb.los_tankes.service.PaisService;


@RestController
@RequestMapping("/api/v2") 
public class PaisRestController {
	
	
	@Autowired
	private PaisService paisService;
	
	
	
	@GetMapping("/pais")
	public List<Pais> findAll(){
		return paisService.findAll();
	}
	
	@GetMapping("/count")
	public int count(){
		return paisService.count();
	}
	
	
	@GetMapping("/pais/{paisId}")
	public Optional<Pais> getPais(@PathVariable Long paisId){
		return paisService.findById(paisId);
	}
	
	  
	@PostMapping("/pais")
	public int savePais(@RequestBody Pais pais) {
		return paisService.save(pais);
	}
	
	
	 
	@PutMapping("/pais")
	public int updatePais(@RequestBody Pais pais) {
		return paisService.update(pais);
	}
	
	  
	@DeleteMapping("pais/{paisId}")
	public int detetePais(@PathVariable Long paisId) {
		return paisService.delete(paisId);
	}
	

}
