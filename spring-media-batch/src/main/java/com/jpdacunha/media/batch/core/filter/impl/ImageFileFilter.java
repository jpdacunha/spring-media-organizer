package com.jpdacunha.media.batch.core.filter.impl;

import java.io.File;
import java.io.FileFilter;

import org.apache.commons.io.filefilter.AbstractFileFilter;

import com.jpdacunha.media.batch.core.utils.FileSystemUtils;

public class ImageFileFilter extends AbstractFileFilter implements FileFilter {

    @Override
    public boolean accept(final File file) {
        return FileSystemUtils.isImageFile(file);
    }

}
