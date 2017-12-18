package com.cherkashyn.vitalii.market.datasource;

import java.text.MessageFormat;

import org.apache.commons.lang3.ArrayUtils;

public enum FilterOperation {
	 EQ(" {0} = ''{1}'' ")

	 ,NE(" {0} <> ''{1}'' ")
	
	,GT(" {0} > {1} ")
	
	,GE(" {0} >= {1} ")
	
	,LT(" {0} < {1} ")
	
	,LE(" {0} <= {1} ")
	
	,LIKE(" {0} LIKE ''{1}'' ")
	
	,IN(" {0} IN ({1}) ")
	;
	
	final String pattern;
	FilterOperation(String pattern){
		this.pattern=pattern;
	}
	
	public String getPattern(){
		return this.pattern;
	}
	
	private final static String JOINER="\n AND ";
	
	public static String getWhereCondition(FilterElement[] elements){
		StringBuilder returnValue=new StringBuilder();
		if(!ArrayUtils.isEmpty(elements)){
			for(FilterElement eachElement:elements){
				if(returnValue.length()>0){
					returnValue.append(MessageFormat.format(eachElement.getOperation().getPattern(), eachElement.getFieldName(), eachElement.getValue())  );
					returnValue.append(JOINER);
				}else{
					returnValue.append(MessageFormat.format(eachElement.getOperation().getPattern(), eachElement.getFieldName(), eachElement.getValue())  );
				}
			}
		}
		return returnValue.toString();
	}
}
