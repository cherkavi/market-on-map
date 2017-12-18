package com.cherkashyn.vitalii.market.sql.utility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * add ability for set parameters by name
 */
public class NamedPreparedStatement {
	private final Map<String, List<Integer>> positions;
	private final static String MARKER=":";
	private final String preparedStatementQuery;
	private PreparedStatement statement;
	
	public NamedPreparedStatement(final String namedQuery){
		// parse all names
		String[] names=parseAllNames(namedQuery);
		positions=analiseList(names);
		String query=namedQuery;
		for(String eachName: names){
			query=StringUtils.replace(query, MARKER+eachName, "?");
		}
		this.preparedStatementQuery=query;
	}

	/**
	 * call this for create Prepared statement and then {@link #setParameter}
	 * @param connection
	 * @throws SQLException
	 */
	public void openStatement(Connection connection) throws SQLException{
		this.closeStatement();
		this.statement=connection.prepareStatement(this.preparedStatementQuery);
	}
	
	/**
	 * set parameter by name to prepared statement 
	 * @param name
	 * @param value
	 * @throws SQLException
	 */
	public void setParameter(String name, Object value) throws SQLException{
		if(statement==null){
			throw new IllegalStateException("need to call #openStatement before execute current method ");
		}
		List<Integer> positions=this.positions.get(name);
		if(positions!=null){
			for(Integer index:positions){
				this.statement.setObject(index, value);
			}
		}
	}
	
	/**
	 * close statement 
	 */
	public void closeStatement(){
		DbUtils.closeQuietly(this.statement);
	}
	
	Map<String, List<Integer>> analiseList(String[] names) {
		HashMap<String,List<Integer>> returnValue=new HashMap<String,List<Integer>>();
		for(int index=0;index<names.length;index++){
			String nextKey=names[index];
			if(returnValue.containsKey(nextKey)){
				returnValue.get(nextKey).add(index+1);
			}else{
				List<Integer> list=new ArrayList<Integer>();
				list.add(index+1);
				returnValue.put(nextKey, list);
			}
		}
		return returnValue;
	}

	String[] parseAllNames(String namedQuery) {
		List<String> returnValue=new ArrayList<String>();
		int index=-1;
		while( (index=StringUtils.indexOf(namedQuery, MARKER, index)) >=0){
			index++;
			StringBuilder parameterNameBuilder=new StringBuilder();
			while(index<namedQuery.length()){
				char ch=namedQuery.charAt(index);
				if(CharUtils.isAsciiAlpha(ch) || CharUtils.isAsciiNumeric(ch) || ch=='_'){
					parameterNameBuilder.append(ch);
				}else{
					break;
				}
				index++;
			}
			returnValue.add(parameterNameBuilder.toString());
		}
		return returnValue.toArray(new String[returnValue.size()]);
	}

	String getPreparedStatementQuery(){
		return this.preparedStatementQuery;
	}
	
	Map<String, List<Integer>> getPositions(){
		return this.positions;
	}

	public int executeUpdate() throws SQLException {
		if(this.statement==null){
			throw new IllegalStateException("need to call #openStatement before execute current method ");
		}
		return this.statement.executeUpdate();
	}

	
}
