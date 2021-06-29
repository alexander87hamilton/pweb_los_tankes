package cu.cujae.pweb.los_tankes.repository;

import java.util.List;
import java.util.Optional;

import cu.cujae.pweb.los_tankes.domain.Chofer;

public interface ChoferRepository {

	int count();
	
	Optional<Chofer> findById(String id);
	
	Chofer findByChoferName(String name);

    int save(Chofer chofer);

    int update(Chofer chofer);

    int deleteById(String id);

    List<Chofer> findAll();

	
}
