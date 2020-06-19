package csci5408.catme.dao.impl;

import csci5408.catme.dao.IPasswordHistoryDao;
import csci5408.catme.domain.PasswordHistory;
import csci5408.catme.sql.impl.ConnectionManager;
import csci5408.catme.sql.impl.QueryBuilder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class PasswordHistoryDaoImpl implements IPasswordHistoryDao {
    final ConnectionManager dataSource;

    public PasswordHistoryDaoImpl(ConnectionManager dataSource) {
        this.dataSource = dataSource;
    }


    @Override
    public List<PasswordHistory> getPasswordsByUserId(Long userId, Long count) {
        Connection con = dataSource.getConnection();
        ResultSet rs = null;
        Statement s = null;
        assert con != null;
        List<PasswordHistory> passwords = new ArrayList<>();

        try {
            s = con.createStatement();
            String sql = "SELECT * from password_history" +
                    " WHERE u_id = :id ORDER BY creationtime DESC LIMIT :count ";
            QueryBuilder builder = new QueryBuilder(sql);
            builder.setParameter("id", userId);
            builder.setParameter("count", count);
            rs = s.executeQuery(builder.query());

            while (rs.next()) {
                PasswordHistory passwordhistory = new PasswordHistory(
                        rs.getLong("id"),
                        rs.getString("password"),
                        rs.getTimestamp("creationtime"),
                        rs.getLong("u_id")

                );

                passwords.add(passwordhistory);
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        finally {
            dataSource.close(rs);
            dataSource.close(s);
            dataSource.close(con);
        }

        return passwords;
    }

    @Override
    public void passwordInsert(Long userId, String password) {
        LocalDateTime creationDateTime = LocalDateTime.now();
        Connection con = dataSource.getConnection();
        ResultSet rs1=null;
        Statement s = null;
        assert con != null;
        try {
            s = con.createStatement();
            QueryBuilder builder = new QueryBuilder(
                    "INSERT INTO password_history" +
                            "(id, password, creationtime, u_id) " +
                            "values (default,:password,:creationtime,:u_id)");

            builder.setParameter("password",password);
            builder.setParameter("creationtime", creationDateTime);
            builder.setParameter("u_id",userId);

            s.executeUpdate(builder.query());

        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        finally {
            dataSource.close(rs1);
            dataSource.close(s);
            dataSource.close(con);
        }



    }


    @Override
    public PasswordHistory save(PasswordHistory passwordHistory) {
        return null;
    }

    @Override
    public PasswordHistory update(PasswordHistory passwordHistory) {
        return null;
    }

    @Override
    public Optional<PasswordHistory> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean delete(PasswordHistory passwordHistory) {
        return false;
    }

    @Override
    public List<PasswordHistory> findAll() {
        return null;
    }
}