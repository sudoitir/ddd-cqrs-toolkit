package io.github.sudoitir.dddcqrstoolkit.repository;

import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Optional;

public abstract class BaseRepository<T, ID> {
    protected final JdbcTemplate jdbcTemplate;

    public BaseRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public abstract void save(T entity);

    public abstract Optional<T> findById(ID id);

    public abstract List<T> findAll();

    public abstract void deleteById(ID id);
}
