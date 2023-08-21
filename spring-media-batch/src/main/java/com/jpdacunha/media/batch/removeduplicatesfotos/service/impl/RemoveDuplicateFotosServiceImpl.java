package com.jpdacunha.media.batch.removeduplicatesfotos.service.impl;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
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

import com.jpdacunha.media.batch.core.database.model.Cursor;
import com.jpdacunha.media.batch.core.model.HumanReadableDurationModel;
import com.jpdacunha.media.batch.core.service.CursorService;
import com.jpdacunha.media.batch.core.utils.FileSystemUtils;
import com.jpdacunha.media.batch.removeduplicatesfotos.configuration.RemoveDuplicatesFotosYamlConfiguration;
import com.jpdacunha.media.batch.removeduplicatesfotos.exception.RemoveDuplicateImagesException;
import com.jpdacunha.media.batch.removeduplicatesfotos.filter.impl.ImageFileFilterAndDuplicates;
import com.jpdacunha.media.batch.removeduplicatesfotos.model.DuplicatePhotosModel;
import com.jpdacunha.media.batch.removeduplicatesfotos.service.RemoveDuplicateImagesService;

import dev.brachtendorf.jimagehash.hashAlgorithms.AverageHash;
import dev.brachtendorf.jimagehash.hashAlgorithms.HashingAlgorithm;

@Service
public class RemoveDuplicateFotosServiceImpl implements RemoveDuplicateImagesService {
	
	private static Logger log = LoggerFactory.getLogger(RemoveDuplicateFotosServiceImpl.class);
	
	@Autowired
	private RemoveDuplicatesFotosYamlConfiguration configuration;
	
	@Autowired
	private CursorService cursorService;
	
	// Key bit resolution
	private int keyLength = 64;

	// Pick an algorithm
	private HashingAlgorithm hasher = new AverageHash(keyLength);

	@Scheduled(cron = "0 0 2 * * *")
	public void removeDuplicateFotos() throws RemoveDuplicateImagesException {
		
		Instant start = Instant.now();
		
		//TODO:JDA Remove dryRun option here
		boolean dryRun = true;
		
		log.info("#######################################################################################");
		log.info("# Starting remove duplicates FOTOS //////> ...");
		log.info("#######################################################################################");
		
		String[] startPaths = configuration.getPaths().getStartRootDirs();
		
		for (String startPath : startPaths) {
			
			File startDir = new File(startPath);
			
			log.info("###### Processing [" + startDir.getAbsolutePath() + "] directory...");
			
			cleanCursors();
			
			displayAllCursors();
			
			File workDir = cursorService.getWorkDirFromRegisteredCursors(startDir);
			
			removeDuplicates(workDir, dryRun);
			
			cursorService.createOrUpdateCursor(workDir);
			
			log.info("###### Done.");
			
		}
		
		Instant end = Instant.now();
		String exectime = new HumanReadableDurationModel(start, end).toHumanReadable();
			
		log.info("#######################################################################################");
		log.info("# End remove duplicates FOTOS. Executed in " + exectime);
		log.info("#######################################################################################");
		
	}
	
	public void displayAllCursors() {
		
		log.info("##### Displaying registered cursors ...");
		
		List<Cursor> cursors = cursorService.getAll();
		
		for (Cursor cursor : cursors) {
			
			log.info("  Found " + cursor.toString());
			
		}
		
		log.info("##### End.");
		
	}
	
	public void cleanCursors() {
		
		log.info("##### Starting cleaning cursors ...");
		
		cursorService.cleanDatabaseCursors();
		
		log.info("##### End.");
		
	}

