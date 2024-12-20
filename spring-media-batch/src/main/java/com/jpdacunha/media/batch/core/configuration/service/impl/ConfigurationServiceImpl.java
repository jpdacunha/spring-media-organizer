package com.jpdacunha.media.batch.core.configuration.service.impl;

import java.io.File;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jpdacunha.media.batch.core.configuration.service.ConfigurationService;
import com.jpdacunha.media.batch.core.utils.ArrayHelper;
import com.jpdacunha.media.batch.core.utils.FileSystemUtils;
import com.jpdacunha.media.batch.organizer.configuration.MediaBatchYamlConfiguration;
import com.jpdacunha.media.batch.organizer.exception.InvalidPatternSetMediaBatchException;
import com.jpdacunha.media.batch.organizer.exception.MediaBatchException;
import com.jpdacunha.media.batch.organizer.exception.MissingDirectoryMediaBatchException;

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
		
		String[] photoFileNamePatterns = configuration.getPhotoFileNamePatterns();
		String[] photoFileNameEmbeddedDatePatterns = configuration.getPhotoFileNameEmbeddedDatePatterns();
		String[] photoDateFormaterPatterns = configuration.getPhotoDateFormaterPatterns();
		
		if (ArrayHelper.differentLength(photoFileNamePatterns, photoFileNameEmbeddedDatePatterns, photoDateFormaterPatterns)) {
			
			String beginErrorMessage = "Pattern sets with different sizes are invalid. ";
			String middleErrorMessage = "Array sizes were photoFileNamePatterns:[" + photoDateFormaterPatterns.length + "],photoFileNameEmbeddedDatePatterns:[" + photoFileNameEmbeddedDatePatterns.length  + "],photoDateFormaterPatterns:[" + photoDateFormaterPatterns.length + "]. ";
			String endErrorMessage = "Configuration dump : photoFileNamePatterns:[" + Arrays.toString(photoDateFormaterPatterns) + "],photoFileNameEmbeddedDatePatterns:[" + Arrays.toString(photoFileNameEmbeddedDatePatterns)  + "],photoDateFormaterPatterns:[" + Arrays.toString(photoDateFormaterPatterns)  + "]";
			throw new InvalidPatternSetMediaBatchException(beginErrorMessage + middleErrorMessage + endErrorMessage);
			
		}
		
		log.info("## Configuration is OK");
		
	}

}
