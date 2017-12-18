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
import com.cherkashyn.vitalii.market.datasource.points.PointFinderNavigation;
import com.cherkashyn.vitalii.market.domain.Point;
import com.cherkashyn.vitalii.market.exception.StoreException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/context-sql-test.xml"})
public class PointFinderNavigationTest implements ApplicationContextAware{

	@Autowired(required=true)
	PointFinderNavigation pointFinder;
	@SuppressWarnings("unused")
	private ApplicationContext context;
	
	@Test
	public void testGetSize() throws StoreException{
		Assert.assertNotNull(pointFinder);
		Assert.assertTrue(pointFinder.size(null)>=0) ;
	}

	@Test
	public void testGetRecords() throws StoreException{
		Assert.assertNotNull(pointFinder);
		Assert.assertNotNull(pointFinder.find(null)) ;
		Assert.assertTrue(pointFinder.find(null).length>0) ;
	}

	@Test
	public void testExistingRecordsAndFilter() throws StoreException{
		// given 
		Assert.assertNotNull(pointFinder);
		// read all points
		Point[] points=pointFinder.find(null);
		Assert.assertNotNull(points) ;
		Assert.assertTrue(points.length>0);
		// get any Point 
		Point randomPoint=points[points.length-1];
		Assert.assertNotNull(randomPoint.getId());
		
		// when
		FilterElement condition=new FilterElement("id", FilterOperation.EQ, Integer.toString(randomPoint.getId()));
		
		// then 
		Point[] filteredPoints=pointFinder.find(new FilterElement[]{condition});
		Assert.assertTrue(filteredPoints.length==1);
		
		Point foundPoint=filteredPoints[0];
		Assert.assertEquals(randomPoint.getId(), foundPoint.getId() );
		Assert.assertEquals(randomPoint.getPointnum(), foundPoint.getPointnum() );
		Assert.assertEquals(randomPoint.getPositionX(), foundPoint.getPositionX() );
		Assert.assertEquals(randomPoint.getPositionY(), foundPoint.getPositionY() );
	}

	@Override
	public void setApplicationContext(ApplicationContext context)
			throws BeansException {
		this.context=context;
	}
}
