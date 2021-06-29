package cu.cujae.pweb.los_tankes.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cu.cujae.pweb.los_tankes.domain.Modelo;
import cu.cujae.pweb.los_tankes.repository.ModeloRepository;

@Service
@Transactional
public class ModeloServiceImpl implements ModeloService{

	@Autowired
	private ModeloRepository modeloRepository;
	
	@Override
	public int count() {
		int cout = modeloRepository.count();
		return cout;
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Modelo> findById(Long id) {
		return modeloRepository.findById(id);
	}
	
	@Override
	public int save(Modelo modelo) {
		
		return modeloRepository.save(modelo);
	}

	@Override
	public int update(Modelo modelo) {
		return modeloRepository.update(modelo);
	}

	@Override
	public int delete(Long id) {
		return modeloRepository.deleteById(id);
	}
	
	@Override
	public List<Modelo> findAll() {
		List<Modelo> listModelos= modeloRepository.findAll();
		return listModelos;
	}

	@Override
	public Modelo findByModeloName(String nombre) {
		return modeloRepository.findByModeloName(nombre);
	}
}
