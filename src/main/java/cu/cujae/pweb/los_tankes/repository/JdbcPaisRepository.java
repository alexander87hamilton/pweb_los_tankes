package cu.cujae.pweb.los_tankes.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import cu.cujae.pweb.los_tankes.domain.Pais;

@Repository
public class JdbcPaisRepository implements PaisRepository{

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public int count() {
		return jdbcTemplate
				.queryForObject("SELECT count(*) FROM \"pais\"", Integer.class);
	}

	@Override
	public Optional<Pais> findById(Long id) {
		return namedParameterJdbcTemplate.queryForObject(
				"SELECT * FROM \"pais\" where id = :id",
				new MapSqlParameterSource("id", id),
				(rs, rowNum) ->
				Optional.of(new Pais(
						rs.getLong("id"),
						rs.getString("nombre")
						))
				);
	}

	@Override
	public int save(Pais pais) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("nombre", pais.getNombre());

		return namedParameterJdbcTemplate.update(
				"INSERT INTO \"pais\" (nombre) VALUES (:nombre)",
				mapSqlParameterSource);
	}

	@Override
	public int update(Pais pais) {

		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("id", pais.getId());
		mapSqlParameterSource.addValue("nombre", pais.getNombre());

		return namedParameterJdbcTemplate.update(
				"UPDATE \"pais\" SET nombre = :nombre WHERE id = :id",
				mapSqlParameterSource);
	}

	@Override
	public int deleteById(Long id) {
		return jdbcTemplate.update(
				"DELETE from public.pais WHERE id = ?",
				id);
	}

	@Override
	public List<Pais> findAll() {
		return namedParameterJdbcTemplate.query(
				"SELECT * FROM \"pais\"",
				(rs, rowNum) ->
				new Pais(
						rs.getLong("id"),
						rs.getString("nombre")
						)
				);
	}

	@Override
	public Pais findByPaisName(String nombre) {
		try {
			return namedParameterJdbcTemplate.queryForObject(
					"SELECT * FROM public.pais WHERE nombre = :nombre",
					new MapSqlParameterSource("nombre", nombre),
					(rs, rowNum) ->
					new Pais(
							rs.getLong("id"),
							rs.getString("nombre")
							)
					);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}

	}


}
