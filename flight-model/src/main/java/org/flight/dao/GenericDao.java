package org.flight.dao;

import java.util.List;

public interface GenericDao<T, K> {

	List<T> findByElementIs(String nameElment, Object value);

	T findById(K key);

	void save(T object, String defaultKey);

	Integer update(T object, boolean upsert, String query, Object[] queryParams);

	List<T> find(String query, Object[] queryParams);

	void remove(String query, Object[] params);

	void insert(List<T> list);

}
