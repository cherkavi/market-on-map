package com.cherkashyn.vitalii.market.sql.finder;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.cherkashyn.vitalii.market.datasource.commodity.CommodityFinderNavigation;
import com.cherkashyn.vitalii.market.datasource.point2commodity.Point2CommodityFinder;
import com.cherkashyn.vitalii.market.datasource.points.PointFinderNavigation;
import com.cherkashyn.vitalii.market.domain.Commodity;
import com.cherkashyn.vitalii.market.domain.Point;
import com.cherkashyn.vitalii.market.domain.Point2Commodity;
import com.cherkashyn.vitalii.market.exception.StoreException;
import com.cherkashyn.vitalii.market.sql.connector.Connector;
import com.cherkashyn.vitalii.market.sql.entity.Point2CommodityEntity;
import com.cherkashyn.vitalii.market.sql.exception.DatabaseException;

@Component("Finder.Point2Commodity")
public class Point2CommodityFinderImpl implements Point2CommodityFinder{

	@Autowired
	Connector connector;
	
	@Autowired
	ApplicationContext context;
	
	@Autowired
	PointFinderNavigation finderPoint;
	
	@Autowired
	CommodityFinderNavigation finderCommodity;

	@Override
	public Point2Commodity findById(Integer value) throws StoreException {
		Point2CommodityEntity entity=context.getBean(Point2CommodityEntity.class);
		entity.loadById(value);
		return entity.getPoint2Commodity();
	}

	@Override
	public Collection<Point> getPoints(Commodity commodity) throws StoreException{
		List<Point> listOfPoint=new ArrayList<Point>();
		Connection connection=null;
		Statement statement=null;
		ResultSet resultSet=null;
		try{
			connection=this.connector.getConnection();
			statement=connection.createStatement();
			resultSet=statement.executeQuery("select idpoint from point2commodity where idcommodity="+commodity.getId());
			while(resultSet.next()){
				Point nextPoint=finderPoint.findById(resultSet.getInt(1));
				if(nextPoint!=null){
					listOfPoint.add(nextPoint);
				}
			}
		}catch(SQLException ex){
			throw new DatabaseException(ex);
		}finally{
			DbUtils.closeQuietly(resultSet);
			DbUtils.closeQuietly(statement);
			DbUtils.closeQuietly(connection);
		}
		return listOfPoint;
	}

	@Override
	public Collection<Commodity> getCommodity(Point point) throws StoreException{
		List<Commodity> listOfCommodity=new ArrayList<Commodity>();
		Connection connection=null;
		Statement statement=null;
		ResultSet resultSet=null;
		try{
			connection=this.connector.getConnection();
			statement=connection.createStatement();
			resultSet=statement.executeQuery("select idcommodity from point2commodity where idpoint="+point.getId());
			while(resultSet.next()){
				Commodity nextCommodity=finderCommodity.findById(resultSet.getInt(1));
				if(nextCommodity!=null){
					listOfCommodity.add(nextCommodity);
				}
			}
		}catch(SQLException ex){
			throw new DatabaseException(ex);
		}finally{
			DbUtils.closeQuietly(resultSet);
			DbUtils.closeQuietly(statement);
			DbUtils.closeQuietly(connection);
		}
		return listOfCommodity;
	}

	@Override
	public Point2Commodity find(Point point, Commodity commodity) throws StoreException {
		Integer idForLoad=null;
		Connection connection=null;
		Statement statement=null;
		ResultSet resultSet=null;
		try{
			connection=this.connector.getConnection();
			statement=connection.createStatement();
			resultSet=statement.executeQuery("select id from point2commodity where idpoint="+point.getId()+" and idcommodity="+commodity.getId());
			while(resultSet.next()){
				idForLoad=resultSet.getInt(1);
			}
		}catch(SQLException ex){
			throw new DatabaseException(ex);
		}finally{
			DbUtils.closeQuietly(resultSet);
			DbUtils.closeQuietly(statement);
			DbUtils.closeQuietly(connection);
		}
		return this.findById(idForLoad);
	}

	@Override
	public Collection<Point2Commodity> findByPoint(Point point) throws StoreException {
		Connection connection=null;
		Statement statement=null;
		ResultSet resultSet=null;
		List<Point2Commodity> returnValue=new ArrayList<Point2Commodity>(); 
		try{
			connection=this.connector.getConnection();
			statement=connection.createStatement();
			resultSet=statement.executeQuery("select id from point2commodity where idpoint="+point.getId());
			while(resultSet.next()){
				returnValue.add(this.findById(resultSet.getInt(1)));
			}
		}catch(SQLException ex){
			throw new DatabaseException(ex);
		}finally{
			DbUtils.closeQuietly(resultSet);
			DbUtils.closeQuietly(statement);
			DbUtils.closeQuietly(connection);
		}
		return returnValue;
	}

}
