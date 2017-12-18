package com.cherkashyn.vitalii.market.sql.finder;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cherkashyn.vitalii.market.datasource.FilterElement;
import com.cherkashyn.vitalii.market.datasource.FilterOperation;
import com.cherkashyn.vitalii.market.datasource.commodity.CommodityFinderNavigation;
import com.cherkashyn.vitalii.market.domain.Commodity;
import com.cherkashyn.vitalii.market.exception.StoreException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/context-sql-test.xml"})
public class CommodityFinderNavigationTest implements ApplicationContextAware{

	@Autowired(required=true)
	CommodityFinderNavigation commodityFinder;
	@SuppressWarnings("unused")
	private ApplicationContext context;
	
	@Test
	public void testGetSize() throws StoreException{
		Assert.assertNotNull(commodityFinder);
		Assert.assertTrue(commodityFinder.size(null)>=0) ;
	}

	@Test
	public void testGetRecords() throws StoreException{
		Assert.assertNotNull(commodityFinder);
		Assert.assertNotNull(commodityFinder.find(null)) ;
		Assert.assertTrue(commodityFinder.find(null).length>0) ;
	}

	@Test
	public void testExistingRecordsAndFilter() throws StoreException{
		// given 
		Assert.assertNotNull(commodityFinder);
		// read all Commodities
		Commodity[] commodities=commodityFinder.find(null);
		Assert.assertNotNull(commodities) ;
		Assert.assertTrue(commodities.length>0);
		// get any Commodity 
		Commodity randomCommodity=commodities[commodities.length-1];
		Assert.assertNotNull(randomCommodity.getId());
		
		// when
		FilterElement condition=new FilterElement("id", FilterOperation.EQ, Integer.toString(randomCommodity.getId()));
		
		// then 
		Commodity[] filteredCommoditys=commodityFinder.find(new FilterElement[]{condition});
		Assert.assertTrue(filteredCommoditys.length==1);
		
		Commodity foundCommodity=filteredCommoditys[0];
		Assert.assertEquals(randomCommodity.getId(), foundCommodity.getId() );
		Assert.assertEquals(randomCommodity.getName(), foundCommodity.getName() );
		
	}

	@Override
	public void setApplicationContext(ApplicationContext context)
			throws BeansException {
		this.context=context;
	}
}
