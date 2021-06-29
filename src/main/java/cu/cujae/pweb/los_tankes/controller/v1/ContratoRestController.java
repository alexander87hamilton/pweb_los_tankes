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

import cu.cujae.pweb.los_tankes.domain.Contrato;
import cu.cujae.pweb.los_tankes.service.ContratoService;


@RestController
@RequestMapping("/api/v10") 
public class ContratoRestController {
	
	@Autowired
	private ContratoService contratoService;
	
	
	@GetMapping("/contrato")
	public List<Contrato> findAll(){
		return contratoService.findAll();
	}
	
	@GetMapping("/count")
	public int count(){
		return contratoService.count();
	}
	
	
	@GetMapping("/contrato/{contratoId}")
	public Optional<Contrato> getContrato(@PathVariable Long contratoId){
		return contratoService.findById(contratoId);
	}
	
	 
	@PostMapping("/contrato")
	public int saveContrato(@RequestBody Contrato contrato) {
		return contratoService.save(contrato);
	}
	
	
	
	@PutMapping("/contrato")
	public int updateContrato(@RequestBody Contrato contrato) {
		return contratoService.update(contrato);
	}
	
	  
	@DeleteMapping("/contrato/{contratoId}")
	public int deteteContrato(@PathVariable Long contratoId) {
		return contratoService.delete(contratoId);
	}
	

}
