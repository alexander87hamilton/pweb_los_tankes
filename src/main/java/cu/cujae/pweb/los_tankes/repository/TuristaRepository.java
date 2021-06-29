package cu.cujae.pweb.los_tankes.repository;

import java.util.List;
import java.util.Optional;

import cu.cujae.pweb.los_tankes.domain.Pais;
import cu.cujae.pweb.los_tankes.domain.Turista;

public interface TuristaRepository {

	int count();
	
	Optional<Turista> findById(String nopasaporte);
	
	Turista findByTuristaName(String name);

    int save(Turista turista);

    int update(Turista turista);

    int deleteById(String nopasaporte);

    List<Turista> findAll();

	Pais findPaisById(Long id);

	
}
