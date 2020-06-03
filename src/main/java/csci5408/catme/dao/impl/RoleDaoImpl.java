package csci5408.catme.dao.impl;

import csci5408.catme.dao.RoleDao;
import csci5408.catme.domain.Role;
import csci5408.catme.sql.MySQLDataSource;
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
public class RoleDaoImpl implements RoleDao {

    final MySQLDataSource dataSource;

    public RoleDaoImpl(MySQLDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Role save(Role role) {
        return null;
    }

    @Override
    public Role update(Role role) {
        return null;
    }

    @Override
    public Optional<Role> findById(Long id) {
        Connection con = dataSource.getConnection();
        ResultSet rs;
        assert con != null;
        try {
            Statement s = con.createStatement();
            QueryBuilder builder = new QueryBuilder("select id, name from roles where id= :id");
            builder.setParameter("id", id);
            if(s.execute(builder.query())) {
                rs = s.getResultSet();
                if (rs.next()) {
                    Role role = new Role(
                            rs.getLong("id"),
                            rs.getString("name")
                    );
                    return Optional.of(role);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            dataSource.recycle(con);
        }
        return Optional.empty();
    }

    @Override
    public boolean delete(Role role) {
        return false;
    }

    @Override
    public List<Role> findAll() {
        Connection con = dataSource.getConnection();
        ResultSet rs;
        assert con != null;
        List<Role> roles = new ArrayList<>();
        try {
            Statement s = con.createStatement();
            if(s.execute("select id, name from roles")) {
                rs = s.getResultSet();
                while (rs.next()) {
                    Role role = new Role(
                            rs.getLong("id"),
                            rs.getString("name")
                    );

                    roles.add(role);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            dataSource.recycle(con);
        }
        return roles;
    }
}
