package csci5408.catme.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import csci5408.catme.dao.EnrollmentDao;
import csci5408.catme.domain.Enrollment;
import csci5408.catme.domain.Role;
import csci5408.catme.sql.ConnectionManager;
import csci5408.catme.sql.impl.QueryBuilder;

@Component
public class EnrollmentDaoImpl implements EnrollmentDao {

	final ConnectionManager dataSource;

	public EnrollmentDaoImpl(ConnectionManager dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public Enrollment save(Enrollment enrollment) {
		Connection con = dataSource.getConnection();
		ResultSet rs = null;
		Statement s = null;
		assert con != null;

		try {
			s = con.createStatement();
			QueryBuilder builder = new QueryBuilder("INSERT INTO enrollment " + "(id, course_id, user_id, role_id) "
					+ "values (default, :courseId, :userId, :roleId)");
			builder.setParameter("courseId", enrollment.getCourseId());
			builder.setParameter("userId", enrollment.getUserId());
			builder.setParameter("roleId", enrollment.getRoleId());
			s.executeUpdate(builder.query(), Statement.RETURN_GENERATED_KEYS);
			rs = s.getGeneratedKeys();

			Long generatedId = -1L;
			if (rs.next()) {
				generatedId = rs.getLong(1);
			}

			enrollment.setId(generatedId);
			return enrollment;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			dataSource.close(rs);
			dataSource.close(s);
			dataSource.close(con);
		}
	}

	@Override
	public Enrollment update(Enrollment enrollment) {
		return null;
	}

	@Override
	public Optional<Enrollment> findById(Long aLong) {
		return Optional.empty();
	}

	@Override
	public boolean delete(Enrollment enrollment) {
		return false;
	}

	@Override
	public List<Enrollment> findAll() {
		return null;
	}

	@Override
	public Role findRole(Long userId) {
		Role roleString = new Role();

		Connection connection = dataSource.getConnection();
		ResultSet resultSet = null;
		Statement s = null;
		assert connection != null;

		try {
			s = connection.createStatement();
			QueryBuilder builder = new QueryBuilder(
					"select * from roles where id = (select role_id from enrollment where user_id = :user_id);");
			builder.setParameter("user_id", userId);
			if (s.execute(builder.query())) {
				resultSet = s.getResultSet();
				if (resultSet.next()) {
					roleString.setId(resultSet.getLong("id"));
					roleString.setName(resultSet.getString("name"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			dataSource.close(resultSet);
			dataSource.close(s);
			dataSource.close(connection);
		}

		return roleString;
	}
}
