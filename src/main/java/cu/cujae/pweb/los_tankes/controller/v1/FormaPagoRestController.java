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

import cu.cujae.pweb.los_tankes.domain.FormaPago;
import cu.cujae.pweb.los_tankes.service.FormaPagoServiceImpl;


@RestController
@RequestMapping("/api/v3")
public class FormaPagoRestController {
	
	
	@Autowired
	private FormaPagoServiceImpl formaPagoService;
	
	

	@GetMapping("/formaPago")
	public List<FormaPago> findAll(){
		return formaPagoService.findAll();
	}
	
	@GetMapping("/count")
	public int count(){
		return formaPagoService.count();
	}
	
	
	@GetMapping("/formaPago/{formaPagoId}")
	public Optional<FormaPago> getFormaPago(@PathVariable Long formaPagoId){
		return formaPagoService.findById(formaPagoId);
	}
	
	  
	@PostMapping("/formaPago")
	public int saveFormaPago(@RequestBody FormaPago formaPago) {
		return formaPagoService.save(formaPago);
	}
	
	
	  
	@PutMapping("/formaPago")
	public int updateFormaPago(@RequestBody FormaPago formaPago) {
		return formaPagoService.update(formaPago);
	}
	
	  
	@DeleteMapping("formaPago/{formaPagoId}")
	public int deteteFormaPago(@PathVariable Long formaPagoId) {
		return formaPagoService.delete(formaPagoId);
	}
	

}
