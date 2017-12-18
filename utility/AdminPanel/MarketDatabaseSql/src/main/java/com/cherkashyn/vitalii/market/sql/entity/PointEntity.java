package com.cherkashyn.vitalii.market.sql.entity;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.cherkashyn.vitalii.market.domain.Point;
import com.cherkashyn.vitalii.market.exception.StoreException;
import com.cherkashyn.vitalii.market.sql.utility.JdbcEntity;

@Component
@Scope(value=BeanDefinition.SCOPE_PROTOTYPE)
public class PointEntity extends JdbcEntity<PointEntity> implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private static enum Columns{
		id,
		pointnum,
		active,
		pos_x,
		pos_y,
		html;
	}
	
	public String getTableName(){
		return "points";
	}

	private Integer id;
	private Integer pointnum;
	private Integer active;
	private Integer pos_x;
	private Integer pos_y;
	private String html;
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPointnum() {
		return pointnum;
	}

	public void setPointnum(Integer pointnum) {
		this.pointnum = pointnum;
	}

	public Integer getActive() {
		return active;
	}

	public void setActive(Integer active) {
		this.active = active;
	}

	public Integer getPos_x() {
		return pos_x;
	}

	public void setPos_x(Integer pos_x) {
		this.pos_x = pos_x;
	}

	public Integer getPos_y() {
		return pos_y;
	}

	public void setPos_y(Integer pos_y) {
		this.pos_y = pos_y;
	}

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	@Override
	public void fillEntity(PointEntity commodity, ResultSet rs) throws SQLException{
		commodity.setId(rs.getInt(Columns.id.toString()));
		commodity.setActive(rs.getInt(Columns.active.toString()));
		commodity.setPointnum(rs.getInt(Columns.pointnum.toString()));
		commodity.setPos_x(rs.getInt(Columns.pos_x.toString()));
		commodity.setPos_y(rs.getInt(Columns.pos_y.toString()));
		commodity.setHtml(rs.getString(Columns.html.toString()));
	}

	@Override
	public PointEntity fillEntity(ResultSet source) throws SQLException {
		PointEntity returnValue=new PointEntity();
		this.fillEntity(returnValue, source);
		return returnValue;
	}
	
	public Point getPoint(){
		Point returnValue=new Point();
		returnValue.setId(this.id);
		returnValue.setActive(this.active);
		returnValue.setHtml(this.html);
		returnValue.setPointnum(this.pointnum);
		returnValue.setPositionX(this.pos_x);
		returnValue.setPositionY(this.pos_y);
		return returnValue;
	}
	
	/**
	 * fill entity from point value, load from store, if Point.Id is present 
	 * @param entity
	 * @param point
	 * @throws StoreException
	 */
	public static void fillEntity(PointEntity entity, Point point) throws StoreException {
		if(point.getId()!=null){
			//  prepare for update 
			if(!entity.loadById(point.getId())){
				// need to insert new value with existing id
				entity.setId(point.getId());
			}
		}
		entity.setActive(point.getActive());
		entity.setHtml(point.getHtml());
		entity.setPointnum(point.getPointnum());
		entity.setPos_x(point.getPositionX());
		entity.setPos_y(point.getPositionY());
	}
}
