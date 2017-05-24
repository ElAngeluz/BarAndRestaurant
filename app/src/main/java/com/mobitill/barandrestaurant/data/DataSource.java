package com.mobitill.barandrestaurant.data;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by james on 4/27/2017.
 */

public interface DataSource<T, E> {

    Observable<List<T>>  getAll();
    Observable<T> getOne(E id);

    T save(T item);
    int delete(E id);
    int update(T item);
    void deleteAll();

}
