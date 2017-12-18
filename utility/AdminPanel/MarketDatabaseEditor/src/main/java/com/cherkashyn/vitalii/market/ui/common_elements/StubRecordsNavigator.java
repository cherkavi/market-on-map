package com.cherkashyn.vitalii.market.ui.common_elements;

import java.util.ArrayList;
import java.util.List;

import com.cherkashyn.vitalii.market.datasource.FilterElement;
import com.cherkashyn.vitalii.market.exception.StoreException;


public class StubRecordsNavigator implements RecordsNavigator{

	@Override
	public String[] getColumns() {
		return new String[]{"id","name"};
	}

	@Override
	public int getRecordSize() {
		return 25;
	}

	@Override
	public List<String[]> getRecords(int start, int count) {
		List<String[]> returnValue=new ArrayList<String[]>();
		returnValue.add(new String[]{"1", "one"});
		returnValue.add(new String[]{"2", "two"});
		returnValue.add(new String[]{"3", "three"});
		returnValue.add(new String[]{"4", "four"});
		returnValue.add(new String[]{"5", "five"});
		returnValue.add(new String[]{"6", "six"});
		returnValue.add(new String[]{"7", "seven"});
		returnValue.add(new String[]{"8", "eight"});
		returnValue.add(new String[]{"9", "nine"});
		returnValue.add(new String[]{"10", "ten"});
		
		returnValue.add(new String[]{"11", "one"});
		returnValue.add(new String[]{"12", "two"});
		returnValue.add(new String[]{"13", "three"});
		returnValue.add(new String[]{"14", "four"});
		returnValue.add(new String[]{"15", "five"});
		returnValue.add(new String[]{"16", "six"});
		returnValue.add(new String[]{"17", "seven"});
		returnValue.add(new String[]{"18", "eight"});
		returnValue.add(new String[]{"19", "nine"});
		returnValue.add(new String[]{"20", "ten"});

		returnValue.add(new String[]{"21", "one"});
		returnValue.add(new String[]{"22", "two"});
		returnValue.add(new String[]{"23", "three"});
		returnValue.add(new String[]{"24", "four"});
		returnValue.add(new String[]{"25", "five"});

		if(start>returnValue.size()){
			return new ArrayList<String[]>();
		}else{
			if(start+count>returnValue.size()){
				return returnValue.subList(start, returnValue.size());
			}else{
				return returnValue.subList(start, start+count);
			}
		}
		
	}

	@Override
	public void setFilter(FilterElement[] filters) throws StoreException {
		throw new IllegalStateException();
	}

}
