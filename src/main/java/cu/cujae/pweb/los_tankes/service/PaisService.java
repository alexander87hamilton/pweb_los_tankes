package cu.cujae.pweb.los_tankes.service;

import java.util.List;
import java.util.Optional;

import cu.cujae.pweb.los_tankes.domain.Pais;

public interface PaisService {

	public int count();
	
	public List<Pais> findAll();
	
	public Optional<Pais> findById(Long id);
	
	public Pais findByPaisName(String nombre);
	
	public int save(Pais pais);
	
	public int update(Pais pais);
	
	public int delete(Long id);
	
	
}
