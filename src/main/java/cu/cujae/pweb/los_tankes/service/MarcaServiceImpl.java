package cu.cujae.pweb.los_tankes.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cu.cujae.pweb.los_tankes.domain.Marca;
import cu.cujae.pweb.los_tankes.repository.MarcaRepository;

@Service
@Transactional
public class MarcaServiceImpl implements MarcaService{

	@Autowired
	private MarcaRepository marcaRepository;
	
	@Override
	public int count() {
		int cout = marcaRepository.count();
		return cout;
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Marca> findById(Long id) {
		return marcaRepository.findById(id);
	}
	
	@Override
	public int save(Marca marca) {
		
		return marcaRepository.save(marca);
	}

	@Override
	public int update(Marca marca) {
		return marcaRepository.update(marca);
	}

	@Override
	public int delete(Long id) {
		return marcaRepository.deleteById(id);
	}
	
	@Override
	public List<Marca> findAll() {
		List<Marca> listMarcas= marcaRepository.findAll();
		return listMarcas;
	}

	@Override
	public Marca findByMarcaName(String nombre) {
		return marcaRepository.findByMarcaName(nombre);
	}
}
