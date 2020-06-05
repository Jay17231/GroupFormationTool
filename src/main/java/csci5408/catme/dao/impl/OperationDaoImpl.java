package csci5408.catme.dao.impl;

import csci5408.catme.dao.OperationDao;
import csci5408.catme.domain.Operation;
import csci5408.catme.sql.ConnectionManager;
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
public class OperationDaoImpl implements OperationDao {

    private final ConnectionManager dataSource;

    public OperationDaoImpl(ConnectionManager dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Operation save(Operation operation) {
        return null;
    }

    @Override
    public Operation update(Operation operation) {
        return null;
    }

    @Override
    public Optional<Operation> findById(Long id) {
        Connection con = dataSource.getConnection();
        ResultSet rs = null;
        Statement s = null;
        assert con != null;
        try {
            s = con.createStatement();
            QueryBuilder builder = new QueryBuilder("select id, name from operations where id = :id");
            builder.setParameter("id", id);
            if(s.execute(builder.query())) {
                rs = s.getResultSet();
                if (rs.next()) {
                    Operation operation = Operation.valueOf(rs.getString("name"));
                    return Optional.of(operation);
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            dataSource.close(rs);
            dataSource.close(s);
            dataSource.close(con);
        }
        return Optional.empty();
    }

    @Override
    public boolean delete(Operation operation) {
        return false;
    }

    @Override
    public List<Operation> findAll() {
        Connection con = dataSource.getConnection();
        ResultSet rs = null;
        Statement s = null;
        assert con != null;
        List<Operation> operations = new ArrayList<>();
        try {
            s = con.createStatement();
            if(s.execute("select id, name from operations")) {
                rs = s.getResultSet();
                while (rs.next()) {
                    Operation operation = Operation.valueOf(rs.getString("name"));
                    operations.add(operation);
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
        return operations;
    }

    @Override
    public List<Operation> findAllByRoleId(Long id) {
        String sql = "select o.id, o.operation \n" +
                "from operations o \n" +
                "inner join role_operation ro on o.id = ro.operation_id\n" +
                "where role_id= :role_id ";
        QueryBuilder queryBuilder = new QueryBuilder(sql);
        queryBuilder.setParameter("role_id", id);
        Connection con = dataSource.getConnection();
        ResultSet rs = null;
        Statement s = null;
        assert con != null;
        List<Operation> operations = new ArrayList<>();
        try {
            s = con.createStatement();
            if(s.execute(queryBuilder.query())) {
                rs = s.getResultSet();
                while (rs.next()) {
                    Operation operation = Operation.valueOf(rs.getString("operation"));
                    operations.add(operation);
                }
                return operations;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            dataSource.close(rs);
            dataSource.close(s);
            dataSource.close(con);
        }
        return operations;
    }
}
