package cu.cujae.pweb.los_tankes.repository;

import java.util.List;
import java.util.Optional;

import cu.cujae.pweb.los_tankes.domain.Auto;
import cu.cujae.pweb.los_tankes.domain.Estado;
import cu.cujae.pweb.los_tankes.domain.Marca;
import cu.cujae.pweb.los_tankes.domain.Modelo;

public interface AutoRepository {

	int count();
	
	Optional<Auto> findById(String placa);
	
    int save(Auto auto);

    int update(Auto auto);

    int deleteById(String placa);

    List<Auto> findAll();

	Modelo findModeloById(Long id);

	Marca findMarcaById(Long id);

	Estado findEstadoById(Long id);

	
}
