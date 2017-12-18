package com.cherkashyn.vitalii.market.sql.repository;

import java.sql.Connection;

import org.apache.commons.dbutils.DbUtils;

import com.cherkashyn.vitalii.market.exception.StoreException;
import com.cherkashyn.vitalii.market.sql.connector.Connector;

abstract class ConnectionLogicExecutor {
	private Connector connector;
	
	public ConnectionLogicExecutor(Connector connector) {
		this.connector=connector;
	}
	
	public void execute() throws StoreException{
		Connection connection=connector.getConnection();
		try{
			logic(connection);
		}finally{
			DbUtils.closeQuietly(connection);
		}
	}
	
	public abstract void logic(Connection connection) throws StoreException;

}
