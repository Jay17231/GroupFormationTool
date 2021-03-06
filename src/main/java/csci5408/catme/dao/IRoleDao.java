package csci5408.catme.dao;

import csci5408.catme.domain.Role;

public interface IRoleDao extends IDao<Role, Long> {
    Long getRoleIdByName(String roleName);
}
