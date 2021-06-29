package cu.cujae.pweb.los_tankes.service;

import java.util.List;
import java.util.Optional;

import cu.cujae.pweb.los_tankes.domain.Marca;

public interface MarcaService {

	public int count();
	
	public List<Marca> findAll();
	
	public Optional<Marca> findById(Long id);
	
	public Marca findByMarcaName(String nombre);
	
	public int save(Marca marca);
	
	public int update(Marca marca);
	
	public int delete(Long id);
	
	
}
