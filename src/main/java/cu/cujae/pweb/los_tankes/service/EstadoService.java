package cu.cujae.pweb.los_tankes.service;

import java.util.List;
import java.util.Optional;

import cu.cujae.pweb.los_tankes.domain.Estado;

public interface EstadoService {

	public int count();
	
	public List<Estado> findAll();
	
	public Optional<Estado> findById(Long id);
	
	public Estado findByEstadoName(String nombre);
	
	public int save(Estado estado);
	
	public int update(Estado estado);
	
	public int delete(Long id);
	
	
}
