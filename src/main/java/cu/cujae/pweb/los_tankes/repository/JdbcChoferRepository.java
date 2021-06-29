package cu.cujae.pweb.los_tankes.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import cu.cujae.pweb.los_tankes.domain.Chofer;

@Repository
public class JdbcChoferRepository implements ChoferRepository{

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public int count() {
		return jdbcTemplate
				.queryForObject("SELECT count(*) FROM \"chofer\"", Integer.class);
	}

	@Override
	public Optional<Chofer> findById(String id) {
		if(!id.toString().equalsIgnoreCase("null"))
		{
			return namedParameterJdbcTemplate.queryForObject(
					"SELECT * FROM \"chofer\" where id = :id",
					new MapSqlParameterSource("id", id),
					(rs, rowNum) ->
					Optional.of(new Chofer(
							rs.getString("id"),
							rs.getString("nombre"),
							rs.getString("direccion"),
							rs.getString("categoria")
							))
					);
		} else {
			return null;
		}
		
	}

	@Override
	public int save(Chofer chofer) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("id", chofer.getId());
		mapSqlParameterSource.addValue("nombre", chofer.getNombre());
		mapSqlParameterSource.addValue("direccion", chofer.getDireccion());
		mapSqlParameterSource.addValue("categoria", chofer.getCategoria());

		return namedParameterJdbcTemplate.update(
				"INSERT INTO \"chofer\" (id, nombre, direccion, categoria) VALUES (:id, :nombre, :direccion, :categoria)",
				mapSqlParameterSource);
	}

	@Override
	public int update(Chofer chofer) {
	
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("id", chofer.getId());
		mapSqlParameterSource.addValue("nombre", chofer.getNombre());
		mapSqlParameterSource.addValue("direccion", chofer.getDireccion());
		mapSqlParameterSource.addValue("categoria", chofer.getCategoria());

		return namedParameterJdbcTemplate.update(
				"UPDATE \"chofer\" SET nombre = :nombre, direccion = :direccion, categoria = :categoria WHERE id = :id",
				mapSqlParameterSource);
	}

	@Override
	public int deleteById(String id) {
		return jdbcTemplate.update(
				"DELETE from public.chofer WHERE id = ?",
				id);
	}

	@Override
	public List<Chofer> findAll() {
		return namedParameterJdbcTemplate.query(
				"SELECT * FROM \"chofer\"",
				(rs, rowNum) ->
				new Chofer(
						rs.getString("id"),
						rs.getString("nombre"),
						rs.getString("direccion"),
						rs.getString("categoria")
						)
				);
	}

	@Override
	public Chofer findByChoferName(String nombre) {
		try {
			return namedParameterJdbcTemplate.queryForObject(
					"SELECT * FROM public.chofer WHERE nombre = :nombre",
					new MapSqlParameterSource("nombre", nombre),
					(rs, rowNum) ->
					new Chofer(
							rs.getString("id"),
							rs.getString("nombre"),
							rs.getString("direccion"),
							rs.getString("categoria")
							)
					);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}

	}


}
