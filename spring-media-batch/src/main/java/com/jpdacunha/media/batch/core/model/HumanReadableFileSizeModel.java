package com.jpdacunha.media.batch.core.model;

import org.apache.commons.io.FileUtils;

public class HumanReadableFileSizeModel {
	
	private double sizeInBytes;
	
	public HumanReadableFileSizeModel(double sizeInBytes) {
		super();
		this.sizeInBytes = sizeInBytes;
	}

	public String toHumanReadable() {
		
		long converted = Double.valueOf(sizeInBytes).longValue();
		String formatedSize = FileUtils.byteCountToDisplaySize(converted);
		return formatedSize;
		
	}

}
