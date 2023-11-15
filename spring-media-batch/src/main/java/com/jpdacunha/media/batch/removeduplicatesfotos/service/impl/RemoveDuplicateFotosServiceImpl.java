package com.jpdacunha.media.batch.removeduplicatesfotos.service.impl;

import java.io.File;
import java.io.FileFilter;
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

import com.jpdacunha.media.batch.core.database.model.Cursor;
import com.jpdacunha.media.batch.core.model.HumanReadableDurationModel;
import com.jpdacunha.media.batch.core.service.CursorService;
import com.jpdacunha.media.batch.core.utils.FileSystemUtils;
import com.jpdacunha.media.batch.removeduplicatesfotos.configuration.RemoveDuplicatesFotosYamlConfiguration;
import com.jpdacunha.media.batch.removeduplicatesfotos.exception.RemoveDuplicateImagesException;
import com.jpdacunha.media.batch.removeduplicatesfotos.filter.impl.ImageFileFilterAndDuplicates;
import com.jpdacunha.media.batch.removeduplicatesfotos.model.DuplicatePhotosModel;
import com.jpdacunha.media.batch.removeduplicatesfotos.model.ListRemovalReportModel;
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

	@Scheduled(cron = "0 0 3 * * *")
	public void removeDuplicateFotos() throws RemoveDuplicateImagesException {
		
		Instant start = Instant.now();
		
		boolean dryRun = false;
		
		log.info("#######################################################################################");
		log.info("# Starting remove duplicates FOTOS //////> ...");
		log.info("#######################################################################################");
		
		log.info("## Applied configuration : [" + configuration + "]");
		
		String[] startPaths = configuration.getPaths().getStartRootDirs();
		
		for (String startPath : startPaths) {
			
			Instant pathStart = Instant.now();
			
			File startDir = new File(startPath);
			
			log.info("###### Processing [" + startDir.getAbsolutePath() + "] directory...");
			
			cleanCursors();
			
			displayAllCursors();
			
			File workDir = cursorService.getWorkDirFromRegisteredCursors(startDir);
			
			removeDuplicates(workDir, dryRun);
			
			Instant pathEnd = Instant.now();
			
			Duration duration = Duration.between(pathStart, pathEnd);
			
			cursorService.createOrUpdateCursor(workDir, duration);
			
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
		
		if (toAnalyzeDir.exists()) {
			
			//Non recursive list of files
			Set<DuplicatePhotosModel> toRemove = new HashSet<>();
			
			FileFilter fileFilter = new ImageFileFilterAndDuplicates();		

			List<File> searchedList = (List<File>) FileUtils.listFilesAndDirs(toAnalyzeDir, (IOFileFilter)fileFilter, TrueFileFilter.INSTANCE);
			
			int total = searchedList.size();
			
			if (total == 0) {
				log.info("No files in specified directories.");
			} else {
				log.info("Found [" + total + "] files in [" + toAnalyzeDir.getAbsolutePath() + "]");
			}
			
			try {
				
				int i = 1;
				Instant start = Instant.now();
				
				//Searching for images to remove
				for (final Iterator<File> searchedListIterator = searchedList.listIterator(); searchedListIterator.hasNext();) {
					
			    	if (i == 1 || (i % 100) == 0) {
			    		
						Instant end = Instant.now();
						String exectime = new HumanReadableDurationModel(start, end).toHumanReadable();
					
	            		String message = String.format("Processing : %d of %d", i, total);
	            		log.info(message);
	            		
						String timeMessage = String.format("Elapsed time : %s", exectime);
	            		log.info(timeMessage);
	            		
	            	}
					
					File file = searchedListIterator.next();
					
					if (FileSystemUtils.isValidFile(file)) {
						
						log.debug("## Start to remove duplicates images for [" + file.getName() + "] ...");
						
						List<File> toCompareList = (List<File>) FileUtils.listFilesAndDirs(toAnalyzeDir, (IOFileFilter)fileFilter, TrueFileFilter.INSTANCE);
								
						//Using an iterator in order to remove the file instance during iteration
						for (final Iterator<File> toCompareListIterator = toCompareList.listIterator(); toCompareListIterator.hasNext();) {
							
							File toCompareFile = toCompareListIterator.next();
							
							if (FileSystemUtils.isValidFile(toCompareFile)) {
								
								String toCompareAbsolutePath = toCompareFile.getAbsolutePath();						
								String fileAbsolutePath = file.getAbsolutePath();
								
								log.debug("  :> Comparing [" + fileAbsolutePath + "] and [" + toCompareAbsolutePath + "].");
								
								if (toCompareFile.exists() && file.exists() && !fileAbsolutePath.equals(toCompareAbsolutePath)) {
									
									if (toCompareAbsolutePath.endsWith(RemoveDuplicateImagesService.DUPLICATE_EXTENSION)) {
										
										DuplicatePhotosModel duplicate = new DuplicatePhotosModel(toCompareFile);
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
										
										//Clone the object to avoid sharing reference
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
								
							} else {
								log.debug("Invalid file [" + file.getAbsolutePath() + "]");
							}
							
						}
						
					} else {
						log.debug("Invalid file [" + file.getAbsolutePath() + "]");
					}

					
					log.debug("## Done for [" + file.getName() + "].");
					
					i++;
					
				}
				
			} catch (Exception e) {
				log.error(e.getMessage() ,e);
				throw new RemoveDuplicateImagesException(e);
			}
			
			log.info("## Start to physically remove identified duplicates ...");
			
			
			if (toRemove.size() == 0) {
				
				log.info("  :> No duplicates found");
				
			} else {

				ListRemovalReportModel report = removeFileList(dryRun, toRemove);
				String sizeMessage = String.format("Nb files deleted : %s, Total saved size : %s", report.getNbFilesDeleted(), report.getTotalSavedSize());
				log.info(sizeMessage);
				
			}
			log.info("## Done.");
			
		} else {
			log.error(toAnalyzeDir.getAbsolutePath() + " does not exists");
		}
		
		log.info("##### End.");
		
	}

	private ListRemovalReportModel removeFileList(boolean dryRun, Set<DuplicatePhotosModel> toRemove) {
		
		double savedSize = 0;
		int nbDeletedFiles = 0;
		
		//Removing registered files
		for (DuplicatePhotosModel duplicatePhotosModel : toRemove) {
			
			File toRemoveFile = duplicatePhotosModel.getDuplicateFile();
			File conservedFile = duplicatePhotosModel.getFile();
		
			if (toRemoveFile != null && conservedFile != null) {
				
				String conservedFilePath = conservedFile.getAbsolutePath();
				String toRemoveFilePath = toRemoveFile.getAbsolutePath();
				log.info("  :> Try to remove : [" + toRemoveFilePath + "] as duplicate as [" + conservedFilePath + "]");
				
			} else {
				
				String toRemoveFilePath = toRemoveFile.getAbsolutePath();
				log.info("  :> Try to remove : [" + toRemoveFilePath + "]");
				
			}
			
			savedSize = savedSize + FileSystemUtils.getSizeIfExists(toRemoveFile);
			
			boolean removed = false;

			if (dryRun) {
				log.warn("  :> File not removed (Dry run activated)");
			} else if (!FileSystemUtils.isFileExists(toRemoveFile)) {
				log.warn("  :> File not removed (Does not exists)");
			} else {
				
				removed = FileSystemUtils.removeIfExists(toRemoveFile);
				
				if (removed) {
					nbDeletedFiles ++;
					log.info("  :> File removed successfully");
				} else {
					log.warn("  :> File not removed because of an unknown error");
				}
				
			}

		}
		
		return new ListRemovalReportModel(savedSize, nbDeletedFiles);
		
	}

}
