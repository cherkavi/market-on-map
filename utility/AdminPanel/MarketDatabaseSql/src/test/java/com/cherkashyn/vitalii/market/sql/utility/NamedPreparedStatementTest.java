package com.cherkashyn.vitalii.market.sql.utility;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;

public class NamedPreparedStatementTest {

	@Test
	public void testParseAllNames(){
		String query="SELECT * FROM customers WHERE name = :name AND city = :city";
		NamedPreparedStatement statement=new NamedPreparedStatement(query);
		List<String> list=Arrays.asList(statement.parseAllNames(query));
		Assert.assertTrue(list.contains("name"));
		Assert.assertTrue(list.contains("city"));
		Assert.assertTrue(list.size()==2);
	}
	

	@Test
	public void testAnaliseList(){
		/** just fake statement */
		String query="SELECT * FROM customers";
		NamedPreparedStatement statement=new NamedPreparedStatement(query);
		String[] values=new String[]{"one", "two", "one", "four", "five_1","five_2", "five_1"};
		Map<String, List<Integer>> positions=statement.analiseList(values);
		
		Assert.assertTrue(positions.get("one").size()==2);
		Assert.assertTrue(positions.get("one").get(0)==1);
		Assert.assertTrue(positions.get("one").get(1)==3);
		
		
		Assert.assertTrue(positions.get("two").size()==1);
		Assert.assertTrue(positions.get("two").get(0)==2);
		
		Assert.assertTrue(positions.get("four").size()==1);
		Assert.assertTrue(positions.get("four").get(0)==4);
		
		Assert.assertTrue(positions.get("five_1").size()==2);
		Assert.assertTrue(positions.get("five_1").get(0)==5);
		Assert.assertTrue(positions.get("five_1").get(1)==7);

		Assert.assertTrue(positions.get("five_2").size()==1);
		Assert.assertTrue(positions.get("five_2").get(0)==6);
	}

	
	@Test
	public void testPreparedStatementQuery(){
		String query="SELECT * FROM customers WHERE name = :name AND city = :city AND zip_code= :zip_code";
		NamedPreparedStatement statement=new NamedPreparedStatement(query);
		String preparedQuery=statement.getPreparedStatementQuery();
		String controlString="SELECT * FROM customers WHERE name = ? AND city = ? AND zip_code= ?";
		// Assert.assertEquals((String)controlString, (String)preparedQuery);
		Assert.assertTrue(controlString.equals(preparedQuery));
	}
	
}
