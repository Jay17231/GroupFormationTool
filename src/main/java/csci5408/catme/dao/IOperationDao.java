package csci5408.catme.dao;

import csci5408.catme.domain.Operation;

import java.util.List;

public interface IOperationDao extends IDao<Operation, Long> {
    List<Operation> findAllByRoleId(Long id);
}
