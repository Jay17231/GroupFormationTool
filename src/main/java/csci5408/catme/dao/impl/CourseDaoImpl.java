package csci5408.catme.dao.impl;

import csci5408.catme.dao.ICourseDao;
import csci5408.catme.domain.Course;
import csci5408.catme.sql.impl.ConnectionManager;
import csci5408.catme.sql.impl.QueryBuilder;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class CourseDaoImpl implements ICourseDao {

	final ConnectionManager dataSource;


	public CourseDaoImpl(ConnectionManager dataSource) {
		this.dataSource = dataSource;
	}


	@Override
	public Course save(Course course) {
		Connection con = dataSource.getConnection();
		ResultSet rs = null;
		Statement s = null;
		assert con != null;

		try {
			s = con.createStatement();
			QueryBuilder builder = new QueryBuilder(
					"INSERT INTO course" +
							"(id, name) " +
							"values (default,:name)");
			builder.setParameter("name", course.getName());


			s.executeUpdate(builder.query(), Statement.RETURN_GENERATED_KEYS);
			
			rs = s.getGeneratedKeys();
			if (rs.next()) {
				course.setId(rs.getLong(1));
			}
			

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			dataSource.close(rs);
			dataSource.close(s);
			dataSource.close(con);
		}
		return course;
	}

	@Override
	public Course update(Course course) {
		return null;
	}

	@Override
	public Optional<Course> findById(Long id) {
		Connection con = dataSource.getConnection();
		ResultSet rs = null;
		Statement s = null;
		assert con != null;
		Course course = null;
		try {
			s = con.createStatement();
			QueryBuilder builder = new QueryBuilder(
					"SELECT id, name from course" +
							" WHERE id = :id ");
			builder.setParameter("id", id);


			s.execute(builder.query());
			rs = s.getResultSet();
			if (rs.next()) {
				course = new Course(rs.getLong("id"), rs.getString("name"));
				return Optional.of(course);
			} else {
				return Optional.empty();
			}
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
	public boolean delete(Course course) {
		Connection con = dataSource.getConnection();
		ResultSet rs = null;
		Statement s = null;
		assert con != null;

		try {
			s = con.createStatement();
			QueryBuilder builder = new QueryBuilder("delete from enrollment where course_id= :id ");
			builder.setParameter("id", course.getId());

			s.executeUpdate(builder.query(), Statement.RETURN_GENERATED_KEYS);

			builder = new QueryBuilder("delete from course where id= :id ");
			builder.setParameter("id", course.getId());

			s.executeUpdate(builder.query(), Statement.RETURN_GENERATED_KEYS);
			rs = s.getGeneratedKeys();
			if (rs.next()) {
				course.setId(rs.getLong(1));
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			dataSource.close(rs);
			dataSource.close(s);
			dataSource.close(con);
		}

		return true;

	}



	@Override
	public List<Course> findAll() {
		Connection con = dataSource.getConnection();
		Statement s = null;
		ResultSet rs = null;
		assert con != null;
		List<Course> courses = new ArrayList<>();
		try {
			s = con.createStatement();
			if (s.execute("select id, name from course")) {
				rs = s.getResultSet();
				while (rs.next()) {
					Course u = new Course(
							rs.getLong("id"),
							rs.getString("name")

					);

					courses.add(u);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			dataSource.close(rs);
			dataSource.close(s);
			dataSource.close(con);
		}
		return courses;
	}

	@Override
	public Course findCoursesById(Long id) {
		Connection con = dataSource.getConnection();
		ResultSet rs = null;
		Statement s = null;
		assert con != null;
		Course u = null;
		try {
			s = con.createStatement();

			QueryBuilder builder = new QueryBuilder("select * from course where id = :id ");
			builder.setParameter("id", id);

			s.execute(builder.query());
			rs = s.getResultSet();
			if (rs.next()) {
				u = new Course(rs.getLong("id"), rs.getString("name"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			dataSource.close(rs);
			dataSource.close(s);
			dataSource.close(con);
		}
		return u;
	}

	@Override
	public List<Course> findCoursesByUserId(Long id) {
		Connection con = dataSource.getConnection();
		ResultSet rs = null;
		Statement s = null;
		assert con != null;
		List<Course> courses = new ArrayList<>();
		try {
			s = con.createStatement();

			String sql = "select c.id, c.name\n" +
					"from course c\n" +
					"INNER JOIN enrollment e on e.course_id = c.id\n" +
					"INNER JOIN user u on u.id = e.user_id\n" +
					"where u.id = :id \n";
			QueryBuilder builder = new QueryBuilder(sql);
			builder.setParameter("id", id);


			if (s.execute(builder.query())) {
				rs = s.getResultSet();
				while (rs.next()) {
					Course u = new Course(
							rs.getLong("id"),
							rs.getString("name")

					);

					courses.add(u);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			dataSource.close(rs);
			dataSource.close(s);
			dataSource.close(con);
		}
		return courses;
	}
}
