package csci5408.catme.dao.impl;

import csci5408.catme.dao.UserDao;
import csci5408.catme.domain.User;
import csci5408.catme.sql.MySQLDataSource;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Service
public class UserDaoImpl implements UserDao {

    final MySQLDataSource dataSource;

    public UserDaoImpl(MySQLDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public User save(User user) {
        return null;
    }

    @Override
    public User update(User user) {
        return null;
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
        List<User> users = new ArrayList<>();
        try {
            Statement s = con.createStatement();
            if(s.execute("select id, first_name, last_name, email_id, password, student_id, is_admin from user")) {
                rs = s.getResultSet();
                while (rs.next()) {
                    User u = new User(
                            rs.getLong("id"),
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getString("student_id"),
                            rs.getBoolean("is_admin"),
                            rs.getString("email_id")
                            );
                    u.setPassword(rs.getString("password"));
                    users.add(u);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            dataSource.recycle(con);
        }
        return users;
    }
}
