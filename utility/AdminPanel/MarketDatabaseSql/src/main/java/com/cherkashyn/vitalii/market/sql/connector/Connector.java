package com.cherkashyn.vitalii.market.sql.connector;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.dbcp.BasicDataSource;

import com.cherkashyn.vitalii.market.sql.exception.DatabaseException;


public class Connector {
	
	private BasicDataSource dataSource;
	private Map<String, MakeUrlStrategy> strategyMap=new HashMap<String, MakeUrlStrategy>();
	{
		strategyMap.put("com.mysql.jdbc.Driver", new MySqlStrategy());
	}
	
	
	private String driverClassName="com.mysql.jdbc.Driver";
	private String userName="root";
	private String password="root";
	private String host="localhost";
	private int port=3306;
	private String dataBaseName="market";

	
	/**
	 */
	public Connector() {
	}
	
	/**
	 * @param driverClassName
	 * @param host
	 * @param port
	 * @param databaseName
	 * @param user
	 * @param password
	 */
	public Connector(String driverClassName, String host, int port, String databaseName, String user, String password) {
		this.driverClassName=driverClassName;
		this.userName=user;
		this.host=host;
		this.port=port;
		this.password=password;
		this.dataBaseName=databaseName;
	}
	
	
	/**
	 * @return connection with DataBase 
	 * @throws SQLException
	 * <br />
	 * <b> IMPORTANT - Need to close connection </b>
	 */
	public synchronized final Connection getConnection() throws DatabaseException{
		if(dataSource==null){
			dataSource=new BasicDataSource();
			dataSource.setDriverClassName(driverClassName);
			dataSource.setUsername(userName);
			dataSource.setPassword(password);
			dataSource.setInitialSize(10);
			dataSource.setDefaultAutoCommit(false);
			String jdbcUrl=strategyMap.get(driverClassName).getUrl(host, port, dataBaseName);
			dataSource.setUrl(  jdbcUrl);
		}
		try{
			Connection returnValue=dataSource.getConnection();
			returnValue.setAutoCommit(false);
			return returnValue;
		}catch(NullPointerException ex){
			throw new DatabaseException("check init DataSource ");
		}catch(SQLException ex){
			throw new DatabaseException(" exception was happen during retrieve connection from pool ", ex);
		}
	}

	
	/**
	 * root interface for JDBC Url create strategy 
	 */
	private static interface MakeUrlStrategy{
		String getUrl(String host, int port, String dataBaseName);
	}
	
	/**
	 * root interface for JDBC Url create strategy 
	 */
	private static class MySqlStrategy implements MakeUrlStrategy{
		@Override
		public String getUrl(String host, int port, String dataBaseName) {
			return MessageFormat.format("jdbc:mysql://{0}:{1, number,#}/{2}?useUnicode=yes&characterEncoding=UTF-8", host, port, dataBaseName);
		}
	}
	
}
