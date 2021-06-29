package cu.cujae.pweb.los_tankes.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cu.cujae.pweb.los_tankes.domain.Estado;
import cu.cujae.pweb.los_tankes.repository.EstadoRepository;

@Service
@Transactional
public class EstadoServiceImpl implements EstadoService{

	@Autowired
	private EstadoRepository estadoRepository;
	
	@Override
	public int count() {
		int cout = estadoRepository.count();
		return cout;
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Estado> findById(Long id) {
		return estadoRepository.findById(id);
	}
	
	@Override
	public int save(Estado estado) {
		
		return estadoRepository.save(estado);
	}

	@Override
	public int update(Estado estado) {
		return estadoRepository.update(estado);
	}

	@Override
	public int delete(Long id) {
		return estadoRepository.deleteById(id);
	}
	
	@Override
	public List<Estado> findAll() {
		List<Estado> listEstados= estadoRepository.findAll();
		return listEstados;
	}

	@Override
	public Estado findByEstadoName(String nombre) {
		return estadoRepository.findByEstadoName(nombre);
	}
}
