package com.jpdacunha.media.batch.removeduplicatesfotos.service.impl;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.jpdacunha.media.batch.core.filter.impl.ImageFileFilter;
import com.jpdacunha.media.batch.core.utils.FileSystemUtils;
import com.jpdacunha.media.batch.organizer.service.impl.MediaServiceImpl;
import com.jpdacunha.media.batch.removeduplicatesfotos.configuration.RemoveDuplicatesFotosYamlConfiguration;
import com.jpdacunha.media.batch.removeduplicatesfotos.exception.RemoveDuplicateImageshException;
import com.jpdacunha.media.batch.removeduplicatesfotos.service.RemoveDuplicateImagesService;

import dev.brachtendorf.jimagehash.hashAlgorithms.AverageHash;
import dev.brachtendorf.jimagehash.hashAlgorithms.HashingAlgorithm;

@Service
public class RemoveDuplicateFotosServiceImpl implements RemoveDuplicateImagesService {
	
	private static Logger log = LoggerFactory.getLogger(MediaServiceImpl.class);
	
	@Autowired
	private RemoveDuplicatesFotosYamlConfiguration configuration;
	
	// Key bit resolution
	private int keyLength = 64;

	// Pick an algorithm
	private HashingAlgorithm hasher = new AverageHash(keyLength);

	@Scheduled(cron = "0 0 2 * * *")
	public void removeDuplicateFotos() throws RemoveDuplicateImageshException {
		
		log.info("#######################################################################################");
		log.info("# Starting remove duplicates FOTOS //////> ...");
		log.info("#######################################################################################");
		
		String[] startPaths = configuration.getPaths().getStartRootDirs();
		
		for (String startPath : startPaths) {
			
			File startDir = new File(startPath);
			log.info("###### Processing [" + startDir.getAbsolutePath() + "] directory...");
			//Remove dryRubn option here
			removeDuplicates(startDir, true);
			log.info("###### Done.");
			
		}
			
		log.info("#######################################################################################");
		log.info("# End remove duplicates FOTOS.");
		log.info("#######################################################################################");
		
	}
	
	public void removeDuplicates(File startDir, boolean dryRun) {
		
		log.info("##### Starting removeDuplicates ...");
		log.info("## Applied configuration : [" + configuration + "]");
		
		FileFilter fileFilter = new ImageFileFilter();
		
		if (startDir == null) {
			throw new RemoveDuplicateImageshException("Missing required parameter");
		}
		
		if (!startDir.isDirectory()) {
			throw new RemoveDuplicateImageshException(startDir.getAbsolutePath() + " is not a valid source directory");
		}
		
		if (startDir.exists()) {
			
			//Non recursive list of files
			List<File> toRemove = new ArrayList<>();
			
			File[] searched = startDir.listFiles(fileFilter);
			List<File> searchedList = new LinkedList<>(Arrays.asList(searched));
			
			if (searchedList.size() == 0) {
				log.info("No files in specified directories.");
			} else {
				log.debug("Found [" + searchedList.size() + "] images in [" + startDir.getAbsolutePath() + "]");
			}
			
			//Searching for images to remove
			for (final Iterator<File> iter = searchedList.listIterator(); iter.hasNext();) {
				
				File file = iter.next();
					
				//Les dates de modification ne sont pas conservées en cas de copie de fichier
				//Les données EXIF ne sont pas renseignées dans les photos que nous avons. Il faut se baser sur la date de modif qui peut être lue sans librairie particulière.
				log.debug("## Start to remove duplicates images for [" + file.getName() + "] ...");
				
				File[] toCompare = startDir.listFiles(fileFilter);
				List<File> toCompareList = new LinkedList<>(Arrays.asList(toCompare));
						
				//Using an iterator in order to remove the file instance during iteration
				for (final Iterator<File> iter2 = toCompareList.listIterator(); iter2.hasNext();) {
					
					File toCompareFile = iter2.next();
					
					try {
						
						String toCompareAbsolutePath = toCompareFile.getAbsolutePath();						
						String fileAbsolutePath = file.getAbsolutePath();
						
						log.debug("  :> Comparing[" + fileAbsolutePath + "] and [" + toCompareAbsolutePath + "].");
						
						if (!fileAbsolutePath.equals(toCompareAbsolutePath)) {
							
							if (FileSystemUtils.imagesAreExactlyTheSame(hasher, file, toCompareFile)) {
								
								log.debug("  :> Registering [" + toCompareAbsolutePath + "] as removed file");
								//Clone the object in order to not share reference
								toRemove.add(new File(toCompareAbsolutePath));
								//This is why we use an iterator
								searchedList.remove(toCompareFile);
								
							} else {
								log.debug("  :> No match for[" + fileAbsolutePath + "] and [" + toCompareAbsolutePath + "]");
							}
							
						} else {
							log.debug("  :> Skipping comparision for current file");
						}
						
					} catch (IOException e) {
						throw new RemoveDuplicateImageshException(e);
					}
					
				}
				
				log.debug("## Done for [" + file.getName() + "].");
				
			}
			
			for (File toRemoveFile : toRemove) {
				log.info("  :> Removing : [" + toRemoveFile.getAbsolutePath() + "]");
				if (!dryRun) {
					FileSystemUtils.removeIfExists(toRemoveFile);
				}
				log.info("  :> File removed successfully");
			}
			
		} else {
			log.error(startDir.getAbsolutePath() + " does not exists");
		}
		
		log.info("##### End.");
		
	}

}
