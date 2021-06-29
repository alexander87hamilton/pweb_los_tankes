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

import cu.cujae.pweb.los_tankes.domain.Turista;
import cu.cujae.pweb.los_tankes.service.TuristaServiceImpl;


@RestController
@RequestMapping("/api/v9") 
public class TuristaRestController {
	
	
	@Autowired
	private TuristaServiceImpl turistaService;
	
	
	
	@GetMapping("/turista")
	public List<Turista> findAll(){
		return turistaService.findAll();
	}
	
	@GetMapping("/count")
	public int count(){
		return turistaService.count();
	}
	
	
	@GetMapping("/turista/{nopasaporte}")
	public Optional<Turista> getTurista(@PathVariable String nopasaporte){
		return turistaService.findById(nopasaporte);
	}
	
	 
	@PostMapping("/turista")
	public int saveTurista(@RequestBody Turista turista) {
		return turistaService.save(turista);
	}
	
	
	  
	@PutMapping("/turista")
	public int updateTurista(@RequestBody Turista turista) {
		return turistaService.update(turista);
	}
	
	
	@DeleteMapping("turista/{nopasaporte}")
	public int deteteTurista(@PathVariable String nopasaporte) {
		return turistaService.delete(nopasaporte);
	}
	

}
