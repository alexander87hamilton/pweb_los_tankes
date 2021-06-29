package cu.cujae.pweb.los_tankes.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cu.cujae.pweb.los_tankes.domain.Contrato;
import cu.cujae.pweb.los_tankes.repository.ContratoRepository;

@Service
@Transactional
public class ContratoServiceImpl implements ContratoService{

	@Autowired
	private ContratoRepository contratoRepository;
	
	@Override
	public int count() {
		int cout = contratoRepository.count();
		return cout;
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Contrato> findById(Long id) {
		return contratoRepository.findById(id);
	}
	
	@Override
	public int save(Contrato contrato) {
		
		return contratoRepository.save(contrato);
	}

	@Override
	public int update(Contrato contrato) {
		return contratoRepository.update(contrato);
	}

	@Override
	public int delete(Long id) {
		return contratoRepository.deleteById(id);
	}
	
	@Override
	public List<Contrato> findAll() {
		List<Contrato> listContratos= contratoRepository.findAll();
		return listContratos;
	}

	@Override
	public Contrato findByContratoName(String nombre) {
		return contratoRepository.findByContratoName(nombre);
	}
}
