package com.cherkashyn.vitalii.market.sql.entity;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.cherkashyn.vitalii.market.domain.Point2Commodity;
import com.cherkashyn.vitalii.market.exception.StoreException;
import com.cherkashyn.vitalii.market.sql.utility.JdbcEntity;


@Component
@Scope(value=BeanDefinition.SCOPE_PROTOTYPE)
public class Point2CommodityEntity extends JdbcEntity<Point2CommodityEntity> implements Serializable{

	private static final long serialVersionUID = 1L;


	private static enum Columns{
		id,
		idpoint,
		idcommodity,
		percent;
	}
	
	private int id;
	private int idpoint;
	private int idcommodity;
	private Float percent;
	
	@Override
	public void fillEntity(Point2CommodityEntity destination, ResultSet source)
			throws SQLException {
		destination.setId(source.getInt(Columns.id.toString()));
		destination.setIdpoint(source.getInt(Columns.idpoint.toString()));
		destination.setIdcommodity(source.getInt(Columns.idcommodity.toString()));
		
		destination.setPercent(source.getFloat(Columns.percent.toString()));
		if(source.wasNull()){
			destination.setPercent(null);
		}
	}
	

	@Override
	public Point2CommodityEntity fillEntity(ResultSet source)
			throws SQLException {
		Point2CommodityEntity returnValue=new Point2CommodityEntity();
		this.fillEntity(returnValue, source);
		return returnValue;
	}
	

	@Override
	public String getTableName() {
		return "point2commodity";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdpoint() {
		return idpoint;
	}

	public void setIdpoint(int idpoint) {
		this.idpoint = idpoint;
	}

	public int getIdcommodity() {
		return idcommodity;
	}

	public void setIdcommodity(int idcommodity) {
		this.idcommodity = idcommodity;
	}

	public Float getPercent() {
		return percent;
	}

	public void setPercent(Float percent) {
		this.percent = percent;
	}

	
	public static void fillEntity(Point2CommodityEntity entity, Point2Commodity link) throws StoreException{
		if(link.getId()!=null){
			// load by id
			if(!entity.loadById(link.getId())){
				// need to insert new value with existing id 
				entity.setId(link.getId());
			}
		}
		entity.setIdcommodity(link.getIdCommodity());
		entity.setIdpoint(link.getIdPoint());
		entity.setPercent(link.getPercent());
	}


	public Point2Commodity getPoint2Commodity() {
		Point2Commodity returnValue=new Point2Commodity();
		returnValue.setId(this.getId());
		returnValue.setIdCommodity(this.getIdcommodity());
		returnValue.setIdPoint(this.getIdpoint());
		returnValue.setPercent(this.getPercent());
		return returnValue;
	}
	

}
