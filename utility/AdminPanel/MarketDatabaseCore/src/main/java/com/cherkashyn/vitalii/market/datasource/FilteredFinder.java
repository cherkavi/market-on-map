package com.cherkashyn.vitalii.market.datasource;

import com.cherkashyn.vitalii.market.exception.StoreException;

/**
 * root interface for find some value(s) of certain type 
 * @param <T>
 */
public interface FilteredFinder<T> extends Finder<T>{

	/**
	 * @param filters
	 * @return dozen of records
	 */
	public T[] find(FilterElement[] filters) throws StoreException;
	
}
