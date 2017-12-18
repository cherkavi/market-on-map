package com.cherkashyn.vitalii.market.ui.point;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.cherkashyn.vitalii.market.datasource.FilterElement;
import com.cherkashyn.vitalii.market.datasource.points.PointFinderNavigation;
import com.cherkashyn.vitalii.market.domain.Point;
import com.cherkashyn.vitalii.market.exception.StoreException;
import com.cherkashyn.vitalii.market.ui.common_elements.RecordsNavigator;

@Component
@Scope("prototype")
public class PointNavigator implements RecordsNavigator{

	@Autowired
	PointFinderNavigation finder;
	
	private String[] columns=new String[]{"id","number"};
	
	@Override
	public String[] getColumns() {
		return columns;
	}

	@Override
	public int getRecordSize() throws StoreException {
		return finder.size(null);
	}

	@Override
	public List<String[]> getRecords(int start, int count) {
		Point[] points;
		try {
			points = this.finder.find(filterElements, start, count);
		} catch (StoreException e) {
			return Collections.emptyList();
		}
		List<String[]> returnValue=new ArrayList<String[]>(points.length);
		for(Point eachPoint:points){
			returnValue.add(convertPointToArray(eachPoint));
		}
		return returnValue;
	}

	private String[] convertPointToArray(Point point){
		String[] returnValue=new String[2];
		returnValue[0]=Integer.toString(point.getId());
		returnValue[1]=Integer.toString(point.getPointnum());
		return returnValue;
	}

	private FilterElement[] filterElements=null;
	
	@Override
	public void setFilter(FilterElement[] filters) throws StoreException {
		this.filterElements=filters;
	}
	
}
