package cu.cujae.pweb.los_tankes.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import cu.cujae.pweb.los_tankes.domain.Estado;

@Repository
public class JdbcEstadoRepository implements EstadoRepository{

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public int count() {
		return jdbcTemplate
				.queryForObject("SELECT count(*) FROM \"estado\"", Integer.class);
	}

	@Override
	public Optional<Estado> findById(Long id) {
		return namedParameterJdbcTemplate.queryForObject(
				"SELECT * FROM \"estado\" where id = :id",
				new MapSqlParameterSource("id", id),
				(rs, rowNum) ->
				Optional.of(new Estado(
						rs.getLong("id"),
						rs.getString("nombre")
						))
				);
	}

	@Override
	public int save(Estado estado) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("nombre", estado.getNombre());

		return namedParameterJdbcTemplate.update(
				"INSERT INTO \"estado\" (nombre) VALUES (:nombre)",
				mapSqlParameterSource);
	}

	@Override
	public int update(Estado estado) {

		
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("id", estado.getId());
		mapSqlParameterSource.addValue("nombre", estado.getNombre());

		return namedParameterJdbcTemplate.update(
				"UPDATE \"estado\" SET nombre = :nombre WHERE id = :id",
				mapSqlParameterSource);
	}

	@Override
	public int deleteById(Long id) {
		return jdbcTemplate.update(
				"DELETE from public.estado WHERE id = ?",
				id);
	}

	@Override
	public List<Estado> findAll() {
		return namedParameterJdbcTemplate.query(
				"SELECT * FROM \"estado\"",
				(rs, rowNum) ->
				new Estado(
						rs.getLong("id"),
						rs.getString("nombre")
						)
				);
	}

	@Override
	public Estado findByEstadoName(String nombre) {
		try {
			return namedParameterJdbcTemplate.queryForObject(
					"SELECT * FROM public.estado WHERE nombre = :nombre",
					new MapSqlParameterSource("nombre", nombre),
					(rs, rowNum) ->
					new Estado(
							rs.getLong("id"),
							rs.getString("nombre")
							)
					);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}

	}


}
