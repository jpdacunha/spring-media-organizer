package com.jpdacunha.media.batch.organizer.service.impl;

import java.io.File;
import java.io.FileFilter;
import java.time.Instant;
import java.util.Locale;

import org.apache.commons.io.filefilter.IOFileFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.jpdacunha.media.batch.core.filter.impl.ImageFileFilter;
import com.jpdacunha.media.batch.core.filter.impl.VideoFileFilter;
import com.jpdacunha.media.batch.core.model.HumanReadableDurationModel;
import com.jpdacunha.media.batch.core.utils.FileSystemUtils;
import com.jpdacunha.media.batch.organizer.configuration.MediaBatchYamlConfiguration;
import com.jpdacunha.media.batch.organizer.exception.MediaBatchException;
import com.jpdacunha.media.batch.organizer.model.MediaDescriptor;
import com.jpdacunha.media.batch.organizer.service.MediaService;

@Service
public class MediaServiceImpl implements MediaService {
	
	private static Logger log = LoggerFactory.getLogger(MediaServiceImpl.class);

	@Autowired
	private MediaBatchYamlConfiguration configuration;

	@Scheduled(cron = "0 0 1 * * *")
	public void classifyPhotos() throws MediaBatchException {
		
		Instant start = Instant.now();
		log.info("#######################################################################################");
		log.info("# Starting classify FOTOS //////> ...");
		log.info("#######################################################################################");
		
		String[] startPaths = configuration.getPaths().getStartRootDirs();
		String destPath = configuration.getPaths().getDestinationRootDirPhoto();
		IOFileFilter fileFilter = new ImageFileFilter();
		
		File destDir = new File(destPath);
		for (String startPath : startPaths) {
			
			File startDir = new File(startPath);
			log.info("###### Processing [" + startDir.getAbsolutePath() + "] directory...");
			classifyByYear(startDir, destDir, fileFilter, false);
			log.info("###### Done.");
		}
		
		Instant end = Instant.now();
		String exectime = new HumanReadableDurationModel(start, end).toHumanReadable();
		
		log.info("#######################################################################################");
		log.info("# End FOTOS. Executed in " + exectime);
		log.info("#######################################################################################");
		
	}
	
	@Scheduled(cron = "0 0 2 * * *")
	public void classifyVideos() throws MediaBatchException {
		
		Instant start = Instant.now();
		log.info("#######################################################################################");
		log.info("# Starting classify VIDEOS //////> ...");
		log.info("#######################################################################################");
		
		String[] startPaths = configuration.getPaths().getStartRootDirs();
		String destPath = configuration.getPaths().getDestinationRootDirVideo();
		IOFileFilter fileFilter = new VideoFileFilter();
		
		File destDir = new File(destPath);
		for (String startPath : startPaths) {
			
			File startDir = new File(startPath);
			log.info("###### Processing [" + startDir.getAbsolutePath() + "] directory...");
			classifyByYear(startDir, destDir, fileFilter, false);
			log.info("###### Done.");
		}
		
		Instant end = Instant.now();
		String exectime = new HumanReadableDurationModel(start, end).toHumanReadable();
		
		log.info("#######################################################################################");
		log.info("# End VIDEOS. Executed in " + exectime);
		log.info("#######################################################################################");
		
	}
	
	
	public void classifyByYear(File startDir, File destDir, FileFilter fileFilter, boolean dryRun) throws MediaBatchException {
		
		log.info("##### Starting classifyByYear ...");
		log.info("## Applied configuration : [" + configuration + "]");
		
		if (startDir == null || destDir == null) {
			throw new MediaBatchException("Missing required parameter");
		}
		
		if (!startDir.isDirectory()) {
			throw new MediaBatchException(startDir.getAbsolutePath() + " is not a valid source directory");
		}
		
		if (!destDir.isDirectory()) {
			throw new MediaBatchException(destDir.getAbsolutePath() + " is not a valid destination directory");
		}
		
		if (startDir.exists()) {
			
			//Non recursive list of files
			File[] searched = startDir.listFiles(fileFilter);
			
			if (searched.length == 0) {
				log.info("Unable to find any matching files");
			}
			
			for (File file : searched) {
				
				MediaDescriptor mediaDescriptor = new MediaDescriptor(file, Locale.FRANCE);
				
				//Les dates de modification ne sont pas conservées en cas de copie de fichier
				//Les données EXIF ne sont pas renseignées dans les photos que nous avons. Il faut se baser sur la date de modif qui peut être lue sans librairie particulière.
				log.info("## Starting classification for [" + file.getName() + "] ...");
				log.debug("  :> File details : " + mediaDescriptor);
				
				String yearMonthPath = destDir.getAbsolutePath() + File.separator + mediaDescriptor.getYearMonthDirName();
				File yearMonthDir = FileSystemUtils.createDirIfNotExists(yearMonthPath);
				
				log.debug("  :> Moving : [" + file.getAbsolutePath() + "] to [" + yearMonthDir.getAbsolutePath() + "]");
				if (!dryRun) {
					FileSystemUtils.moveFileToDirectory(file, yearMonthDir, true);
				}
				log.debug("  :> File moved successfully");
				
				log.info("## Done for [" + file.getName() + "].");
			}
			
		} else {
			log.error(startDir.getAbsolutePath() + " does not exists");
		}
		
		log.info("##### End.");
		
	}

}
