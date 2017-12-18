package com.cherkashyn.vitalii.market.sql.repository;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.cherkashyn.vitalii.market.datasource.point2commodity.Point2CommodityFinder;
import com.cherkashyn.vitalii.market.datasource.point2commodity.Point2CommodityRepository;
import com.cherkashyn.vitalii.market.datasource.points.PointFinderNavigation;
import com.cherkashyn.vitalii.market.domain.Commodity;
import com.cherkashyn.vitalii.market.domain.Point;
import com.cherkashyn.vitalii.market.domain.Point2Commodity;
import com.cherkashyn.vitalii.market.exception.StoreException;
import com.cherkashyn.vitalii.market.sql.connector.Connector;
import com.cherkashyn.vitalii.market.sql.entity.Point2CommodityEntity;

@Component("Repository.Point2Commodity")
@Scope("prototype")
public class Point2CommodityRepositoryImpl implements Point2CommodityRepository {

	@Autowired
	Connector connector;
	
	@Autowired
	ApplicationContext context;
	
	@Autowired
	Point2CommodityFinder finder;
	
	@Autowired
	PointFinderNavigation finderPoint;
	
	@Override
	public void insert(Point2Commodity value) throws StoreException {
		checkRecord(value);
		Point2CommodityEntity entity=context.getBean(Point2CommodityEntity.class);
		Point2CommodityEntity.fillEntity(entity, value);
		checkRepeatCommodityForPoint(value);
		entity.insert();
		value.setId(entity.getId());
	}

	@Override
	public void update(Point2Commodity value) throws StoreException {
		checkRecord(value);
		Point2CommodityEntity entity=context.getBean(Point2CommodityEntity.class);
		Point2CommodityEntity.fillEntity(entity, value);
		this.checkRepeatCommodityForPoint(value);
		entity.update();
	}

	@Override
	public void remove(Point2Commodity value) throws StoreException {
		if(value!=null){
			checkRecord(value);
			Point2CommodityEntity entity=context.getBean(Point2CommodityEntity.class);
			Point2CommodityEntity.fillEntity(entity, value);
			entity.delete();
		}
	}

	private void checkRecord(Point2Commodity value) throws StoreException{
		if(value==null){
			throw new StoreException(" value is null ");
		}
		if(value.getIdCommodity()==0){
			throw new StoreException(" commodity is undefined ");
		}
		if(value.getIdCommodity()==0){
			throw new StoreException(" commodity is undefined ");
		}
		
	}

	/**
	 * @throws StoreException - when commodity already exists for current point 
	 */
	private void checkRepeatCommodityForPoint(Point2Commodity value) throws StoreException{
		Point point=finderPoint.findById(value.getIdPoint());
		Collection<Commodity> commodities=this.finder.getCommodity(point);
		for(Commodity eachCommodity:commodities){
			if(value.getIdCommodity()==eachCommodity.getId()){
				throw new StoreException("already contains ");
			}
		}
	}

}
