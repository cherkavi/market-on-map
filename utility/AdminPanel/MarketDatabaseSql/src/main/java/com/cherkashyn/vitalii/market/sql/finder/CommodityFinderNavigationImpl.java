package com.cherkashyn.vitalii.market.sql.finder;

import java.sql.Connection;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.cherkashyn.vitalii.market.datasource.FilterElement;
import com.cherkashyn.vitalii.market.datasource.FilterOperation;
import com.cherkashyn.vitalii.market.domain.Commodity;
import com.cherkashyn.vitalii.market.exception.StoreException;
import com.cherkashyn.vitalii.market.sql.connector.Connector;
import com.cherkashyn.vitalii.market.sql.entity.CommodityEntity;
import com.cherkashyn.vitalii.market.sql.exception.DatabaseException;
import com.cherkashyn.vitalii.market.sql.utility.SqlFinderUtils;

import com.cherkashyn.vitalii.market.datasource.commodity.CommodityFinderNavigation;

@Component("Finder.Commodity")
public class CommodityFinderNavigationImpl implements CommodityFinderNavigation{

	@Autowired
	Connector connector;
	
	@Autowired
	ApplicationContext context;

	public Commodity findById(Integer value) throws StoreException{
		CommodityEntity entity=context.getBean(CommodityEntity.class);
		if(entity.loadById(value)){
			return entity.getCommodity();
		}else{
			return null;
		}
	}

	public Commodity[] find(FilterElement[] filters, int indexBegin, int count) throws DatabaseException {
		CommodityEntity entity=new CommodityEntity();
		Connection connection=connector.getConnection();
		try{
			List<CommodityEntity> result=
					SqlFinderUtils.getListOfRecords(entity, 
													entity.getTableName(), 
													FilterOperation.getWhereCondition(filters), 
													indexBegin, 
													count, 
													connection);
			Commodity[] returnValue=new Commodity[result.size()];
			int index=0;
			for(CommodityEntity eachElement:result){
				returnValue[index++]=eachElement.getCommodity();
			}
			return returnValue;
		}finally{
			DbUtils.closeQuietly(connection);
		}
	}

	public Commodity[] find(FilterElement[] filters) throws DatabaseException {
		return find(filters, 0,0);
	}

	public int size(FilterElement[] filters) throws StoreException {
		Connection connection=connector.getConnection();
		try{
			return SqlFinderUtils.getListSize(new CommodityEntity().getTableName(), connection);
		}finally{
			DbUtils.closeQuietly(connection);
		}
	}

}
