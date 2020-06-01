package csci5408.catme.dao.impl;


import csci5408.catme.dao.CourseDao;
import csci5408.catme.domain.Course;
import csci5408.catme.sql.MySQLDataSource;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CourseDaoImpl implements CourseDao {

    final MySQLDataSource dataSource;


    public CourseDaoImpl(MySQLDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Course save(Course course) {
        return null;
    }

    @Override
    public Course update(Course course) {
        return null;
    }

    @Override
    public Optional<Course> findById(String s) {
        return Optional.empty();
    }

    @Override
    public boolean delete(Course course) {
        return false;
    }

    @Override
    public List<Course> findAll() {
        Connection con = dataSource.getConnection();
        ResultSet rs;
        assert con != null;
        List<Course> courses = new ArrayList<>();
        try {
            Statement s = con.createStatement();
            if(s.execute("select id, name from course")) {
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
            dataSource.recycle(con);
        }
        return courses;
    }

    @Override
    public List<Course> findCoursesByUserId(Long id) {
        return null;
    }
}
