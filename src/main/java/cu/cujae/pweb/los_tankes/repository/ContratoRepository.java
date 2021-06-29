package cu.cujae.pweb.los_tankes.repository;

import java.util.List;
import java.util.Optional;

import cu.cujae.pweb.los_tankes.domain.Contrato;

public interface ContratoRepository {

	int count();
	
	Optional<Contrato> findById(Long id);
	
	Contrato findByContratoName(String name);

    int save(Contrato contrato);

    int update(Contrato contrato);

    int deleteById(Long id);

    List<Contrato> findAll();

	
}
