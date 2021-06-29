package cu.cujae.pweb.los_tankes.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cu.cujae.pweb.los_tankes.domain.Turista;
import cu.cujae.pweb.los_tankes.repository.TuristaRepository;

@Service
@Transactional
public class TuristaServiceImpl implements TuristaService{

	@Autowired
	private TuristaRepository turistaRepository;
	
	@Override
	public int count() {
		int cout = turistaRepository.count();
		return cout;
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Turista> findById(String turistaId) {
		return turistaRepository.findById(turistaId);
	}
	
	@Override
	public int save(Turista turista) {
		
		return turistaRepository.save(turista);
	}

	@Override
	public int update(Turista turista) {
		return turistaRepository.update(turista);
	}

	@Override
	public int delete(String turistaId) {
		return turistaRepository.deleteById(turistaId);
	}
	
	@Override
	public List<Turista> findAll() {
		List<Turista> listTuristas= turistaRepository.findAll();
		return listTuristas;
	}

	@Override
	public Turista findByTuristaName(String nombre) {
		return turistaRepository.findByTuristaName(nombre);
	}
}
