package cu.cujae.pweb.los_tankes.service;

import java.util.List;
import java.util.Optional;

import cu.cujae.pweb.los_tankes.domain.Contrato;

public interface ContratoService {

	public int count();
	
	public List<Contrato> findAll();
	
	public Optional<Contrato> findById(Long id);
	
	public Contrato findByContratoName(String nombre);
	
	public int save(Contrato contrato);
	
	public int update(Contrato contrato);
	
	public int delete(Long id);
	
	
}
