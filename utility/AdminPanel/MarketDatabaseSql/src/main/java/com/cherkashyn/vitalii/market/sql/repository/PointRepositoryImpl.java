package com.cherkashyn.vitalii.market.sql.repository;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.cherkashyn.vitalii.market.datasource.point2commodity.Point2CommodityFinder;
import com.cherkashyn.vitalii.market.datasource.point2commodity.Point2CommodityRepository;
import com.cherkashyn.vitalii.market.datasource.points.PointRepository;
import com.cherkashyn.vitalii.market.domain.Point;
import com.cherkashyn.vitalii.market.domain.Point2Commodity;
import com.cherkashyn.vitalii.market.exception.StoreException;
import com.cherkashyn.vitalii.market.sql.connector.Connector;
import com.cherkashyn.vitalii.market.sql.entity.PointEntity;

@Component("Repository.Point")
@Scope("prototype")
public class PointRepositoryImpl implements PointRepository{
	@Autowired
	Connector connector;
	
	@Autowired
	ApplicationContext context;
	
	@Autowired
	Point2CommodityRepository linkRepository;
	
	@Autowired
	Point2CommodityFinder linkFinder;
	
	@Override
	public void insert(final Point value) throws StoreException {
		PointEntity entity=context.getBean(PointEntity.class);
		PointEntity.fillEntity(entity, value);
		entity.insert();
		value.setId(entity.getId());
	}

	@Override
	public void update(final Point value) throws StoreException {
		PointEntity entity=context.getBean(PointEntity.class);
		PointEntity.fillEntity(entity, value);
		entity.update();
	}

	@Override
	public void remove(final Point value) throws StoreException {
		PointEntity entity=context.getBean(PointEntity.class);
		PointEntity.fillEntity(entity, value);
		// find all links
		Collection<Point2Commodity> links=linkFinder.findByPoint(value);
		entity.delete();
		// remove all links
		for(Point2Commodity link:links){
			this.linkRepository.remove(link);
		}
	}

}
