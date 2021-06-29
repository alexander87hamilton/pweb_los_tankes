package cu.cujae.pweb.los_tankes.service;

import java.util.List;
import java.util.Optional;

import cu.cujae.pweb.los_tankes.domain.Modelo;

public interface ModeloService {

	public int count();
	
	public List<Modelo> findAll();
	
	public Optional<Modelo> findById(Long id);
	
	public Modelo findByModeloName(String nombre);
	
	public int save(Modelo modelo);
	
	public int update(Modelo modelo);
	
	public int delete(Long id);
	
	
}
