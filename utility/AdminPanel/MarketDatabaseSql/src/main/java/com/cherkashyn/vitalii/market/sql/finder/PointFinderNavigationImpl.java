package com.cherkashyn.vitalii.market.sql.finder;

import java.sql.Connection;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.cherkashyn.vitalii.market.datasource.FilterElement;
import com.cherkashyn.vitalii.market.datasource.FilterOperation;
import com.cherkashyn.vitalii.market.datasource.points.PointFinderNavigation;
import com.cherkashyn.vitalii.market.domain.Point;
import com.cherkashyn.vitalii.market.exception.StoreException;
import com.cherkashyn.vitalii.market.sql.connector.Connector;
import com.cherkashyn.vitalii.market.sql.entity.PointEntity;
import com.cherkashyn.vitalii.market.sql.exception.DatabaseException;
import com.cherkashyn.vitalii.market.sql.utility.SqlFinderUtils;

@Component("Finder.Point")
public class PointFinderNavigationImpl implements PointFinderNavigation{

	@Autowired
	Connector connector;
	
	@Autowired
	ApplicationContext context;

	@Override
	public Point findById(Integer value) throws StoreException{
		PointEntity entity=context.getBean(PointEntity.class);
		if(entity.loadById(value)){
			return entity.getPoint();
		}else{
			return null;
		}
	}

	@Override
	public Point[] find(FilterElement[] filters, int indexBegin, int count) throws DatabaseException {
		PointEntity entity=new PointEntity();
		Connection connection=connector.getConnection();
		try{
			List<PointEntity> result=
					SqlFinderUtils.getListOfRecords(entity, 
													entity.getTableName(), 
													FilterOperation.getWhereCondition(filters), 
													indexBegin, 
													count, 
													connection);
			Point[] returnValue=new Point[result.size()];
			int index=0;
			for(PointEntity eachElement:result){
				returnValue[index++]=eachElement.getPoint();
			}
			return returnValue;
		}finally{
			DbUtils.closeQuietly(connection);
		}
	}

	@Override
	public Point[] find(FilterElement[] filters) throws DatabaseException {
		return find(filters, 0,0);
	}

	@Override
	public int size(FilterElement[] filters) throws StoreException {
		Connection connection=connector.getConnection();
		try{
			return SqlFinderUtils.getListSize(new PointEntity().getTableName(), connection);
		}finally{
			DbUtils.closeQuietly(connection);
		}
	}

}
