package com.cherkashyn.vitalii.market.sql.connector;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Test;

import com.cherkashyn.vitalii.market.sql.exception.DatabaseException;

public class ConnectorTest {
	@Test
	public void checkConnection() throws DatabaseException, SQLException{
		// given
		String driverClass="com.mysql.jdbc.Driver";
		String host="radio-rynok.kiev.ua";
		int port=3306;
		String databaseName="root";
		String user="root";
		String password="root";
		
		// when
		Connector connector=new Connector(driverClass, host, port, databaseName, user, password);
		Connection connection=connector.getConnection();
		
		// then 
		Assert.assertNotNull(connection);
		try{
			Assert.assertNotNull(connection.getMetaData());
		}catch(SQLException ex){
			throw new DatabaseException(ex);
		}
		
	}
}
