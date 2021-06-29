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

@Repository
public class JdbcMarcaRepository implements MarcaRepository{

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public int count() {
		return jdbcTemplate
				.queryForObject("SELECT count(*) FROM \"marca\"", Integer.class);
	}

	@Override
	public Optional<Marca> findById(Long id) {
		return namedParameterJdbcTemplate.queryForObject(
				"SELECT * FROM \"marca\" where id = :id",
				new MapSqlParameterSource("id", id),
				(rs, rowNum) ->
				Optional.of(new Marca(
						rs.getLong("id"),
						rs.getString("nombre")
						))
				);
	}

	@Override
	public int save(Marca marca) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("nombre", marca.getNombre());

		return namedParameterJdbcTemplate.update(
				"INSERT INTO \"marca\" (nombre) VALUES (:nombre)",
				mapSqlParameterSource);
	}

	@Override
	public int update(Marca marca) {

		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("id", marca.getId());
		mapSqlParameterSource.addValue("nombre", marca.getNombre());

		return namedParameterJdbcTemplate.update(
				"UPDATE \"marca\" SET nombre = :nombre WHERE id = :id",
				mapSqlParameterSource);
	}

	@Override
	public int deleteById(Long id) {
		return jdbcTemplate.update(
				"DELETE from public.marca WHERE id = ?",
				id);
	}

	@Override
	public List<Marca> findAll() {
		return namedParameterJdbcTemplate.query(
				"SELECT * FROM \"marca\"",
				(rs, rowNum) ->
				new Marca(
						rs.getLong("id"),
						rs.getString("nombre")
						)
				);
	}

	@Override
	public Marca findByMarcaName(String nombre) {
		try {
			return namedParameterJdbcTemplate.queryForObject(
					"SELECT * FROM public.marca WHERE nombre = :nombre",
					new MapSqlParameterSource("nombre", nombre),
					(rs, rowNum) ->
					new Marca(
							rs.getLong("id"),
							rs.getString("nombre")
							)
					);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}

	}


}
