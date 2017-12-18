package com.cherkashyn.vitalii.market.sql.repository;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.cherkashyn.vitalii.market.datasource.commodity.CommodityRepository;
import com.cherkashyn.vitalii.market.datasource.point2commodity.Point2CommodityFinder;
import com.cherkashyn.vitalii.market.domain.Commodity;
import com.cherkashyn.vitalii.market.domain.Point;
import com.cherkashyn.vitalii.market.exception.StoreException;
import com.cherkashyn.vitalii.market.sql.connector.Connector;
import com.cherkashyn.vitalii.market.sql.entity.CommodityEntity;
import com.cherkashyn.vitalii.market.sql.exception.DatabaseException;

@Component("Repository.Commodity")
@Scope("prototype")
public class CommodityRepositoryImpl implements CommodityRepository{
	@Autowired
	Connector connector;
	
	@Autowired
	ApplicationContext context;
	
	@Autowired
	Point2CommodityFinder finderPoint2commodity;

	@Override
	public void insert(Commodity value) throws StoreException {
		CommodityEntity entity=context.getBean(CommodityEntity.class);
		CommodityEntity.fillEntity(entity, value);
		entity.insert();
		value.setId(entity.getId());
	}

	@Override
	public void update(Commodity value) throws StoreException {
		CommodityEntity entity=context.getBean(CommodityEntity.class);
		CommodityEntity.fillEntity(entity, value);
		entity.update();	
	}

	@Override
	public void remove(Commodity value) throws StoreException {
		CommodityEntity entity=context.getBean(CommodityEntity.class);
		CommodityEntity.fillEntity(entity, value);
		Collection<Point> pointCollection=finderPoint2commodity.getPoints(entity.getCommodity());
		if(pointCollection!=null && pointCollection.size()>0 ){
			throw new DatabaseException("can't remove record when some point contain them: "+pointCollection);
		}
		entity.delete();
	}

}
