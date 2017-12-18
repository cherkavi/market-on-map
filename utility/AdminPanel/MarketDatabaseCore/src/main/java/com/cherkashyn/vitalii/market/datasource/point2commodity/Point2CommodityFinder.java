package com.cherkashyn.vitalii.market.datasource.point2commodity;

import java.util.Collection;

import com.cherkashyn.vitalii.market.datasource.Finder;
import com.cherkashyn.vitalii.market.domain.Commodity;
import com.cherkashyn.vitalii.market.domain.Point;
import com.cherkashyn.vitalii.market.domain.Point2Commodity;
import com.cherkashyn.vitalii.market.exception.StoreException;

public interface Point2CommodityFinder extends Finder<Point2Commodity>{
	/**
	 * find all points which contain commodity
	 * @param commodity
	 * @return
	 * @throws DatabaseException 
	 */
	Collection<Point> getPoints(Commodity commodity) throws StoreException;
	
	/**
	 * find all commodity by certain point  
	 * @param point
	 * @return
	 * @throws StoreException 
	 */
	Collection<Commodity> getCommodity(Point point) throws StoreException;

	/**
	 * find element by point and commodity 
	 * @param point
	 * @param commodityForRemove
	 * @return
	 * <ul>
	 * 	<li><b>null</b> - was not found </li>
	 * 	<li><b>{@link Point2Commodity}</b> - element with id of point and id of commodity</li>
	 * </ul>
	 * @throws StoreException 
	 */
	Point2Commodity find(Point point, Commodity commodityForRemove) throws StoreException;
	
	
	/**
	 * find all {@link Point2Commodity} elements by {@link Point} 
	 * @param point - point for find 
	 * @return
	 * @throws StoreException 
	 */
	Collection<Point2Commodity> findByPoint(Point point) throws StoreException;
}
