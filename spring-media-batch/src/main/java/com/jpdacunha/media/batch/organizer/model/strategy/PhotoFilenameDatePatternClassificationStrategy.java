package com.jpdacunha.media.batch.organizer.model.strategy;

import java.io.File;
import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jpdacunha.media.batch.core.utils.FileSystemUtils;
import com.jpdacunha.media.batch.organizer.configuration.MediaBatchYamlConfiguration;

public class PhotoFilenameDatePatternClassificationStrategy extends FilenameDatePatternClassificationStrategy implements ClassificationStrategy {
	
	private static Logger log = LoggerFactory.getLogger(PhotoFilenameDatePatternClassificationStrategy.class);
	
	public PhotoFilenameDatePatternClassificationStrategy(MediaBatchYamlConfiguration configuration) {
		super(configuration.getPhotoFileNamePatterns(), configuration.getPhotoFileNameEmbeddedDatePatterns(), configuration.getPhotoDateFormaterPatterns());
	}

	@Override
	public Path getClassificationPath(File toOrganize, File destDir) {
		
		log.debug("Inside classification strategy identified by [" + this.getClass().getName() + "]");
		if (FileSystemUtils.isImageFile(toOrganize)) {
			return super.getClassificationPath(toOrganize, destDir);
		} else {
			log.debug("[" + toOrganize.getName() + "] is not an image");
		}
		return null;
		
	}

}
