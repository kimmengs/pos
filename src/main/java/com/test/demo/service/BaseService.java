package com.test.demo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;

public interface BaseService<T, D, ID extends Serializable> {
    T create(T entity);
    List<T> getAll();
    Page<D> getAllWithPaginationAndSearch(Pageable pageable, String content);

    T getById(ID id);
    T update(ID id, T entity);
    void delete(ID id);
}