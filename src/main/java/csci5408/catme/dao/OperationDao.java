package csci5408.catme.dao;

import csci5408.catme.domain.Operation;

import java.util.List;

public interface OperationDao extends Dao<Operation, Long> {
    List<Operation> findAllByRoleId(Long id);
}
