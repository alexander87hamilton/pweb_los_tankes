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
import cu.cujae.pweb.los_tankes.domain.Turista;

@Repository
public class JdbcTuristaRepository implements TuristaRepository{

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public int count() {
		return jdbcTemplate
				.queryForObject("SELECT count(*) FROM \"turista\"", Integer.class);
	}

	@Override
	public Optional<Turista> findById(String nopasaporte) {
		return namedParameterJdbcTemplate.queryForObject(
				"SELECT * FROM \"turista\" where nopasaporte = :nopasaporte",
				new MapSqlParameterSource("nopasaporte", nopasaporte),
				(rs, rowNum) ->
				Optional.of(new Turista(
						rs.getString("nopasaporte"),
						rs.getString("nombre"),
						rs.getDate("fechanacimiento"),
						rs.getString("sexo"),
						findPaisById(rs.getLong("idpais"))
						
						))
				);
	}
	
	@Override
	public Pais findPaisById (Long id){
		return namedParameterJdbcTemplate.queryForObject(
				"SELECT * FROM \"pais\" where id = :id",
				new MapSqlParameterSource("id", id),
				(rs, rowNum) ->
				new Pais(
						rs.getLong("id"),
						rs.getString("nombre")
						)
				);
	}

	@Override
	public int save(Turista turista) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("nopasaporte", turista.getNoPasaporte());
		mapSqlParameterSource.addValue("nombre", turista.getNombre());
		mapSqlParameterSource.addValue("fechanacimiento", turista.getFechaNacimiento());
		mapSqlParameterSource.addValue("sexo", turista.getSexo());
		mapSqlParameterSource.addValue("idpais", turista.getPais().getId());

		return namedParameterJdbcTemplate.update(
				"INSERT INTO \"turista\" (nopasaporte, nombre, fechanacimiento, sexo, idpais) VALUES (:nopasaporte, :nombre, :fechanacimiento, :sexo, :idpais)",
				mapSqlParameterSource);
	}

	@Override
	public int update(Turista turista) {

		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("nopasaporte", turista.getNoPasaporte());
		mapSqlParameterSource.addValue("nombre", turista.getNombre());
		mapSqlParameterSource.addValue("fechanacimiento", turista.getFechaNacimiento());
		mapSqlParameterSource.addValue("sexo", turista.getSexo());
		mapSqlParameterSource.addValue("idpais", turista.getPais().getId());
		

		return namedParameterJdbcTemplate.update(
				"UPDATE \"turista\" SET nombre = :nombre, fechanacimiento = :fechanacimiento, sexo = :sexo, idpais = :idpais WHERE nopasaporte = :nopasaporte",
				mapSqlParameterSource);
	}

	@Override
	public int deleteById(String id) {
		return jdbcTemplate.update(
				"DELETE from public.turista WHERE nopasaporte = ?",
				id);
	}

	@Override
	public List<Turista> findAll() {
		return namedParameterJdbcTemplate.query(
				"SELECT * FROM \"turista\"",
				(rs, rowNum) ->
				new Turista(
						rs.getString("nopasaporte"),
						rs.getString("nombre"),
						rs.getDate("fechanacimiento"),
						rs.getString("sexo"),
						findPaisById(rs.getLong("idpais"))
						)
				);
	}

	@Override
	public Turista findByTuristaName(String nombre) {
		try {
			return namedParameterJdbcTemplate.queryForObject(
					"SELECT * FROM public.turista WHERE nombre = :nombre",
					new MapSqlParameterSource("nombre", nombre),
					(rs, rowNum) ->
					new Turista(
							rs.getString("nopasaporte"),
							rs.getString("nombre"),
							rs.getDate("fechanacimiento"),
							rs.getString("sexo"),
							findPaisById(rs.getLong("idpais"))
							)
					);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}

	}


}
