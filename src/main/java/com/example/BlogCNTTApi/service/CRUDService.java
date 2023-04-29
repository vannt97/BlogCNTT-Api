package com.example.BlogCNTTApi.service;

import java.util.List;

public interface CRUDService<T,E> {
    void create(E param);

    void edit(long id, E param);

    void delete(long id);

    List<T> datas();

    T data(long id);
}
