package com.cherkashyn.vitalii.market.ui.common_elements;

import java.util.List;

import com.cherkashyn.vitalii.market.datasource.FilterElement;
import com.cherkashyn.vitalii.market.exception.StoreException;

public interface RecordsNavigator {

	/** list of columns for display */
	public String[] getColumns();

	/** get records count */
	public int getRecordSize() throws StoreException;
	
	/**
	 * @param filter
	 * <ul>
	 * 	<li><b>null</b> - skip filter </li>
	 * 	<li><b>empty </b> - skip filter </li>
	 * 	<li><b> pair key-value </b>
	 * </ul>
	 * @throws StoreException
	 */
	public void setFilter(FilterElement[] filters) throws StoreException;
	
	/** get records from certain range */
	public List<String[]> getRecords(int start, int count) throws StoreException;
}
