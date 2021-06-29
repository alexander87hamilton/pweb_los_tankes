package cu.cujae.pweb.los_tankes.repository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import cu.cujae.pweb.los_tankes.domain.Auto;
import cu.cujae.pweb.los_tankes.domain.Chofer;
import cu.cujae.pweb.los_tankes.domain.Contrato;
import cu.cujae.pweb.los_tankes.domain.FormaPago;
import cu.cujae.pweb.los_tankes.domain.Turista;
import cu.cujae.pweb.los_tankes.util.ApiRestMapper;
import cu.cujae.pweb.los_tankes.util.RestService;

@Repository
public class JdbcContratoRepository implements ContratoRepository{

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private RestService restService;

	@Override
	public int count() {
		return jdbcTemplate
				.queryForObject("SELECT count(*) FROM \"contrato\"", Integer.class);
	}

	@Override
	public Optional<Contrato> findById(Long id) {
		return namedParameterJdbcTemplate.queryForObject(
				"SELECT * FROM \"contrato\" where id = :id",
				new MapSqlParameterSource("id", id),
				(rs, rowNum) ->
				{
					try {
						return Optional.of(new Contrato(
								rs.getLong("id"),
								rs.getDate("fechainicio"),
								rs.getDate("fechafin"),
								findFormaPagoById(rs.getLong("idfp")),
								rs.getDouble("tarifa"),
								findChoferById(rs.getString("idch")),
								findAutoById(rs.getString("placaauto")),
								findTuristaById(rs.getString("nopast")),
								rs.getDate("diasprorroga")

								));
					} catch (IOException e) {
						
						e.printStackTrace();
					}
					return null;
				}
				);
	}

	
	public FormaPago findFormaPagoById (Long id) throws IOException
	{
		FormaPago formaPago = null;
		
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		ApiRestMapper<FormaPago> apiRestMapper = new ApiRestMapper<>();
		String response = (String)restService.GET("/api/v3/formaPago/" + id, params, String.class).getBody();
		formaPago = apiRestMapper.mapOne(response, FormaPago.class);
		
		return formaPago;
	}

	
	public Chofer findChoferById (String id) throws IOException 
	{
		Chofer chofer = new Chofer();
		
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		ApiRestMapper<Chofer> apiRestMapper = new ApiRestMapper<>();
		String response = (String)restService.GET("/api/v6/chofer/" + id, params, String.class).getBody();
		chofer = apiRestMapper.mapOne(response, Chofer.class);
		
		return chofer;
	}

	
	public Auto findAutoById (String placa) throws IOException  
	{
		Auto auto = null;
		
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		ApiRestMapper<Auto> apiRestMapper = new ApiRestMapper<>();
		String response = (String)restService.GET("/api/v8/auto/" + placa, params, String.class).getBody();
		auto = apiRestMapper.mapOne(response, Auto.class);
		
		return auto;
	}

	
	public Turista findTuristaById (String turistaId) throws IOException {
		
		Turista turista = null;
		
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		ApiRestMapper<Turista> apiRestMapper = new ApiRestMapper<>();
		String response = (String)restService.GET("/api/v9/turista/" + turistaId, params, String.class).getBody();
		turista = apiRestMapper.mapOne(response, Turista.class);
		
		return turista;
	}


	@Override
	public int save(Contrato contrato) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("id", contrato.getId());
		mapSqlParameterSource.addValue("fechainicio", contrato.getFechaInicio());
		mapSqlParameterSource.addValue("fechafin", contrato.getFechaFin());
		mapSqlParameterSource.addValue("idfp", contrato.getFormaPago().getId());
		mapSqlParameterSource.addValue("tarifa", contrato.getTarifa());

		if(contrato.getChofer() != null)
			mapSqlParameterSource.addValue("idch", contrato.getChofer().getId());
		else
			mapSqlParameterSource.addValue("idch", null);
		
		mapSqlParameterSource.addValue("placaauto", contrato.getAuto().getPlaca());
		mapSqlParameterSource.addValue("nopast", contrato.getTurista().getNoPasaporte());
		mapSqlParameterSource.addValue("diasprorroga", contrato.getDiasProrroga());

		return namedParameterJdbcTemplate.update(
				"INSERT INTO \"contrato\" (fechainicio, fechafin, idfp, tarifa, idch, placaauto, nopast, diasprorroga) VALUES (:fechainicio, :fechafin, :idfp, :tarifa, :idch, :placaauto, :nopast, :diasprorroga)",
				mapSqlParameterSource);
	}

	@Override
	public int update(Contrato contrato) {
	
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("id", contrato.getId());
		mapSqlParameterSource.addValue("fechainicio", contrato.getFechaInicio());
		mapSqlParameterSource.addValue("fechafin", contrato.getFechaFin());
		mapSqlParameterSource.addValue("idfp", contrato.getFormaPago().getId());
		mapSqlParameterSource.addValue("tarifa", contrato.getTarifa());
		
		if(contrato.getChofer() != null)
			mapSqlParameterSource.addValue("idch", contrato.getChofer().getId());
		else
			mapSqlParameterSource.addValue("idch", null);
		
		mapSqlParameterSource.addValue("placaauto", contrato.getAuto().getPlaca());
		mapSqlParameterSource.addValue("nopast", contrato.getTurista().getNoPasaporte());
		mapSqlParameterSource.addValue("diasprorroga", contrato.getDiasProrroga());

		return namedParameterJdbcTemplate.update(
				"UPDATE \"contrato\" SET fechainicio = :fechainicio, fechafin = :fechafin, idfp = :idfp, tarifa = :tarifa, idch = :idch, placaauto = :placaauto, nopast = :nopast, diasprorroga = :diasprorroga WHERE id = :id",
				mapSqlParameterSource);
	}

	@Override
	public int deleteById(Long id) {
		return jdbcTemplate.update(
				"DELETE from public.contrato WHERE id = ?",
				id);
	}

	@Override
	public List<Contrato> findAll() {
		return namedParameterJdbcTemplate.query(
				"SELECT * FROM \"contrato\"",
				(rs, rowNum) ->
				{
					try {
						return new Contrato(
								rs.getLong("id"),
								rs.getDate("fechainicio"),
								rs.getDate("fechafin"),
								findFormaPagoById(rs.getLong("idfp")),
								rs.getDouble("tarifa"),
								findChoferById(rs.getString("idch")),
								findAutoById(rs.getString("placaauto")),
								findTuristaById(rs.getString("nopast")),
								rs.getDate("diasprorroga")
								);
					} catch (IOException e) {
						
						e.printStackTrace();
					}
					return null;
				}
				);
	}

	@Override
	public Contrato findByContratoName(String nombre) {
		try {
			return namedParameterJdbcTemplate.queryForObject(
					"SELECT * FROM public.contrato WHERE nombre = :nombre",
					new MapSqlParameterSource("nombre", nombre),
					(rs, rowNum) ->
					{
						try {
							return new Contrato(
									rs.getLong("id"),
									rs.getDate("fechainicio"),
									rs.getDate("fechafin"),
									findFormaPagoById(rs.getLong("idfp")),
									rs.getDouble("tarifa"),
									findChoferById(rs.getString("idch")),
									findAutoById(rs.getString("placaauto")),
									findTuristaById(rs.getString("nopast")),
									rs.getDate("diasprorroga")
									);
						} catch (IOException e) {
							
							e.printStackTrace();
						}
						return null;
					}
					);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}

	}


}
