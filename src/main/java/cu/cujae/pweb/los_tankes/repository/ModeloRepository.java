package cu.cujae.pweb.los_tankes.repository;

import java.util.List;
import java.util.Optional;

import cu.cujae.pweb.los_tankes.domain.Marca;
import cu.cujae.pweb.los_tankes.domain.Modelo;

public interface ModeloRepository {

	int count();
	
	Optional<Modelo> findById(Long id);
	
	Modelo findByModeloName(String name);

    int save(Modelo modelo);

    int update(Modelo modelo);

    int deleteById(Long id);

    List<Modelo> findAll();

	Marca findMarcaById(Long id);
	


	
}
