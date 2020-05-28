package csci5408.catme.dao;

import java.util.List;
import java.util.Optional;

public interface Dao<T, ID> {
    T save(T t);
    T update(T t);
    Optional<T> findById(ID id);
    boolean delete(T t);
    List<T> findAll();
}
