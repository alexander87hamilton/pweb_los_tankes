package cu.cujae.pweb.los_tankes.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import cu.cujae.pweb.los_tankes.domain.FormaPago;

@Repository
public class JdbcFormaPagoRepository implements FormaPagoRepository{

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public int count() {
		return jdbcTemplate
				.queryForObject("SELECT count(*) FROM \"formapago\"", Integer.class);
	}

	@Override
	public Optional<FormaPago> findById(Long id) {
		return namedParameterJdbcTemplate.queryForObject(
				"SELECT * FROM \"formapago\" where id = :id",
				new MapSqlParameterSource("id", id),
				(rs, rowNum) ->
				Optional.of(new FormaPago(
						rs.getLong("id"),
						rs.getString("nombre")
						))
				);
	}

	@Override
	public int save(FormaPago formapago) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("nombre", formapago.getNombre());

		return namedParameterJdbcTemplate.update(
				"INSERT INTO \"formapago\" (nombre) VALUES (:nombre)",
				mapSqlParameterSource);
	}

	@Override
	public int update(FormaPago formapago) {

		
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("id", formapago.getId());
		mapSqlParameterSource.addValue("nombre", formapago.getNombre());

		return namedParameterJdbcTemplate.update(
				"UPDATE \"formapago\" SET nombre = :nombre WHERE id = :id",
				mapSqlParameterSource);
	}

	@Override
	public int deleteById(Long id) {
		return jdbcTemplate.update(
				"DELETE from public.formapago WHERE id = ?",
				id);
	}

	@Override
	public List<FormaPago> findAll() {
		return namedParameterJdbcTemplate.query(
				"SELECT * FROM \"formapago\"",
				(rs, rowNum) ->
				new FormaPago(
						rs.getLong("id"),
						rs.getString("nombre")
						)
				);
	}

	@Override
	public FormaPago findByFormaPagoName(String nombre) {
		try {
			return namedParameterJdbcTemplate.queryForObject(
					"SELECT * FROM public.formapago WHERE nombre = :nombre",
					new MapSqlParameterSource("nombre", nombre),
					(rs, rowNum) ->
					new FormaPago(
							rs.getLong("id"),
							rs.getString("nombre")
							)
					);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}

	}


}
