package com.cherkashyn.vitalii.market.datasource;

import com.cherkashyn.vitalii.market.exception.StoreException;

/**
 * root interface for find some value(s) of certain type 
 * @param <T>
 */
public interface Finder<T> {

	/**
	 * @param filters  
	 * @return only one record
	 */
	public T findById(Integer value) throws StoreException;
	
	
}
