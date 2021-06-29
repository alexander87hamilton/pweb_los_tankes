package cu.cujae.pweb.los_tankes.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cu.cujae.pweb.los_tankes.domain.Pais;
import cu.cujae.pweb.los_tankes.repository.PaisRepository;

@Service
@Transactional
public class PaisServiceImpl implements PaisService{

	@Autowired
	private PaisRepository paisRepository;
	
	@Override
	public int count() {
		int cout = paisRepository.count();
		return cout;
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Pais> findById(Long id) {
		return paisRepository.findById(id);
	}
	
	@Override
	public int save(Pais pais) {
		
		return paisRepository.save(pais);
	}

	@Override
	public int update(Pais pais) {
		return paisRepository.update(pais);
	}

	@Override
	public int delete(Long id) {
		return paisRepository.deleteById(id);
	}
	
	@Override
	public List<Pais> findAll() {
		List<Pais> listPaiss= paisRepository.findAll();
		return listPaiss;
	}

	@Override
	public Pais findByPaisName(String nombre) {
		return paisRepository.findByPaisName(nombre);
	}
}
