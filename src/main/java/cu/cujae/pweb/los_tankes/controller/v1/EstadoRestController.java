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

import cu.cujae.pweb.los_tankes.domain.Estado;
import cu.cujae.pweb.los_tankes.service.EstadoServiceImpl;


@RestController
@RequestMapping("/api/v4")
public class EstadoRestController {
	
	
	@Autowired
	private EstadoServiceImpl estadoService;
	
	
	
	@GetMapping("/estado")
	public List<Estado> findAll(){
		return estadoService.findAll();
	}
	
	@GetMapping("/count")
	public int count(){
		return estadoService.count();
	}
	
	
	@GetMapping("/estado/{estadoId}")
	public Optional<Estado> getEstado(@PathVariable Long estadoId){
		return estadoService.findById(estadoId);
	}
	
	 
	@PostMapping("/estado")
	public int saveEstado(@RequestBody Estado estado) {
		return estadoService.save(estado);
	}
	
	
  
	@PutMapping("/estado")
	public int updateEstado(@RequestBody Estado estado) {
		return estadoService.update(estado);
	}
	
	 
	@DeleteMapping("estado/{estadoId}")
	public int deteteEstado(@PathVariable Long estadoId) {
		return estadoService.delete(estadoId);
	}
	

}
