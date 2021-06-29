package cu.cujae.pweb.los_tankes.repository;

import java.util.List;
import java.util.Optional;

import cu.cujae.pweb.los_tankes.domain.Pais;

public interface PaisRepository {

	int count();
	
	Optional<Pais> findById(Long id);
	
	Pais findByPaisName(String name);

    int save(Pais pais);

    int update(Pais pais);

    int deleteById(Long id);

    List<Pais> findAll();

	
}
