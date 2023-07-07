package com.jpdacunha.media.batch.core.filter.impl;

import java.io.File;
import java.io.FileFilter;

import org.apache.commons.io.filefilter.AbstractFileFilter;
import org.apache.commons.lang3.StringUtils;

public class YearDirectoryFileFilter extends AbstractFileFilter implements FileFilter {
	
    @Override
    public boolean accept(final File file) {
    	
    	if (file != null) {
    		
    		String fileName = file.getName();
        	
        	boolean directory = file.isDirectory();
        	boolean isYearFile = fileName.length() == 4 && StringUtils.isNumeric(fileName);
        	
        	return directory && isYearFile;
    		
    	}
    	
    	return false;

    	
    }

}
