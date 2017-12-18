package com.cherkashyn.vitalii.market.sql.repository;

import java.util.Random;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cherkashyn.vitalii.market.datasource.points.PointFinderNavigation;
import com.cherkashyn.vitalii.market.datasource.points.PointRepository;
import com.cherkashyn.vitalii.market.domain.Point;
import com.cherkashyn.vitalii.market.exception.StoreException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/context-sql-test.xml"})
public class PointRepositoryTest {

	@Autowired
	PointRepository repository;
	
	@Autowired
	PointFinderNavigation finder;

	@Test
	public void testCreateUpdateRemove() throws StoreException{
		// -- insert 
		// given 
		Point point=getPointForTest();
		// when 
		repository.insert(point);
		
		// then 
		Assert.assertNotNull(point.getId());
		
		
		// -- update 
		// given
		Integer controlPosition=(point.getPositionX()==null)?0:point.getPositionX() +10;
		point.setPositionX(  controlPosition  )  ;
		
		// when
		repository.update(point);
		Point controlPoint=finder.findById(point.getId());
		
		// then 
		Assert.assertNotNull(controlPoint);
		Assert.assertEquals(controlPosition, controlPoint.getPositionX());

		
		// -- remove
		// given 
		repository.remove(point);
		// then 
		Point removedPoint=finder.findById(point.getId());
		// then
		Assert.assertNull(removedPoint);
	}
	
	private Point getPointForTest(){
		Point point=new Point();
		point.setActive(1);
		point.setHtml("<html>  <body> <h1>test information</h1> </body>  </html>");
		point.setPointnum(new Random().nextInt(100)+1000);
		point.setPositionX(new Random().nextInt(100)+2000);
		point.setPositionY(new Random().nextInt(100)+3000);
		return point;
	}
	
	
}
