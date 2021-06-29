package cu.cujae.pweb.los_tankes.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cu.cujae.pweb.los_tankes.domain.Chofer;
import cu.cujae.pweb.los_tankes.repository.ChoferRepository;

@Service
@Transactional
public class ChoferServiceImpl implements ChoferService{

	@Autowired
	private ChoferRepository choferRepository;
	
	@Override
	public int count() {
		int cout = choferRepository.count();
		return cout;
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Chofer> findById(String id) {
		return choferRepository.findById(id);
	}
	
	@Override
	public int save(Chofer chofer) {
		
		return choferRepository.save(chofer);
	}

	@Override
	public int update(Chofer chofer) {
		return choferRepository.update(chofer);
	}

	@Override
	public int delete(String id) {
		return choferRepository.deleteById(id);
	}
	
	@Override
	public List<Chofer> findAll() {
		List<Chofer> listChofers= choferRepository.findAll();
		return listChofers;
	}

	@Override
	public Chofer findByChoferName(String nombre) {
		return choferRepository.findByChoferName(nombre);
	}
}
