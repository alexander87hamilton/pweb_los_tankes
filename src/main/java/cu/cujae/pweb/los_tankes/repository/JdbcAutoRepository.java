package cu.cujae.pweb.los_tankes.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import cu.cujae.pweb.los_tankes.domain.Auto;
import cu.cujae.pweb.los_tankes.domain.Estado;
import cu.cujae.pweb.los_tankes.domain.Marca;
import cu.cujae.pweb.los_tankes.domain.Modelo;


@Repository
public class JdbcAutoRepository implements AutoRepository{

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public int count() {
		return jdbcTemplate
				.queryForObject("SELECT count(*) FROM \"auto\"", Integer.class);
	}

	@Override
	public Optional<Auto> findById(String placa) {
		return namedParameterJdbcTemplate.queryForObject(
				"SELECT * FROM \"auto\" where placa = :placa",
				new MapSqlParameterSource("placa", placa),
				(rs, rowNum) ->
				Optional.of(new Auto(
						rs.getString("placa"),
						findModeloById(rs.getLong("idmodelo")),
						findEstadoById(rs.getLong("idestado")),
						rs.getLong("cantkm"),
						rs.getString("color")

						))
				);
	}

	
	@Override
	public Modelo findModeloById (Long id){
		return namedParameterJdbcTemplate.queryForObject(
				"SELECT * FROM \"modelo\" where id = :id",
				new MapSqlParameterSource("id", id),
				(rs, rowNum) ->
				new Modelo(
						rs.getLong("id"),
						rs.getString("nombre"),
						findMarcaById(rs.getLong("idmarca")),
						rs.getDouble("tarifa")
						)
				);
	}
	
	
	@Override
	public Marca findMarcaById (Long id){
		return namedParameterJdbcTemplate.queryForObject(
				"SELECT * FROM \"marca\" where id = :id",
				new MapSqlParameterSource("id", id),
				(rs, rowNum) ->
				new Marca(
						rs.getLong("id"),
						rs.getString("nombre")
						)
				);
	}
	
	
	
	@Override
	public Estado findEstadoById(Long id) {
		return namedParameterJdbcTemplate.queryForObject(
				"SELECT * FROM \"estado\" where id = :id",
				new MapSqlParameterSource("id", id),
				(rs, rowNum) ->
				new Estado(
						rs.getLong("id"),
						rs.getString("nombre")
						)
				);
	}

	@Override
	public int save(Auto auto) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("placa", auto.getPlaca());
		mapSqlParameterSource.addValue("idmodelo", auto.getModelo().getId());
		mapSqlParameterSource.addValue("idestado", auto.getEstado().getId());
		mapSqlParameterSource.addValue("cantkm", auto.getCantKm());
		mapSqlParameterSource.addValue("color", auto.getColor());


		return namedParameterJdbcTemplate.update(
				"INSERT INTO \"auto\" (placa, idmodelo, idestado, cantkm, color) VALUES (:placa, :idmodelo, :idestado, :cantkm, :color)",
				mapSqlParameterSource);
	}

	@Override
	public int update(Auto auto) {


		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("placa", auto.getPlaca());
		mapSqlParameterSource.addValue("idmodelo", auto.getModelo().getId());
		mapSqlParameterSource.addValue("idestado", auto.getEstado().getId());
		mapSqlParameterSource.addValue("cantkm", auto.getCantKm());
		mapSqlParameterSource.addValue("color", auto.getColor());
		
		
		return namedParameterJdbcTemplate.update(
				"UPDATE \"auto\" SET idmodelo = :idmodelo, idestado = :idestado, cantkm = :cantkm, color = :color WHERE placa = :placa",
				mapSqlParameterSource);
	}

	@Override
	public int deleteById(String placa) {
		return jdbcTemplate.update(
				"DELETE from public.auto WHERE placa = ?",
				placa);
	}

	@Override
	public List<Auto> findAll() {
		return namedParameterJdbcTemplate.query(
				"SELECT * FROM \"auto\"",
				(rs, rowNum) ->
				new Auto(
						rs.getString("placa"),
						findModeloById(rs.getLong("idmodelo")),
						findEstadoById(rs.getLong("idestado")),
						rs.getLong("cantkm"),
						rs.getString("color")
						)
				);
	}




}
