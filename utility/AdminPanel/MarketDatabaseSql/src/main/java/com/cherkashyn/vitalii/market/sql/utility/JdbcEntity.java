package com.cherkashyn.vitalii.market.sql.utility;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.cherkashyn.vitalii.market.exception.StoreException;
import com.cherkashyn.vitalii.market.sql.connector.Connector;
import com.cherkashyn.vitalii.market.sql.exception.DatabaseException;


public abstract class JdbcEntity<T> implements EntityFiller<T>, EntityTableNameAware, EntityIdNameAware{
	
	private final static String[] fieldsBlackList=new String[]{"serialVersionUID"};
	
	@Autowired(required=true)
	Connector connector;
	
	/**
	 * load T value from Database by unique Id
	 * @param id
	 * <b> Dedicated transaction </b>
	 * @throws DatabaseException
	 */
	public boolean loadById(int id) throws StoreException{
		Connection connection=null;
		try{
			connection=connector.getConnection();
			return loadById(connection, id);
		}finally{
			DbUtils.closeQuietly(connection);
		}
	}

	/**
	 * @param connection - certain connection for load
	 * @param id - unique id for load 
	 * @throws DatabaseException
	 * @return 
	 * <ul>
	 * 	<li><b>true</b> - record was loaded </li>
	 * 	<li><b>false</b> - record was NOT FOUND by id </li>
	 * </ul>
	 */
	@SuppressWarnings("unchecked")
	public boolean loadById(Connection connection, int id) throws StoreException{
		ResultSet rs=null;
		try{
			rs=connection.createStatement().executeQuery(getSqlFindById(id));
			if(rs.next()){
				fillEntity((T)JdbcEntity.this, rs);
				return true;
			}else{
				return false;
			}
		}catch(SQLException ex){
			throw new DatabaseException(ex);
		}finally{
			DbUtils.closeQuietly(rs);
		}
	}
	
	/**
	 * open Connection, insert value, close connection;
	 * <b> insert record into dedicated transaction </b>
	 * @throws StoreException
	 */
	public void insert() throws StoreException{
		Connection connection=null;
		try{
			connection=connector.getConnection();
			this.insert(connection);
			try {
				connection.commit();
			} catch (SQLException e) {
				throw new StoreException(e);
			}
		}finally{
			DbUtils.closeQuietly(connection);
		}
	}
	
	/**
	 * insert value ( over certain transaction )
	 * @param connection - SQL connection 
	 * @throws StoreException
	 */
	public void insert(Connection connection) throws StoreException{
		List<String> listOfFields=getAllFields(this);
		this.mySqlInsertStrategy(connection, listOfFields);
	}

	// create different realization for different Database 
	private void mySqlInsertStrategy(Connection connection, List<String> listOfFields) throws DatabaseException{
		// remove Id field value 
		listOfFields.remove(this.getIdFieldName());
		NamedPreparedStatement ps=new NamedPreparedStatement(createInsertQuery(listOfFields));
		try{
			ps.openStatement(connection);
		}catch(SQLException ex){
			throw new DatabaseException(ex);
		}
		fillFieldsByName(ps, listOfFields, this);
		try{
			ps.executeUpdate();
		}catch(SQLException ex){
			throw new DatabaseException(ex);
		}
		try {
			ResultSet rs=connection.createStatement().executeQuery("SELECT LAST_INSERT_ID()");
			if(rs.next()){
				this.setPrimaryKey(rs.getInt(1));
			}
			DbUtils.closeQuietly(rs);
		} catch (SQLException e) {
			throw new DatabaseException(e);
		} catch (SecurityException e) {
			throw new DatabaseException(e);
		} catch (NoSuchFieldException e) {
			throw new DatabaseException(e);
		} catch (IllegalArgumentException e) {
			throw new DatabaseException(e);
		} catch (IllegalAccessException e) {
			throw new DatabaseException(e);
		}
		ps.closeStatement();
	}

	/**
	 * set primary key (it's like "setId" without certain name of id)
	 * @param value - new integer value for primary key
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	protected void setPrimaryKey(Integer value) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException  {
		Field field = this.getClass().getDeclaredField(this.getIdFieldName());
		field.setAccessible(true);
		field.set(this, value);
	}

	/**
	 * 
	 * @return primary key ( it's like "getId" without certain name of Id )
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	protected Integer getPrimaryKey() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException  {
		Field field = this.getClass().getDeclaredField(this.getIdFieldName());
		field.setAccessible(true);
		return (Integer)field.get(this);
	}

	/**
	 * update all fields ( except primary key ) by primary key <br />
	 * <b> update in dedicated transaction </b>
	 * @throws StoreException
	 */
	public void update() throws StoreException{
		Connection connection=null;
		try{
			connection=connector.getConnection();
			this.update(connection);
			try {
				connection.commit();
			} catch (SQLException e) {
				throw new StoreException(e);
			}
		}finally{
			DbUtils.closeQuietly(connection);
		}
	}
	
