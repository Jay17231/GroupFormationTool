package csci5408.catme.dao.impl;

import csci5408.catme.dao.UserDao;
import csci5408.catme.domain.User;
import csci5408.catme.sql.ConnectionManager;
import csci5408.catme.sql.impl.QueryBuilder;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class UserDaoImpl implements UserDao {

    final ConnectionManager dataSource;

    public UserDaoImpl(ConnectionManager dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public User save(User user) {
        Connection con = dataSource.getConnection();
        ResultSet rs;
        Statement s = null;
        assert con != null;

        try {
            s = con.createStatement();
            QueryBuilder builder = new QueryBuilder(
                    "INSERT INTO user " +
                        "(id, first_name, last_name, email_id, password, student_id, is_admin) " +
                        "values (default, :firstName, :lastName, :emailId, :password, :studentId, :isAdmin)");
            builder.setParameter("firstName", user.getFirstName());
            builder.setParameter("lastName", user.getLastName());
            builder.setParameter("emailId", user.getEmailId());
            builder.setParameter("password", user.getPassword());
            builder.setParameter("studentId", user.getStudentId());
            builder.setParameter("isAdmin", user.isAdmin());

            s.execute(builder.query());
            return findByEmail(user.getEmailId());

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            dataSource.close(s);
            dataSource.close(con);
        }
    }

    @Override
    public User update(User user) {
        Connection con = dataSource.getConnection();
        ResultSet rs;
        Statement s = null;
        assert con != null;
        try {
            QueryBuilder builder = new QueryBuilder(
                    "REPLACE INTO user " +
                            "(id, first_name, last_name, email_id, password, student_id, is_admin) " +
                            "values (default, :firstName, :lastName, :emailId, :password, :studentId, :isAdmin)");
            builder.setParameter("firstName", user.getFirstName());
            builder.setParameter("lastName", user.getLastName());
            builder.setParameter("emailId", user.getEmailId());
            builder.setParameter("password", user.getPassword());
            builder.setParameter("studentId", user.getStudentId());
            builder.setParameter("isAdmin", user.isAdmin());
            s = con.createStatement();
            s.execute(builder.query());
            return findByEmail(user.getEmailId());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            dataSource.close(s);
            dataSource.close(con);
        }
    }

    @Override
    public Optional<User> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean delete(User user) {
        return false;
    }

    @Override
    public List<User> findAll() {
        Connection con = dataSource.getConnection();
        ResultSet rs;
        assert con != null;
        Statement s = null;
        List<User> users = new ArrayList<>();
        try {
            s = con.createStatement();
            if(s.execute("select id, first_name, last_name, email_id, password, student_id, is_admin from user")) {
                rs = s.getResultSet();
                while (rs.next()) {
                    User u =getUser(rs);
                    users.add(u);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            dataSource.close(s);
            dataSource.close(con);
        }
        return users;
    }

    @Override
    public User findByEmail(String email) {
        Connection con = dataSource.getConnection();
        ResultSet rs;
        Statement s = null;
        assert con != null;
        try {
            s = con.createStatement();
            QueryBuilder builder = new QueryBuilder(
                    "select id, first_name, last_name, email_id, password, student_id, is_admin " +
                    "from user where email_id = :emailId");
            builder.setParameter("emailId", email);
            if(s.execute(builder.query())) {
                rs = s.getResultSet();
                if (rs.next()) {
                    return getUser(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            dataSource.close(s);
            dataSource.close(con);
        }
        return null;
    }

    private User getUser(ResultSet rs) throws SQLException {
        User u = new User(
                rs.getLong("id"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("student_id"),
                rs.getBoolean("is_admin"),
                rs.getString("email_id")
        );
        u.setPassword(rs.getString("password"));
        return u;
    }
}
