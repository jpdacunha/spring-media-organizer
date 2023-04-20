package com.jpdacunha.media.batch.removeduplicatesfotos.filter.impl;

import java.io.File;
import java.io.FileFilter;

import org.apache.commons.io.filefilter.AbstractFileFilter;

import com.jpdacunha.media.batch.core.utils.FileSystemUtils;
import com.jpdacunha.media.batch.removeduplicatesfotos.service.RemoveDuplicateImagesService;

public class ImageFileFilterAndDuplicates extends AbstractFileFilter implements FileFilter {
	
	@Override
    public boolean accept(final File file) {
		
		boolean isRenamedFile = (file != null && file.getName().endsWith(RemoveDuplicateImagesService.DUPLICATE_EXTENSION));
		
		return isRenamedFile || FileSystemUtils.isImageFile(file);
		
    }

}
