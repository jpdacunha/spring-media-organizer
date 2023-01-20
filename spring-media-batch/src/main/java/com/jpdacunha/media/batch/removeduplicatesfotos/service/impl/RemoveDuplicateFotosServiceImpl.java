package com.jpdacunha.media.batch.removeduplicatesfotos.service.impl;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.jpdacunha.media.batch.core.utils.FileSystemUtils;
import com.jpdacunha.media.batch.organizer.service.impl.MediaServiceImpl;
import com.jpdacunha.media.batch.removeduplicatesfotos.configuration.RemoveDuplicatesFotosYamlConfiguration;
import com.jpdacunha.media.batch.removeduplicatesfotos.exception.RemoveDuplicateImageshException;
import com.jpdacunha.media.batch.removeduplicatesfotos.filter.impl.ImageFileFilterAndDuplicates;
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
		
		Instant start = Instant.now();
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
		
		Instant end = Instant.now();
		Duration exectime = Duration.between(start, end);
			
		log.info("#######################################################################################");
		log.info("# End remove duplicates FOTOS. Executed in " + exectime);
		log.info("#######################################################################################");
		
	}
	
	public void removeDuplicates(File startDir, boolean dryRun) {
		
		log.info("##### Starting removeDuplicates ...");
		log.info("## Applied configuration : [" + configuration + "]");
		
		if (startDir == null) {
			throw new RemoveDuplicateImageshException("Missing required parameter");
		}
		
		if (!startDir.isDirectory()) {
			throw new RemoveDuplicateImageshException(startDir.getAbsolutePath() + " is not a valid source directory");
		}
		
		if (startDir.exists()) {
			
			//Non recursive list of files
			Set<String> toRemove = new HashSet<>();
			
			FileFilter fileFilter = new ImageFileFilterAndDuplicates();
			
			//File[] searched = startDir.listFiles(fileFilter);
			List<File> searchedList = (List<File>) FileUtils.listFilesAndDirs(startDir, (IOFileFilter)fileFilter, TrueFileFilter.INSTANCE);
			
			if (searchedList.size() == 0) {
				log.info("No files in specified directories.");
			} else {
				log.debug("Found [" + searchedList.size() + "] s in [" + startDir.getAbsolutePath() + "]");
			}
			
			//Searching for images to remove
			for (final Iterator<File> searchedListIterator = searchedList.listIterator(); searchedListIterator.hasNext();) {
				
				File file = searchedListIterator.next();
				
				if (!FileSystemUtils.isValidFile(file)) {
					continue;
				}
					
				//Les dates de modification ne sont pas conservées en cas de copie de fichier
				//Les données EXIF ne sont pas renseignées dans les photos que nous avons. Il faut se baser sur la date de modif qui peut être lue sans librairie particulière.
				log.debug("## Start to remove duplicates images for [" + file.getName() + "] ...");
				
				List<File> toCompareList = (List<File>) FileUtils.listFilesAndDirs(startDir, (IOFileFilter)fileFilter, TrueFileFilter.INSTANCE);
						
				//Using an iterator in order to remove the file instance during iteration
				for (final Iterator<File> toCompareListIterator = toCompareList.listIterator(); toCompareListIterator.hasNext();) {
					
					File toCompareFile = toCompareListIterator.next();
					
					if (!FileSystemUtils.isValidFile(toCompareFile)) {
						continue;
					}
					
					try {
						
						String toCompareAbsolutePath = toCompareFile.getAbsolutePath();						
						String fileAbsolutePath = file.getAbsolutePath();
						
						log.debug("  :> Comparing[" + fileAbsolutePath + "] and [" + toCompareAbsolutePath + "].");
						
						if (toCompareFile.exists() && file.exists() && !fileAbsolutePath.equals(toCompareAbsolutePath)) {
							
							if (toCompareAbsolutePath.endsWith(RemoveDuplicateImagesService.DUPLICATE_EXTENSION)) {
								
								toRemove.add(toCompareAbsolutePath);
								log.debug("  :> Registering [" + toCompareFile.getAbsolutePath() + "] as file to remove");
								
							} else if (FileSystemUtils.imagesAreExactlyTheSame(hasher, file, toCompareFile)) {
								
								File renamedToCompareFile = new File(toCompareAbsolutePath + RemoveDuplicateImagesService.DUPLICATE_EXTENSION);
								
								boolean renamed = false;
								if (!dryRun) {
									renamed = toCompareFile.renameTo(renamedToCompareFile);
								} else {
									renamed =true;
								}
								
								//Clone the object in order to not share reference
								if (renamed) {
									toRemove.add(renamedToCompareFile.getAbsolutePath());
									log.debug("  :> Renaming and registering [" + renamedToCompareFile.getAbsolutePath() + "] as file to remove");
								} else {
									log.error("Unexpected error while renaming [" + toCompareAbsolutePath + "].");
								}
								
							} else {
								log.debug("  :> No match for[" + fileAbsolutePath + "] and [" + toCompareAbsolutePath + "]");
							}
							
						} else {
							log.debug("  :> Skipping comparision for current file");
						}
						
					} catch (IOException e) {
						log.error(e.getMessage() ,e);
						throw new RemoveDuplicateImageshException(e);
					}
					
				}
				
				log.debug("## Done for [" + file.getName() + "].");
				
			}
			
			log.info("## Start to physically remove identified duplicates ...");
			//Removing registered files
			for (String toRemoveFilePath : toRemove) {
				
				log.info("  :> Removing : [" + toRemoveFilePath + "]");
				if (!dryRun) {
					File toRemoveFile = new File(toRemoveFilePath);
					FileSystemUtils.removeIfExists(toRemoveFile);
				}
				log.info("  :> File removed successfully");
				
			}
			log.info("## Done.");
			
		} else {
			log.error(startDir.getAbsolutePath() + " does not exists");
		}
		
		log.info("##### End.");
		
	}

}
