package org.flight.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;

@Component(value="mongoProviderFactory")
public class MongoProviderFactory implements FactoryBean<DB> {

	private List<ServerAddress> mongoServers;

	private String dbName;
	private String user;
	private String password;

	private Integer connectionsPerHost = 20;


	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@SuppressWarnings("deprecation")
	@Override
	public DB getObject()  {
		MongoClientOptions.Builder builder = new MongoClientOptions.Builder();
		MongoClientOptions options = builder.connectionsPerHost(
				connectionsPerHost).build();
		DB db = null;
		Mongo mongoClient = new MongoClient(mongoServers, options);
		db = mongoClient.getDB(dbName);
		if (user != null && password != null) {
			db.authenticate(user, password.toCharArray());
		}
		logger.debug("Mongo client working");

		return db;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public Class<?> getObjectType() {
		return DB.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	public List<ServerAddress> getMongoServers() {
		return mongoServers;
	}

	public void setMongoServers(List<ServerAddress> mongoServers) {
		this.mongoServers = mongoServers;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public Integer getConnectionsPerHost() {
		return connectionsPerHost;
	}

	public void setConnectionsPerHost(Integer connectionsPerHost) {
		this.connectionsPerHost = connectionsPerHost;
	}

}
