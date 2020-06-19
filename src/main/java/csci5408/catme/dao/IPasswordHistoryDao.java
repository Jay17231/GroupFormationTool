package csci5408.catme.dao;

import csci5408.catme.domain.PasswordHistory;

import java.util.List;

public interface IPasswordHistoryDao extends IDao<PasswordHistory, Long>  {
   List<PasswordHistory> getPasswordsByUserId(Long id, Long count);

   void passwordInsert(Long id, String password);

}
