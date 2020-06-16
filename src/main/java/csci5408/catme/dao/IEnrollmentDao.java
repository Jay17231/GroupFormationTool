package csci5408.catme.dao;

import csci5408.catme.domain.Enrollment;
import csci5408.catme.domain.Role;

public interface IEnrollmentDao extends IDao<Enrollment, Long> {
	Role findRole(Long userId, Long courseId);

	boolean makeTA(Long userId);

	Enrollment findEnrollment(Long userId, Long courseId);
}
