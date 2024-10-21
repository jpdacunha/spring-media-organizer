package com.jpdacunha.media.batch.core.arrays.test;

import org.junit.Assert;
import org.junit.Test;

import com.jpdacunha.media.batch.core.utils.ArrayHelper;

public class ArraysTest {

	@Test()
	public void nominal() {	
		
		String[] array1 = {"1", "2", "3"};
		String[] array2 = {"1", "2", "3"};
		String[] array3 = {"1", "2", "3"};
		
		boolean result = ArrayHelper.sameLength(array1, array2, array3);
		
		Assert.assertTrue(result);

	}
	
	@Test()
	public void error1() {	
		
		String[] array1 = {"1", "2"};
		String[] array2 = {"1", "2", "3"};
		String[] array3 = {"1", "2", "3"};
		
		boolean result = ArrayHelper.differentLength(array1, array2, array3);
		
		Assert.assertTrue(result);

	}
	
	@Test()
	public void error2() {	
		
		String[] array1 = {"1", "2", "3"};
		String[] array2 = {"1", "2"};
		String[] array3 = {"1", "2", "3"};
		
		boolean result = ArrayHelper.differentLength(array1, array2, array3);
		
		Assert.assertTrue(result);

	}
	
	@Test()
	public void error22() {	
		
		String[] array1 = {"1", "2", "3"};
		String[] array2 = {"1", "2", "3"};
		String[] array3 = {"1", "2"};
		
		boolean result = ArrayHelper.differentLength(array1, array2, array3);
		
		Assert.assertTrue(result);

	}
	
	@Test()
	public void error3() {	
		
		String[] array1 = {};
		String[] array2 = {};
		String[] array3 = {};
		
		boolean result = ArrayHelper.sameLength(array1, array2, array3);
		
		Assert.assertTrue(result);

	}
	
	@Test()
	public void error4() {	
		
		String[] array1 = null;
		String[] array2 = {};
		String[] array3 = {};
		
		boolean result = ArrayHelper.differentLength(array1, array2, array3);
		
		Assert.assertTrue(result);

	}
	

}
