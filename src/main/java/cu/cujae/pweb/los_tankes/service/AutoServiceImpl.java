package cu.cujae.pweb.los_tankes.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cu.cujae.pweb.los_tankes.domain.Auto;
import cu.cujae.pweb.los_tankes.repository.AutoRepository;

@Service
@Transactional
public class AutoServiceImpl implements AutoService{

	@Autowired
	private AutoRepository autoRepository;
	
	@Override
	public int count() {
		int cout = autoRepository.count();
		return cout;
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Auto> findById(String placa) {
		return autoRepository.findById(placa);
	}
	
	@Override
	public int save(Auto auto) {
		
		return autoRepository.save(auto);
	}

	@Override
	public int update(Auto auto) {
		return autoRepository.update(auto);
	}

	@Override
	public int delete(String placa) {
		return autoRepository.deleteById(placa);
	}
	
	@Override
	public List<Auto> findAll() {
		List<Auto> listAutos= autoRepository.findAll();
		return listAutos;
	}

	
}
