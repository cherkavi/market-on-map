package com.cherkashyn.vitalii.market.datasource;

import org.junit.Assert;
import org.junit.Test;

public class FilterOperationTest {

	@Test
	public void testOperationEQ(){
		String conditionEQ=FilterOperation.getWhereCondition(new FilterElement[]{new FilterElement("field",FilterOperation.EQ,"1")});
		Assert.assertEquals(" field = '1' ",conditionEQ);
	}
	
	@Test
	public void testOperationNE(){
		String conditionNE=FilterOperation.getWhereCondition(new FilterElement[]{new FilterElement("field",FilterOperation.NE,"1")});
		Assert.assertEquals(" field <> '1' ",conditionNE);
	}

	@Test
	public void testOperationLIKE(){
		String conditionNE=FilterOperation.getWhereCondition(new FilterElement[]{new FilterElement("field",FilterOperation.LIKE,"1")});
		Assert.assertEquals(" field LIKE '1' ",conditionNE);
	}
	
}
