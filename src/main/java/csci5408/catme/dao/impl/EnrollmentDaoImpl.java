package csci5408.catme.dao.impl;

import csci5408.catme.dao.EnrollmentDao;
import csci5408.catme.domain.Enrollment;
import csci5408.catme.sql.MySQLDataSource;
import csci5408.catme.sql.impl.QueryBuilder;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Component
public class EnrollmentDaoImpl implements EnrollmentDao {

    final
    MySQLDataSource dataSource;

    public EnrollmentDaoImpl(MySQLDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Enrollment save(Enrollment enrollment) {
        Connection con = dataSource.getConnection();
        ResultSet rs;
        assert con != null;

        try {
            Statement s = con.createStatement();
            QueryBuilder builder = new QueryBuilder(
                    "INSERT INTO enrollment " +
                            "(id, course_id, user_id, role_id) " +
                            "values (default, :courseId, :userId, :roleId)");
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
}
