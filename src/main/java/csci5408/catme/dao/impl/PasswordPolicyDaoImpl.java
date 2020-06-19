/**
 * 
 */
package csci5408.catme.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import csci5408.catme.dao.IPasswordPolicyDao;
import csci5408.catme.domain.Course;
import csci5408.catme.dto.PasswordPolicy;
import csci5408.catme.service.IPasswordValidationService;
import csci5408.catme.sql.impl.ConnectionManager;

/**
 * @author krupa
 *
 */

@Component
public class PasswordPolicyDaoImpl implements IPasswordPolicyDao {
	
	final ConnectionManager dataSource;
	
	public PasswordPolicyDaoImpl(ConnectionManager dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public PasswordPolicy find() {
		Connection con = dataSource.getConnection();
		Statement s = null;
		ResultSet rs = null;
		assert con != null;
		PasswordPolicy passwordPolicy = new PasswordPolicy();
		try {
			s = con.createStatement();
			if (s.execute("select * from password_policy where isActive = True")) {
				rs = s.getResultSet();
				
			}
			if (rs.next()) {
				passwordPolicy.setMinLength(rs.getInt(1));
				passwordPolicy.setMaxLength(rs.getInt(2));
				passwordPolicy.setMinUpperCase(rs.getInt(3));
				passwordPolicy.setMinLowerCase(rs.getInt(4));
				passwordPolicy.setMinSymbol(rs.getInt(5));
				passwordPolicy.setBlockChar(rs.getString(6));
				passwordPolicy.setPasswordHistoryCount(rs.getLong(7));
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			dataSource.close(rs);
			dataSource.close(s);
			dataSource.close(con);
		}
		return passwordPolicy;
	}
	

}
