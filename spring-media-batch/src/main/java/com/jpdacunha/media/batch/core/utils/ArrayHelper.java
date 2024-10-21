package com.jpdacunha.media.batch.core.utils;

public class ArrayHelper {
	
	private ArrayHelper() {
		super();
	}

	public static boolean differentLength(String[] array1, String[] array2, String[] array3) {
		
		if (array1 == null || array2 == null || array3 == null) {
			return true;
		}
		
		int array1Length = array1.length;
		int array2Length = array2.length;
		int array3Length = array3.length;
		
		int expectedLength = array1Length;
		
		return (array2Length != expectedLength || array3Length != expectedLength);
		
	}
	
	public static boolean sameLength(String [] array1, String [] array2, String [] array3) {
		
		return !differentLength(array1, array2, array3);
		
	}

}
