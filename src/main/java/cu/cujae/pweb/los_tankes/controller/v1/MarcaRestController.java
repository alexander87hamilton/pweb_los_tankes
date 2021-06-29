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

import cu.cujae.pweb.los_tankes.domain.Marca;
import cu.cujae.pweb.los_tankes.service.MarcaServiceImpl;


@RestController
@RequestMapping("/api/v5") 
public class MarcaRestController {
	
	
	@Autowired
	private MarcaServiceImpl marcaService;
	
	
	
	@GetMapping("/marca")
	public List<Marca> findAll(){
		return marcaService.findAll();
	}
	
	@GetMapping("/count")
	public int count(){
		return marcaService.count();
	}
	
	
	@GetMapping("/marca/{marcaId}")
	public Optional<Marca> getMarca(@PathVariable Long marcaId){
		return marcaService.findById(marcaId);
	}
	
	
	@PostMapping("/marca")
	public int saveMarca(@RequestBody Marca marca) {
		return marcaService.save(marca);
	}
	
	
	 
	@PutMapping("/marca")
	public int updateMarca(@RequestBody Marca marca) {
		return marcaService.update(marca);
	}
	
	 
	@DeleteMapping("marca/{marcaId}")
	public int deteteMarca(@PathVariable Long marcaId) {
		return marcaService.delete(marcaId);
	}
	

}
