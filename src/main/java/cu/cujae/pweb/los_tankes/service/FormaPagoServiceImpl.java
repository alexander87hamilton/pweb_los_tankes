package cu.cujae.pweb.los_tankes.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cu.cujae.pweb.los_tankes.domain.FormaPago;
import cu.cujae.pweb.los_tankes.repository.FormaPagoRepository;

@Service
@Transactional
public class FormaPagoServiceImpl implements FormaPagoService{

	@Autowired
	private FormaPagoRepository formaPagoRepository;
	
	@Override
	public int count() {
		int cout = formaPagoRepository.count();
		return cout;
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<FormaPago> findById(Long id) {
		return formaPagoRepository.findById(id);
	}
	
	@Override
	public int save(FormaPago formaPago) {
		
		return formaPagoRepository.save(formaPago);
	}

	@Override
	public int update(FormaPago formaPago) {
		return formaPagoRepository.update(formaPago);
	}

	@Override
	public int delete(Long id) {
		return formaPagoRepository.deleteById(id);
	}
	
	@Override
	public List<FormaPago> findAll() {
		List<FormaPago> listFormaPagos= formaPagoRepository.findAll();
		return listFormaPagos;
	}

	@Override
	public FormaPago findByFormaPagoName(String nombre) {
		return formaPagoRepository.findByFormaPagoName(nombre);
	}
}
