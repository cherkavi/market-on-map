package com.cherkashyn.vitalii.market.sql.utility;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.AbstractListHandler;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.cherkashyn.vitalii.market.sql.exception.DatabaseException;

public class SqlFinderUtils {
	
	private final static Logger logger=Logger.getLogger(SqlFinderUtils.class);
	
	public static <T extends EntityFiller<?>> List<T> getListOfRecords(final EntityFiller<T> filler, final String tableName, final String whereCondition, final long beginIndex, final long size, Connection connection) throws DatabaseException{
		return getListOfRecords(filler, tableName, whereCondition, null, beginIndex,size, connection);
	}

	public static <T extends EntityFiller<?>> List<T> getListOfRecords(final EntityFiller<T> filler, 
																	   final String tableName, 
																	   final String whereCondition, 
																	   final String orderColumn, 
																	   final long beginIndex, 
																	   final long size, 
																	   Connection connection) throws DatabaseException{
		final List<T> returnValue=new ArrayList<T>();
		StringBuilder query=new StringBuilder();
		Statement statement=null;
		ResultSet resultSet=null;
		try {
			query.append("select * from ").append(tableName);
			if(!StringUtils.isEmpty(whereCondition)){
				query.append(" where ");
				query.append(whereCondition);
			}
			if(orderColumn!=null){
				query.append(" order by ").append(orderColumn);
			}
			if(size>0){
				query.append(" limit ").append(beginIndex).append(", ").append(size);
			}
			statement=connection.createStatement();
			resultSet=statement.executeQuery(query.toString());
			while(resultSet.next()){
				returnValue.add(filler.fillEntity(resultSet));
			}
		} catch (SQLException e) {
			logger.error("can't execute query: "+query.toString(), e);
			throw new DatabaseException(e);
		}finally{
			DbUtils.closeQuietly(resultSet);
			DbUtils.closeQuietly(statement);
		}
		return returnValue;
	}

	public static int getListSize(String tableName, Connection connection) throws DatabaseException{
		ResultSet rs=null;
		try{
			rs=connection.createStatement().executeQuery("select count(*) from "+tableName);
			if(rs.next()){
				return rs.getInt(1);
			}else{
				return 0;
			}
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}finally{
			DbUtils.closeQuietly(rs);
		}
	}
}
