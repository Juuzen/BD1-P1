package dao;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Optional;

public interface Dao<T, I> {
    Optional<T> get(I key);
    Collection<T> getAll();
    Optional<I> save(T t);
    void update(T t);
    void delete(T t);
}
