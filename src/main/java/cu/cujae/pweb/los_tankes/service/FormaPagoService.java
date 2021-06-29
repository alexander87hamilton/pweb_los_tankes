package cu.cujae.pweb.los_tankes.service;

import java.util.List;
import java.util.Optional;

import cu.cujae.pweb.los_tankes.domain.FormaPago;

public interface FormaPagoService {

	public int count();
	
	public List<FormaPago> findAll();
	
	public Optional<FormaPago> findById(Long id);
	
	public FormaPago findByFormaPagoName(String nombre);
	
	public int save(FormaPago formaPago);
	
	public int update(FormaPago formaPago);
	
	public int delete(Long id);
	
	
}
