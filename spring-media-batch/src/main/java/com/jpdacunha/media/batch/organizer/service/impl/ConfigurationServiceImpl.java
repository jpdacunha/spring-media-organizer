package com.jpdacunha.media.batch.organizer.service.impl;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jpdacunha.media.batch.organizer.configuration.MediaBatchYamlConfiguration;
import com.jpdacunha.media.batch.organizer.exception.MediaBatchException;
import com.jpdacunha.media.batch.organizer.exception.MissingDirectoryMediaBatchException;
import com.jpdacunha.media.batch.organizer.service.ConfigurationService;
import com.jpdacunha.media.batch.organizer.utils.FileSystemUtils;

@Service
public class ConfigurationServiceImpl implements ConfigurationService {
	
	private static Logger log = LoggerFactory.getLogger(ConfigurationServiceImpl.class);

	@Autowired
	private MediaBatchYamlConfiguration configuration;

	@Override
	public void verifyConfiguration() throws MediaBatchException {
		
		log.info("## Starting configuration verification");
		
		//Photo directory
		String photoDestPath = configuration.getPaths().getDestinationRootDirPhoto();
		File photoDestDir = new File(photoDestPath);
		if (!FileSystemUtils.isDirExists(photoDestDir)) {
			throw new MissingDirectoryMediaBatchException(MissingDirectoryMediaBatchException.createMessage(photoDestDir));
		}
		
		//Video directory
		String videoDestPath = configuration.getPaths().getDestinationRootDirVideo();
		File videoDestDir = new File(videoDestPath);
		if (!FileSystemUtils.isDirExists(videoDestDir)) {
			throw new MissingDirectoryMediaBatchException(MissingDirectoryMediaBatchException.createMessage(videoDestDir));
		}
		
		String[] startPaths = configuration.getPaths().getStartRootDirs();
		
		if (startPaths == null || startPaths.length == 0) {
			throw new MissingDirectoryMediaBatchException("No start path is defined");
		}
		
		for (String path : startPaths) {
			
			File startDir = new File(path);
			if (!FileSystemUtils.isDirExists(startDir)) {
				throw new MissingDirectoryMediaBatchException(MissingDirectoryMediaBatchException.createMessage(startDir));
			}

		}
		log.info("## Configuration is OK");
		
	}

}
