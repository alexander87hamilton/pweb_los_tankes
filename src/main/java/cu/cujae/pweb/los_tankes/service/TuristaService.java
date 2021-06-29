package cu.cujae.pweb.los_tankes.service;

import java.util.List;
import java.util.Optional;

import cu.cujae.pweb.los_tankes.domain.Turista;

public interface TuristaService {

	public int count();
	
	public List<Turista> findAll();
	
	public Optional<Turista> findById(String turistaId);
	
	public Turista findByTuristaName(String nombre);
	
	public int save(Turista turista);
	
	public int update(Turista turista);
	
	public int delete(String turistaId);
	
	
}
