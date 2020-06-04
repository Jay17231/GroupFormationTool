package csci5408.catme.dao;

import csci5408.catme.domain.Enrollment;
import csci5408.catme.domain.Role;

public interface EnrollmentDao extends Dao<Enrollment, Long> {
	Role findRole(Long userId);

	boolean makeTA(Long userId);
}
