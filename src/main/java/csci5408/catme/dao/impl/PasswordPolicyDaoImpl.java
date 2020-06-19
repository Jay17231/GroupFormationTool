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
			if (s.execute("select min_length,max_length,min_no_uppercase,min_no_lowercase,min_no_symbol,block_char,password_history from password_policy where isActive = True")) {
				rs = s.getResultSet();
			}
			if (rs.next()) {
				passwordPolicy.setMinLength(rs.getInt("min_length"));
				passwordPolicy.setMaxLength(rs.getInt("max_length"));
				passwordPolicy.setMinUpperCase(rs.getInt("min_no_uppercase"));
				passwordPolicy.setMinLowerCase(rs.getInt("min_no_lowercase"));
				passwordPolicy.setMinSymbol(rs.getInt("min_no_symbol"));
				passwordPolicy.setBlockChar(rs.getString("block_char"));
				passwordPolicy.setPasswordHistoryCount(rs.getInt("password_history"));
				
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
