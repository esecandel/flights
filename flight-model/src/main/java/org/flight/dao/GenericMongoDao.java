package org.flight.dao;

import java.util.List;

import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.jongo.Update;

import com.google.common.collect.Lists;
import com.mongodb.DB;
import com.mongodb.WriteResult;

public abstract class GenericMongoDao<T, K> implements GenericDao<T, K> {

	private MongoCollection mongoCollection;

	protected GenericMongoDao(DB db, String collectionName) {
		Jongo jongo = new Jongo(db);
		mongoCollection = jongo.getCollection(collectionName);
	}

	protected GenericMongoDao(MongoCollection mongoCollection) {
		this.mongoCollection = mongoCollection;
	}

	protected abstract String getKeyFieldName();

	protected abstract Class<T> getClassType();

	protected abstract T addMongoId(T object, String mongoId);

	@Override
	public List<T> findByElementIs(String nameElment, Object value) {
		return Lists.newArrayList(mongoCollection.find(
				"{" + nameElment + ":#}", value).as(getClassType()));
	}

	@Override
	public T findById(K key) {
		List<T> resultList = findByElementIs(getKeyFieldName(), key);
		if (resultList.isEmpty()) {
			return null;
		} else {
			return resultList.get(0);
		}
	}
	@Override
	public Integer update(T object, boolean upsert, String query,
			Object... queryParams) {
		Update update = mongoCollection.update(query, queryParams);
		if (upsert) {
			update.upsert();
		}
		WriteResult result = update.multi().with(object);
		return result.getN();
	}

	@Override
	public List<T> find(String query, Object... queryParams) {
		return Lists.newArrayList(this.mongoCollection.find(query, queryParams)
				.as(getClassType()));
	}

	@Override
	public void save(T object, String defaultKey) {
		addMongoId(object, defaultKey);
		mongoCollection.save(object);
	}

	@Override
	public void remove(String query, Object... params) {
		mongoCollection.remove(query, params);

	}

	@Override
	public void insert(List<T> list) {
		mongoCollection.insert(list.toArray());
	}
	protected void setMongoCollection(MongoCollection mongoCollection) {
		this.mongoCollection = mongoCollection;
	}
}
