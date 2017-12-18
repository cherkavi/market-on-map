package com.cherkashyn.vitalii.market.datasource;

import com.cherkashyn.vitalii.market.exception.StoreException;

public interface FinderNavigation<T> extends FilteredFinder<T> {
	/**
	 * @param filters  
	 * @param indexBegin - index of begin record 
	 * @param count - how many elements need to return 
	 * @return count records by filter ( or less, if they were not found )
	 */
	public T[] find(FilterElement[] filters, int indexBegin, int count ) throws StoreException;

	/**
	 * @param filters
	 * @return count of record by filter
	 */
	public int size(FilterElement[] filters) throws StoreException;
}
