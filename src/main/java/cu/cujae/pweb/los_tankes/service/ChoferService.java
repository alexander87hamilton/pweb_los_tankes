package cu.cujae.pweb.los_tankes.service;

import java.util.List;
import java.util.Optional;

import cu.cujae.pweb.los_tankes.domain.Chofer;

public interface ChoferService {

	public int count();
	
	public List<Chofer> findAll();
	
	public Optional<Chofer> findById(String id);
	
	public Chofer findByChoferName(String nombre);
	
	public int save(Chofer chofer);
	
	public int update(Chofer chofer);
	
	public int delete(String id);
	
	
}
