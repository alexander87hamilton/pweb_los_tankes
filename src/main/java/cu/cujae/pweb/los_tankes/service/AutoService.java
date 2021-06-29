package cu.cujae.pweb.los_tankes.service;

import java.util.List;
import java.util.Optional;

import cu.cujae.pweb.los_tankes.domain.Auto;

public interface AutoService {

	public int count();
	
	public List<Auto> findAll();
	
	public Optional<Auto> findById(String placa);
	
	
	
	public int save(Auto auto);
	
	public int update(Auto auto);
	
	public int delete(String placa);
	
	
}
