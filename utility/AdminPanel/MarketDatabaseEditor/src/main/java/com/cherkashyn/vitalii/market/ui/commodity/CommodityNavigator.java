package com.cherkashyn.vitalii.market.ui.commodity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.cherkashyn.vitalii.market.datasource.FilterElement;
import com.cherkashyn.vitalii.market.datasource.commodity.CommodityFinderNavigation;
import com.cherkashyn.vitalii.market.domain.Commodity;
import com.cherkashyn.vitalii.market.exception.StoreException;
import com.cherkashyn.vitalii.market.ui.common_elements.RecordsNavigator;

@Component
@Scope("prototype")
public class CommodityNavigator implements RecordsNavigator{

	@Autowired
	CommodityFinderNavigation finder;

	private String[] columns=new String[]{"id","name"};

	private FilterElement[] filterElements;
	
	@Override
	public String[] getColumns() {
		return columns;
	}

	@Override
	public int getRecordSize() throws StoreException {
		return finder.size(null);
	}

	@Override
	public List<String[]> getRecords(int start, int count)
			throws StoreException {
		Commodity[] points;
		try {
			points = this.finder.find(this.filterElements, start, count);
		} catch (StoreException e) {
			return Collections.emptyList();
		}
		List<String[]> returnValue=new ArrayList<String[]>(points.length);
		for(Commodity eachPoint:points){
			returnValue.add(convertCommodityToArray(eachPoint));
		}
		return returnValue;
	}

	private String[] convertCommodityToArray(Commodity point){
		String[] returnValue=new String[2];
		returnValue[0]=Integer.toString(point.getId());
		returnValue[1]=point.getName();
		return returnValue;
	}

	@Override
	public void setFilter(FilterElement[] filters){
		this.filterElements=filters;
	}
	
}
