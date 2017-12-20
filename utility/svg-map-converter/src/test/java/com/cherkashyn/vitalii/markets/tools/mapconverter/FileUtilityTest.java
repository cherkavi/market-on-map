package com.cherkashyn.vitalii.markets.tools.mapconverter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.List;

import junit.framework.Assert;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import org.junit.Test;

public class FileUtilityTest {

	@Test
	public void testCopy() throws IOException{
		// given
		String firstString="12345";
		String secondString="6789";
		ByteArrayOutputStream destinationOutput=new ByteArrayOutputStream();
		InputStream firstInputStream=new ByteArrayInputStream(firstString.getBytes());
		InputStream secondInputStream=new ByteArrayInputStream(secondString.getBytes());
		
		// when
		FileUtility.INSTANCE.copy(firstInputStream, destinationOutput);
		FileUtility.INSTANCE.copy(secondInputStream, destinationOutput);
		
		// then
		destinationOutput.close();
		String destinationString=new String(destinationOutput.toByteArray());
		Assert.assertEquals(firstString.length()+secondString.length(),destinationString.length());
		Assert.assertEquals(firstString+secondString,destinationString);
	}
	
	
	@Test
	public void testReplaceHeadTail() throws IOException{
		// given
		String head=" begin ";
		String tail=" end ";
		String pathData="\n<path> some data </path> \n <path> another data </path> \n";
		String rawData="<svg> <dest>some information </dest> "+pathData+"</svg>";
		ByteArrayOutputStream destination=new ByteArrayOutputStream();
		
		// when
		FileUtility.INSTANCE.replaceHeadTail(new ByteArrayInputStream(rawData.getBytes()), new ByteArrayInputStream(head.getBytes()), new ByteArrayInputStream(tail.getBytes()), destination);
		
		// then
		String result=new String(destination.toByteArray());
		Assert.assertNotNull(result);
		Assert.assertTrue(StringUtils.startsWith(result, head));
		Assert.assertTrue(StringUtils.endsWith(result, tail));
		Assert.assertTrue(StringUtils.contains(result, pathData));
		Assert.assertEquals(head+pathData+tail, result);
	}

	
	
	@Test
	public void testForExistingHeadTailIntoClasspath() throws IOException{
		// given
		
		// when
		InputStream headInputStream=this.getClass().getClassLoader().getResource(FileUtility.FILE_HEAD).openStream();
		InputStream tailInputStream=this.getClass().getClassLoader().getResource(FileUtility.FILE_TAIL).openStream();
		
		
		// then 
		Assert.assertNotNull(headInputStream);
		
		List<String> headLines=IOUtils.readLines(headInputStream);
		Assert.assertNotNull(headLines);
		Assert.assertTrue(headLines.size()>0);
		
		List<String> tailLines=IOUtils.readLines(tailInputStream);
		Assert.assertNotNull(tailLines);
		Assert.assertTrue(tailLines.size()>0);
	}
}
