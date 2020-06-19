package csci5408.catme.dao.impl;

import csci5408.catme.dao.IEnrollmentDao;
import csci5408.catme.domain.Enrollment;
import csci5408.catme.domain.Role;
import csci5408.catme.sql.impl.ConnectionManager;
import csci5408.catme.sql.impl.QueryBuilder;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Component
public class EnrollmentDaoImpl implements IEnrollmentDao {

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
		String sql = "UPDATE enrollment " +
				"set role_id= :roleId ," +
				" user_id= :userId ," +
				" course_id= :courseId " +
				"where id= :id ";
		Connection con = dataSource.getConnection();
		ResultSet rs = null;
		Statement s = null;
		assert con != null;

		try {
			s = con.createStatement();
			QueryBuilder builder = new QueryBuilder(sql);
			builder.setParameter("courseId", enrollment.getCourseId());
			builder.setParameter("userId", enrollment.getUserId());
			builder.setParameter("roleId", enrollment.getRoleId());
			builder.setParameter("id", enrollment.getId());
			s.execute(builder.query());

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
	public Role findRole(Long userId, Long courseId) {

		Connection connection = dataSource.getConnection();
		ResultSet resultSet = null;
		Statement s = null;
		assert connection != null;

		try {
			Role role = new Role();
			s = connection.createStatement();
			String sql = "select r.id, r.name \n" +
					"from roles r\n" +
					"inner join enrollment e on r.id = e.role_id\n" +
					"where e.course_id = :course_id and e.user_id = :user_id";
			QueryBuilder builder = new QueryBuilder(sql);

			builder.setParameter("user_id", userId);
			builder.setParameter("course_id", courseId);
			if (s.execute(builder.query())) {
				resultSet = s.getResultSet();
				if (resultSet.next()) {
					role.setId(resultSet.getLong("id"));
					role.setName(resultSet.getString("name"));
					return role;
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

		return null;
	}

	@Override
	public boolean makeTA(Long userId) {
		Boolean madeTA = false;
		Long roleId = (long) 403;
		Connection connection = dataSource.getConnection();
		Statement s = null;
		assert connection != null;

		try {
			s = connection.createStatement();
			QueryBuilder builder = new QueryBuilder(
					"update enrollment set role_id = :role_id where user_id = :user_id");
			builder.setParameter("role_id", roleId);
			builder.setParameter("user_id", userId);
			s.executeUpdate(builder.query());
			madeTA = true;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			dataSource.close(s);
			dataSource.close(connection);
		}
		return madeTA;
	}

	@Override
	public Enrollment findEnrollment(Long userId, Long courseId) {
		String sql = "select id, course_id, user_id, role_id \n" +
				"from enrollment\n" +
				"where user_id = :userId and course_id = :courseId";

		Connection connection = dataSource.getConnection();
		ResultSet resultSet = null;
		Statement s = null;
		assert connection != null;
		Enrollment enrollment = null;
		try {
			s = connection.createStatement();
			QueryBuilder builder = new QueryBuilder(sql);

			builder.setParameter("userId", userId);
			builder.setParameter("courseId", courseId);
			if (s.execute(builder.query())) {
				resultSet = s.getResultSet();
				if (resultSet.next()) {
					enrollment = new Enrollment();
					enrollment.setId(resultSet.getLong("id"));
					enrollment.setCourseId(resultSet.getLong("course_id"));
					enrollment.setUserId(resultSet.getLong("user_id"));
					enrollment.setRoleId(resultSet.getLong("role_id"));
				}
				return enrollment;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			dataSource.close(resultSet);
			dataSource.close(s);
			dataSource.close(connection);
		}

		return null;
	}

}