	public void removeDuplicates(File toAnalyzeDir, boolean dryRun) {
		
		log.info("##### Starting removeDuplicates ...");
		
		if (toAnalyzeDir == null) {
			throw new RemoveDuplicateImagesException("Missing required parameter");
		}
		
		if (!toAnalyzeDir.isDirectory()) {
			throw new RemoveDuplicateImagesException(toAnalyzeDir.getAbsolutePath() + " is not a valid source directory");
		}
		
		log.info("## Applied configuration : [" + configuration + "]");
		
		if (toAnalyzeDir.exists()) {
			
			//Non recursive list of files
			Set<DuplicatePhotosModel> toRemove = new HashSet<>();
			
			FileFilter fileFilter = new ImageFileFilterAndDuplicates();		

			List<File> searchedList = (List<File>) FileUtils.listFilesAndDirs(toAnalyzeDir, (IOFileFilter)fileFilter, TrueFileFilter.INSTANCE);
			
			if (searchedList.size() == 0) {
				log.info("No files in specified directories.");
			} else {
				log.info("Found [" + searchedList.size() + "] files in [" + toAnalyzeDir.getAbsolutePath() + "]");
			}
			
			//Searching for images to remove
			for (final Iterator<File> searchedListIterator = searchedList.listIterator(); searchedListIterator.hasNext();) {
				
				File file = searchedListIterator.next();
				
				if (!FileSystemUtils.isValidFile(file)) {
					continue;
				}
				
				log.debug("## Start to remove duplicates images for [" + file.getName() + "] ...");
				
				List<File> toCompareList = (List<File>) FileUtils.listFilesAndDirs(toAnalyzeDir, (IOFileFilter)fileFilter, TrueFileFilter.INSTANCE);
						
				//Using an iterator in order to remove the file instance during iteration
				for (final Iterator<File> toCompareListIterator = toCompareList.listIterator(); toCompareListIterator.hasNext();) {
					
					File toCompareFile = toCompareListIterator.next();
					
					if (!FileSystemUtils.isValidFile(toCompareFile)) {
						continue;
					}
					
					try {
						
						String toCompareAbsolutePath = toCompareFile.getAbsolutePath();						
						String fileAbsolutePath = file.getAbsolutePath();
						
						log.debug("  :> Comparing [" + fileAbsolutePath + "] and [" + toCompareAbsolutePath + "].");
						
						if (toCompareFile.exists() && file.exists() && !fileAbsolutePath.equals(toCompareAbsolutePath)) {
							
							if (toCompareAbsolutePath.endsWith(RemoveDuplicateImagesService.DUPLICATE_EXTENSION)) {
								
								DuplicatePhotosModel duplicate = new DuplicatePhotosModel(file, toCompareFile);
								toRemove.add(duplicate);
								log.debug("  :> Registering [" + toCompareFile.getAbsolutePath() + "] as file to remove");
								
							} else if (FileSystemUtils.imagesAreExactlyTheSame(hasher, file, toCompareFile)) {
								
								File renamedToCompareFile = new File(toCompareAbsolutePath + RemoveDuplicateImagesService.DUPLICATE_EXTENSION);
								
								boolean renamed = false;
								if (!dryRun) {
									renamed = toCompareFile.renameTo(renamedToCompareFile);
								} else {
									renamed = true;
								}
								
								//Clone the object in order to not share reference
								if (renamed) {
									
									DuplicatePhotosModel duplicate = new DuplicatePhotosModel(file, renamedToCompareFile);
									toRemove.add(duplicate);
									
									log.debug("  :> Duplicate files identified : renaming and registering [" + renamedToCompareFile.getAbsolutePath() + "] as file to remove");
								} else {
									log.error("Unexpected error while renaming [" + toCompareAbsolutePath + "].");
								}
								
							} else {
								log.debug("  :> No match for[" + fileAbsolutePath + "] and [" + toCompareAbsolutePath + "]");
							}
							
						} else {
							log.debug("  :> Skipping comparision : currentFile and toCompareFile are the same file");
						}
						
					} catch (IOException e) {
						log.error(e.getMessage() ,e);
						throw new RemoveDuplicateImagesException(e);
					}
					
				}
				
				log.debug("## Done for [" + file.getName() + "].");
				
			}
			
			log.info("## Start to physically remove identified duplicates ...");
			//Removing registered files
			for (DuplicatePhotosModel duplicatePhotosModel : toRemove) {
				
				String toRemoveFilePath = duplicatePhotosModel.getDuplicateFile().getAbsolutePath();
				String filePath = duplicatePhotosModel.getFile().getAbsolutePath();
				
				log.info("  :> Removing : [" + toRemoveFilePath + "] as duplicate as [" + filePath + "]");
				if (!dryRun) {					
					File toRemoveFile = new File(toRemoveFilePath);
					FileSystemUtils.removeIfExists(toRemoveFile);
				}
				log.info("  :> File removed successfully");
				
			}
			log.info("## Done.");
			
		} else {
			log.error(toAnalyzeDir.getAbsolutePath() + " does not exists");
		}
		
		log.info("##### End.");
		
	}

}
