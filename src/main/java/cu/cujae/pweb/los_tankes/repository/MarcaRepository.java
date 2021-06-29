package cu.cujae.pweb.los_tankes.repository;

import java.util.List;
import java.util.Optional;

import cu.cujae.pweb.los_tankes.domain.Marca;

public interface MarcaRepository {

	int count();
	
	Optional<Marca> findById(Long id);
	
	Marca findByMarcaName(String name);

    int save(Marca marca);

    int update(Marca marca);

    int deleteById(Long id);

    List<Marca> findAll();

	
}
