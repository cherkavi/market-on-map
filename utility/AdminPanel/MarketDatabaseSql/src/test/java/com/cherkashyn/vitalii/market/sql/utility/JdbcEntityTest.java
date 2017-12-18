package com.cherkashyn.vitalii.market.sql.utility;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.cherkashyn.vitalii.market.sql.exception.DatabaseException;


public class JdbcEntityTest {
	
	@Test
	public void setFieldByName() throws DatabaseException{
		TestEntity entity=new TestEntity();
		entity.position=1;
		entity.name="first";
		final Map<String, Object> parameters=new HashMap<String, Object>();
		entity.fillFieldsByName(new NamedPreparedStatement("test"){
			@Override
			public void setParameter(String name, Object value) throws SQLException {
				parameters.put(name, value);
			}
		}, Arrays.asList("name", "position"), entity);
		
		Assert.assertEquals(entity.name, parameters.get("name"));
		Assert.assertEquals(entity.position, parameters.get("position"));
	}
	
	
	@Test
	public void createInsertStatement(){
		TestEntity entity=new TestEntity();
		entity.id=1;
		entity.position=1;
		entity.name="first";
		String insertQuery=entity.createInsertQuery(Arrays.asList("position","name"));
		Assert.assertEquals("insert into table(position, name) values (:position, :name)", insertQuery);
	}

	@Test
	public void createUpdateStatement(){
		TestEntity entity=new TestEntity();
		entity.id=1;
		entity.position=1;
		entity.name="first";
		String updateQuery=entity.createUpdateQuery(Arrays.asList("position","name"));
		Assert.assertEquals("update table set position=:position, name=:name where id=:id", updateQuery);
	}

	@Test
	public void createDeleteStatement(){
		TestEntity entity=new TestEntity();
		entity.id=1;
		entity.position=1;
		entity.name="first";
		String deleteQuery=entity.createDeleteQuery();
		Assert.assertEquals("delete from table where id=:id", deleteQuery);
	}
	
	@Test
	public void getAllFields(){
		TestEntity entity=new TestEntity();
		entity.id=1;
		entity.position=1;
		entity.name="first";
		List<String> list=entity.getAllFields(entity);
		Assert.assertTrue(list.contains("id"));
		Assert.assertTrue(list.contains("position"));
		Assert.assertTrue(list.contains("name"));
	}
	
	
	private static class TestEntity extends JdbcEntity<TestEntity>{
		@SuppressWarnings("unused")
		private int id;
		private int position;
		private String name;
		@Override
		public String getTableName() {
			return "table";
		}
		@Override
		public void fillEntity(TestEntity value, ResultSet rs) {
		}
		
		@Override
		public TestEntity fillEntity(ResultSet source) throws SQLException {
			return null;
		}
	}
	
	
}
