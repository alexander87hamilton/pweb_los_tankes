package cu.cujae.pweb.los_tankes.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import cu.cujae.pweb.los_tankes.domain.Role;

@Repository
public class JdbcRoleRepository implements RoleRepository{

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public int count() {
		return jdbcTemplate
				.queryForObject("SELECT count(*) FROM \"role\"", Integer.class);
	}

	@Override
	public Optional<Role> findById(Long id) {
		return namedParameterJdbcTemplate.queryForObject(
				"SELECT * FROM \"role\" where id = :id",
				new MapSqlParameterSource("id", id),
				(rs, rowNum) ->
				Optional.of(new Role(
						rs.getLong("id"),
						rs.getString("role_name")
						))
				);
	}

	@Override
	public List<Role> findRolesByRoleId(Long id){
		return namedParameterJdbcTemplate.query(
				"SELECT r.id, r.role_name FROM role ur where ur.role_id = :id",
				new MapSqlParameterSource("id", id),
				(rs, rowNum) ->
				new Role(
						rs.getLong("id"),
						rs.getString("role_name")
						)
				);
	}
	

	@Override
	public int save(Role role) {
		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("role_name", role.getRoleName());
		int a= namedParameterJdbcTemplate.update(
				"INSERT INTO \"role\" (role_name) VALUES (:role_name)",
				mapSqlParameterSource);

		return a;
	}

	@Override
	public int update(Role role) {

		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
		mapSqlParameterSource.addValue("id", role.getId());
		mapSqlParameterSource.addValue("role_name", role.getRoleName());

		return namedParameterJdbcTemplate.update(
				"UPDATE \"role\" SET role_name = :role_name WHERE id = :id",
				mapSqlParameterSource);
	}

	@Override
	public int deleteById(Long id) {
		return jdbcTemplate.update(
				"DELETE from public.role WHERE id = ?",
				id);
	}

	@Override
	public List<Role> findAll() {
		return namedParameterJdbcTemplate.query(
				"SELECT id, role_name FROM \"role\"",
				(rs, rowNum) ->
				new Role(
						rs.getLong("id"),
						rs.getString("role_name")
						)
				);
	}



	@Override
	public Role findByRoleName(String role_name) {
		try {
			return namedParameterJdbcTemplate.queryForObject(
					"SELECT * FROM \"role\" where role_name = :role_name",
					new MapSqlParameterSource("role_name", role_name),
					(rs, rowNum) ->
					new Role(
							rs.getLong("id"),
							rs.getString("role_name")
							)
					);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}

	}


}
