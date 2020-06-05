package csci5408.catme.dao;

import csci5408.catme.domain.Role;

public interface RoleDao extends Dao<Role, Long> {
    Long getRoleIdByName(String roleName);
}
