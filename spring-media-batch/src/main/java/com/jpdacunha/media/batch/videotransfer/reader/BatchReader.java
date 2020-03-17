package com.jpdacunha.media.batch.videotransfer.reader;

import java.io.File;

import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.core.io.Resource;

public class BatchReader extends MultiResourceItemReader<File> {
	
	public BatchReader() {
		this.setResources(getInputResources());
	}

	private Resource[] getInputResources() {
		return null;
	}

}
