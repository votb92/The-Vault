package com.dao;

import java.util.ArrayList;


public interface Dao<T> {
    
    ArrayList<T> findAll();
    
    T save(T t);
    
    void update(T t);
    
    void delete(T t);
    
    void delete(int id);

	T findById(int id);
}