	/**
	 * update record over certain connection 
	 * @param connection
	 */
	public void update(Connection connection) throws StoreException{
		List<String> listOfFields=getAllFields(this);
		NamedPreparedStatement ps=null;
		ps=new NamedPreparedStatement(createUpdateQuery(listOfFields));
		try{
			ps.openStatement(connection);
		}catch(SQLException ex){
			throw new DatabaseException(ex);
		 }
		fillFieldsByName(ps, listOfFields, this);
		try{
			ps.executeUpdate();
		}catch(SQLException ex){
			throw new DatabaseException(ex);
		}
		ps.closeStatement();
	}
	
	
	/**
	 * remove current record ( by id ) from database <br />
	 * <b> dedicated transaction </b>
	 * @throws StoreException
	 */
	public void delete() throws StoreException{
		Connection connection=null;
		try{
			connection=connector.getConnection();
			delete(connection);
			try {
				connection.commit();
			} catch (SQLException e) {
				throw new StoreException(e);
			}
		}finally{
			DbUtils.closeQuietly(connection);
		}
	}
	
	/**
	 * remove current record over certain transaction
	 * @param connection - connection with database 
	 */
	public void delete(Connection connection) throws StoreException{
		Integer id=null;
		try {
			id=this.getPrimaryKey();
		} catch (SecurityException e) {
			throw new StoreException(e);
		} catch (IllegalArgumentException e) {
			throw new StoreException(e);
		} catch (NoSuchFieldException e) {
			throw new StoreException(e);
		} catch (IllegalAccessException e) {
			throw new StoreException(e);
		}
		NamedPreparedStatement ps=new NamedPreparedStatement(createDeleteQuery());
		try{
			ps.openStatement(connection);
		}catch(SQLException ex){
			throw new DatabaseException(ex);
		}
		try {
			ps.setParameter(this.getIdFieldName(), id);
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
		try{
			ps.executeUpdate();
		}catch(SQLException ex){
			throw new DatabaseException(ex);
		}
		ps.closeStatement();
	}
	
	/** generate SQL query for remove one certain record */
	String createDeleteQuery() {
		StringBuilder returnValue=new StringBuilder();
		returnValue.append("delete from ").append(this.getTableName()).append(" where ").append(this.getIdFieldName()).append("=").append(":").append(this.getIdFieldName());
		return returnValue.toString();
	}

	String createUpdateQuery(List<String> listOfFields) {
		List<String> fieldList=new ArrayList<String>(listOfFields);
		fieldList.remove(this.getIdFieldName());
		// update commodity set name=1, description=1 where id=2;
		StringBuilder parameters=new StringBuilder();
		for(String eachField:listOfFields){
			if(parameters.length()>0){
				parameters.append(", ");
			}
			parameters.append(eachField).append("=").append(":").append(eachField);
		}
		StringBuilder query=new StringBuilder();
		query.append("update ").append(this.getTableName()).append(" set ");
		query.append(parameters);
		query.append(" where ").append(this.getIdFieldName()).append("=").append(":").append(this.getIdFieldName());
		return query.toString();
	}

	void fillFieldsByName(NamedPreparedStatement ps, List<String> listOfFields, Object objectWithFields) throws DatabaseException {
		Field[] fields=objectWithFields.getClass().getDeclaredFields();
		for(Field eachField:fields){
			if(listOfFields.contains(eachField.getName())){
				eachField.setAccessible(true);
				try{
					ps.setParameter(eachField.getName(), eachField.get(objectWithFields));
				}catch(SQLException ex){
					throw new DatabaseException(ex);
				}catch(IllegalAccessException ex){
					throw new DatabaseException(ex);
				}catch(IllegalArgumentException ex){
					throw new DatabaseException(ex);
				}
			}
		}
	}

	/**
	 * create String like: "insert into table(name) values(:name)" 
	 * @param listOfFields
	 * @return
	 */
	String createInsertQuery(List<String> listOfFields) {
		StringBuilder returnValue=new StringBuilder();

		StringBuilder fieldsList=new StringBuilder();
		StringBuilder parametersList=new StringBuilder();
		for(String eachField: listOfFields){
			if(fieldsList.length()>0){
				fieldsList.append(", ");
				parametersList.append(", ");
			}
			fieldsList.append(eachField);
			parametersList.append(":"+eachField);
		}

		returnValue.append("insert into ").append(this.getTableName()).append("(");
		returnValue.append(fieldsList);
		returnValue.append(")");
		returnValue.append(" values ");
		returnValue.append("(");
		returnValue.append(parametersList); 
		returnValue.append(")");
		return returnValue.toString();
	}

	List<String> getAllFields(Object jdbcEntity) {
		Field[] fields=jdbcEntity.getClass().getDeclaredFields();
		List<String> returnValue=new ArrayList<String>();
		for(Field eachField:fields){
			String fieldName=eachField.getName();
			if(isWhiteListField(fieldName)){
				returnValue.add(eachField.getName());
			}
		}
		return returnValue;
	}

	
	
	private boolean isWhiteListField(String fieldName) {
		return !ArrayUtils.contains(fieldsBlackList, fieldName);
	}


	public abstract String getTableName(); 
	
	
	protected String getSqlFindById(Integer id){
		StringBuilder query=new StringBuilder();
		query.append("select * from ");
		query.append(this.getTableName());
		query.append(" where ");
		query.append(getIdFieldName());
		query.append("=");
		query.append(id);
		return query.toString();
	}
	
	private final static String ID_FIELD_NAME="id";
	
	public String getIdFieldName(){
		return ID_FIELD_NAME;
	}
	
}
