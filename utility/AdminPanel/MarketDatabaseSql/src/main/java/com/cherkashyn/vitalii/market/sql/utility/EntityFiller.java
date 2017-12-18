package com.cherkashyn.vitalii.market.sql.utility;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface EntityFiller<T> {
	/**
	 * fill existing entity
	 * @param destination - entity for fill 
	 * @param source - result set 
	 * @throws SQLException
	 */
	void fillEntity(T destination, ResultSet source) throws SQLException;
	/**
	 * create Entity and fill it from ResultSet
	 * @param source
	 * @return
	 * @throws SQLException
	 */
	T fillEntity(ResultSet source) throws SQLException;
}
