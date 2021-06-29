package cu.cujae.pweb.los_tankes.repository;

import java.util.List;
import java.util.Optional;

import cu.cujae.pweb.los_tankes.domain.FormaPago;

public interface FormaPagoRepository {

	int count();
	
	Optional<FormaPago> findById(Long id);
	
	FormaPago findByFormaPagoName(String name);

    int save(FormaPago formaPago);

    int update(FormaPago formaPago);

    int deleteById(Long id);

    List<FormaPago> findAll();

	
}
