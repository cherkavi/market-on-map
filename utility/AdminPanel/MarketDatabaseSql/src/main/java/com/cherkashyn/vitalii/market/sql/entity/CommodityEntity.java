package com.cherkashyn.vitalii.market.sql.entity;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.cherkashyn.vitalii.market.domain.Commodity;
import com.cherkashyn.vitalii.market.exception.StoreException;
import com.cherkashyn.vitalii.market.sql.utility.JdbcEntity;


@Component
@Scope(value=BeanDefinition.SCOPE_PROTOTYPE)
public class CommodityEntity extends JdbcEntity<CommodityEntity> implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private static enum Columns{
		id,
		name;
	}
	
	public String getTableName(){
		return "commodity";
	}

	private Integer id;
	private String name;
	
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	
	public String getName() {
		return name;
	}

	
	public void setName(String name) {
		this.name = name;
	}


	@Override
	public void fillEntity(CommodityEntity commodity, ResultSet rs) throws SQLException{
		commodity.setId(rs.getInt(Columns.id.toString()));
		commodity.setName(rs.getString(Columns.name.toString()));
	}

	@Override
	public CommodityEntity fillEntity(ResultSet source) throws SQLException {
		CommodityEntity returnValue=new CommodityEntity();
		this.fillEntity(returnValue, source);
		return returnValue;
	}
	
	public Commodity getCommodity(){
		Commodity returnValue=new Commodity();
		returnValue.setId(this.id);
		returnValue.setName(this.name);
		return returnValue;
	}
	
	/**
	 * fill entity from point value, load from store, if Point.Id is present 
	 * @param entity
	 * @param point
	 * @throws StoreException
	 */
	public static void fillEntity(CommodityEntity entity, Commodity point) throws StoreException {
		if(point.getId()!=null){
			//  prepare for update 
			if(!entity.loadById(point.getId())){
				// need to insert new value 
				entity.setId(point.getId());
			}
		}
		// need to be Unique
		entity.setName(StringUtils.trimToNull(point.getName()));
	}
	
}
