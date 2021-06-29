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

import cu.cujae.pweb.los_tankes.domain.Modelo;
import cu.cujae.pweb.los_tankes.service.ModeloService;


@RestController
@RequestMapping("/api/v7") 
public class ModeloRestController {
	
	@Autowired
	private ModeloService modeloService;
	
	
	@GetMapping("/modelo")
	public List<Modelo> findAll(){
		return modeloService.findAll();
	}
	
	@GetMapping("/count")
	public int count(){
		return modeloService.count();
	}
	
	
	@GetMapping("/modelo/{modeloId}")
	public Optional<Modelo> getModelo(@PathVariable Long modeloId){
		return modeloService.findById(modeloId);
	}
	
	  
	@PostMapping("/modelo")
	public int saveModelo(@RequestBody Modelo modelo) {
		return modeloService.save(modelo);
	}
	
	
	  
	@PutMapping("/modelo")
	public int updateModelo(@RequestBody Modelo modelo) {
		return modeloService.update(modelo);
	}
	
	  
	@DeleteMapping("/modelo/{modeloId}")
	public int deteteModelo(@PathVariable Long modeloId) {
		return modeloService.delete(modeloId);
	}

}
