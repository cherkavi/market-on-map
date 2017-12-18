package com.cherkashyn.vitalii.market.datasource;

import com.cherkashyn.vitalii.market.exception.StoreException;

/**
 * save/update operation(s) with Entity
 * @param <T>
 */
public interface Repository<T> {
	/** create new record into Sotre, object was changed - ID will be set after save */
	void insert(T value) throws StoreException;
	/** update existing record  */
	void update(T value) throws StoreException;
	/** remove record from store */
	void remove(T value) throws StoreException;
}
