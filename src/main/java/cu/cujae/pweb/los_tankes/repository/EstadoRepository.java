package cu.cujae.pweb.los_tankes.repository;

import java.util.List;
import java.util.Optional;

import cu.cujae.pweb.los_tankes.domain.Estado;

public interface EstadoRepository {

	int count();
	
	Optional<Estado> findById(Long id);
	
	Estado findByEstadoName(String name);

    int save(Estado estado);

    int update(Estado estado);

    int deleteById(Long id);

    List<Estado> findAll();

	
}
