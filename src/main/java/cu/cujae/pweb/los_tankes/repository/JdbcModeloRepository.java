package cu.cujae.pweb.los_tankes.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import cu.cujae.pweb.los_tankes.domain.Marca;
import cu.cujae.pweb.los_tankes.domain.Modelo;


@Repository
public class JdbcModeloRepository implements ModeloRepository{

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public int count() {
		return jdbcTemplate
				.queryForObject("SELECT count(*) FROM \"modelo\"", Integer.class);
	}

	@Override
	public Optional<Modelo> findById(Long id) {
		return namedParameterJdbcTemplate.queryForObject(
				"SELECT * FROM \"modelo\" where id = :id",
				new MapSqlParameterSource("id", id),
				(rs, rowNum) ->
				Optional.of(new Modelo(
						rs.getLong("id"),
						rs.getString("nombre"),
						findMarcaById(rs.getLong("idmarca")),
						rs.getDouble("tarifa")
						
						))
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
	public int save(Modelo modelo) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("idmarca", modelo.getMarca().getId());
		mapSqlParameterSource.addValue("nombre", modelo.getNombre());
		mapSqlParameterSource.addValue("tarifa", modelo.getTarifa());


		return namedParameterJdbcTemplate.update(
				"INSERT INTO \"modelo\" (idmarca, nombre, tarifa) VALUES (:idmarca, :nombre, :tarifa)",
				mapSqlParameterSource);
	}

	@Override
	public int update(Modelo modelo) {

		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("id", modelo.getId());
		mapSqlParameterSource.addValue("idmarca", modelo.getMarca().getId());
		mapSqlParameterSource.addValue("nombre", modelo.getNombre());
		mapSqlParameterSource.addValue("tarifa", modelo.getTarifa());

		return namedParameterJdbcTemplate.update(
				"UPDATE \"modelo\" SET idmarca = :idmarca, nombre = :nombre, tarifa = :tarifa WHERE id = :id",
				mapSqlParameterSource);
	}

	@Override
	public int deleteById(Long id) {
		return jdbcTemplate.update(
				"DELETE from public.modelo WHERE id = ?",
				id);
	}

	@Override
	public List<Modelo> findAll() {
		return namedParameterJdbcTemplate.query(
				"SELECT * FROM \"modelo\"",
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
	public Modelo findByModeloName(String nombre) {
		try {
			return namedParameterJdbcTemplate.queryForObject(
					"SELECT * FROM public.modelo WHERE nombre = :nombre",
					new MapSqlParameterSource("nombre", nombre),
					(rs, rowNum) ->
					new Modelo(
							rs.getLong("id"),
							rs.getString("nombre"),
							findMarcaById(rs.getLong("id")),
							rs.getDouble("tarifa")
							)
					);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}

	}


}
