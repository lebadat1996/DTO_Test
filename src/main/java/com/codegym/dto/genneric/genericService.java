package com.codegym.dto.genneric;

import com.codegym.dto.entity.User;

import java.util.List;
import java.util.Optional;

public interface genericService<T> {
    List<T> getAll();

    Optional<T> get(Long id);

    T create (T model);

    T update(T t);

    void delete(Long id);
}
