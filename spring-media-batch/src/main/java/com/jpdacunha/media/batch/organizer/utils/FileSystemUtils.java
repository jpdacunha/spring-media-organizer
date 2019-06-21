package com.jpdacunha.media.batch.organizer.utils;

import java.io.File;
import java.io.IOException;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileExistsException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jpdacunha.media.batch.organizer.exception.MediaBatchException;

public class FileSystemUtils {
	
	private static Logger log = LoggerFactory.getLogger(FileSystemUtils.class);

	private FileSystemUtils() {
		super();
	}
	
	public static boolean isVideoFile(File file) {
		
		try {
			String path = file.getCanonicalPath();
			return isVideoFile(path);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		return false;
		
	}
	
	public static boolean isImageFile(File file) {
		
		try {
			String path = file.getCanonicalPath();
			return isImageFile(path);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		return false;
		
	}
	
	public static boolean isVideoFile(String path) {
		
	    String extension = FilenameUtils.getExtension(path);
	    boolean mp4 = extension != null && extension.equalsIgnoreCase("mp4");
	    boolean avi = extension != null && extension.equalsIgnoreCase("avi");
	    boolean flv = extension != null && extension.equalsIgnoreCase("flv");
	    boolean mpg = extension != null && extension.equalsIgnoreCase("mpg");
	    boolean troisgp = extension != null && extension.equalsIgnoreCase("3gp");
	    boolean mkv = extension != null && extension.equalsIgnoreCase("mkv");
	    boolean mov = extension != null && extension.equalsIgnoreCase("mov");
	    
	    return mp4 || avi || flv || mpg || troisgp || mkv || mov;
	    
	}
	
	public static boolean isImageFile(String path) {
	    String mimeType = URLConnection.guessContentTypeFromName(path);
	    return mimeType != null && mimeType.startsWith("image");
	}
	
	public static File createDirIfNotExists(String path) throws MediaBatchException {
		return getDir(path, true);
	}

	public static File getDir(String path) throws MediaBatchException {
		return getDir(path, false);
	}
	
	public static File createFileIfNotExists(String path) throws MediaBatchException {
		return getFile(path, true);
	}	
	
	public static File getFile(String path) throws MediaBatchException {
		return getFile(path, false);
	}
	
	public static File getFile(String path, boolean createIfNotExists) throws MediaBatchException {
		
		if (path == null) {
			throw new MediaBatchException("Invalid null path");
		}
		
		File file = new File(path);
		
		if (file.exists() && file.isFile()) {
			
			return file;
			
		} else {
			
			if (createIfNotExists)  {
				
				log.debug("Creating missing file [" + file.getAbsolutePath() + "] ...");
				try {				
					if (file.createNewFile()) {
						log.debug("Done.");
					}
				} catch (IOException e) {
					throw new MediaBatchException(e);
				}
				
				return file;
				
			} else {
				throw new MediaBatchException("File identified by [" + path + "] does not exist or is not a valid file.");
			}
		}
		
	}
	
	private static File getDir(String path, boolean createDirIfNotExists) throws MediaBatchException {

		if (path == null) {
			throw new MediaBatchException("Invalid null directory path");
		}

		File dir = new File(path);

		if (dir.exists()) {

			return isDirectory(path, dir);

		} else {
			
			if (createDirIfNotExists) {
				
				log.debug("Creating missing directory [" + dir.getAbsolutePath() + "] ...");
				if (dir.mkdirs()) {
					log.debug("Done.");
				}
				
				return isDirectory(path, dir);
				
			} else {
				throw new MediaBatchException("Directory identified by [" + path + "] does not exist");
			}
		}

	}

	private static File isDirectory(String path, File dir) throws MediaBatchException {
		
		if (dir != null && dir.isDirectory()) {
			return dir;
		} else {
			throw new MediaBatchException("[" + path + "] is not a directory");
		}
		
	}

	public static boolean isFileEquals(File file1, File file2) throws MediaBatchException {

		if (!isFileExists(file1)) {
			throw new MediaBatchException("[" + file1.getPath() + "] does not exists");
		}

		if (!isFileExists(file2)) {
			throw new MediaBatchException("[" + file2.getPath() + "] does not exists");
		}

		try {
			return org.apache.commons.io.FileUtils.contentEquals(file1, file2);
		} catch (IOException e) {
			throw new MediaBatchException("Error while comparing files");
		}

	}
	
	public static void copyDirectory(File srcDir, File destDir) {
		
		try {
			FileUtils.copyDirectory(srcDir, destDir);
		} catch (IOException e) {
			throw new MediaBatchException(e);
		}
		
	}
	
	public static void moveFileToDirectory(File srcFile, File dir, boolean deleteIfExists) {
		
		try {
			FileUtils.moveFileToDirectory(srcFile, dir, true);
		} catch (FileExistsException e) {
			
			if (deleteIfExists && srcFile.exists()) {
				
				log.warn(e.getMessage());
				srcFile.delete();
				log.warn("Duplicated [" + srcFile.getAbsoluteFile() + "] successfully deleted");
				
			} else {
				throw new MediaBatchException(e);
			}
			
		} catch (IOException e) {
			throw new MediaBatchException(e);
		}
		
	}
	
	public static File recreateDir(String path) throws MediaBatchException {
		
		File dir = new File(path);
		return recreateDir(dir);
		
	}

	public static File recreateDir(File dir) throws MediaBatchException {

		if (isFileExists(dir) && !dir.isDirectory()) {
			throw new MediaBatchException("[" + dir.getAbsolutePath() + "] is not a directory");
		}

		try {
			
			if (isFileExists(dir)) {
				org.apache.commons.io.FileUtils.deleteDirectory(dir);
			}
			
			dir.mkdir();
			log.debug("[" + dir.getAbsolutePath() + "] successfully recreated");
			return dir;
			
		} catch (IOException e) {
			throw new MediaBatchException(e);
		}

	}

	private static List<String> toFileNames(File[] listDir) {

		List<String> fileNames = new ArrayList<>();
		for (File file : listDir) {
			fileNames.add(file.getName());
		}

		return fileNames;

	}

	public static boolean isFileExists(File file) {
		return file != null && file.exists();
	}
	
	public static boolean isDirExists(File file) {
		return file != null && file.exists() && file.isDirectory();
	}

	public static boolean isDirEquals(File dir1, File dir2) {
		
		log.info("STARTING comparing [" + dir1.getAbsolutePath() + "] == [" + dir2.getAbsolutePath() + "]");
		
		boolean isSameDirs= false;
		
		if (dir1 != null && dir2 != null) {

			File[] filesDir1 = dir1.listFiles();
			File[] filesDir2 = dir2.listFiles();
			
			Arrays.sort(filesDir1);
			Arrays.sort(filesDir2);
			
			List<String> listNames1 = toFileNames(filesDir1);
			List<String> listNames2 = toFileNames(filesDir2);
			boolean sameFiles = listNames1.containsAll(listNames2);
			
			int sizeDir1 = listNames1.size();
			int sizeDir2 = listNames2.size();
			
			boolean haveSameSize = sizeDir1 == sizeDir2;
			
			if (sameFiles && haveSameSize) {
				
				for (File file1 : filesDir1) {
					
					log.debug("file1 = " + file1);
					
					if (file1.isDirectory()) {
						
						String nameDir1 = file1.getName().toString();
					
						log.debug("[" + nameDir1 + "] is directory ? " + file1.isDirectory());
						
						for (File file2 : filesDir2) {
							
							if (file2.isDirectory()) {
								String nameDir2 = file2.getName().toString();
								log.debug("file2 = " + nameDir2);
								if (nameDir2.equals(nameDir1)) {
									log.debug("Two files have the same name");
									isSameDirs = isDirEquals(file1, file2);
								}
							}
							
						}
						
						if(!isSameDirs){
							break;
						}
						
					} else if (file1.isFile()) {
						
						log.debug("file1 = [" + file1.getAbsolutePath() +  "] is a file", FileSystemUtils.class);
						String nameFile1 = file1.getName().toString();
						
						try {
							for (File file2 : filesDir2) {
								
								if (file2.isFile()) {
									
									log.debug("file2 = " + file2.getAbsolutePath() + " is a file too");
									String nameFile2 = file2.getName().toString();
									if (nameFile1.equals(nameFile2)) {
										
										log.debug("[" + file1.getAbsolutePath() + "] & [" + file2.getAbsolutePath() + "] have the same name");
										isSameDirs = org.apache.commons.io.FileUtils.contentEquals(file1, file2);
										if(isSameDirs){
											log.debug("[" + file1.getAbsolutePath() + "] & [" + file2.getAbsolutePath() + "] which have the same name and have exactly the same content too");
										} else {
											log.debug("[" + file1.getAbsolutePath() + "] & [" + file2.getAbsolutePath() + "] have not the same content");
										}
										if(!isSameDirs){
											break;
										}
										
									}
									
								} else {
									log.debug("We will go to study another file in directory");
									isSameDirs = false;
								}
		
							}
							
						} catch (IOException e) {
							log.error(e.getMessage(), e);
						}
						
						if(!isSameDirs){
							break;
						}
					
					}
					
				}
				
			} else if (!sameFiles) {
				log.debug("The two directories does not have the same files");
				isSameDirs = false;
			} else if (!haveSameSize) {
				log.debug("The two directories does not have the same number of files");
				isSameDirs = false;		
			} else {
				log.error("Unsupported operation");
			}
			
		}
		
		return isSameDirs;
		
	}
	
	public static void displayDirectoryContent(File dir) {
		displayDirectoryContent(dir, "");
	}
	
	public static void displayDirectoryContent(File dir, String pad) {
		
		try {
			
			File[] files = dir.listFiles();
			for (File file : files) {
				if (file.isDirectory()) {
					log.debug(pad + " + [" + file.getName() + "] -> [directory] -> [" + file.getCanonicalPath() + "]");
					displayDirectoryContent(file, pad + "  ");
				} else {
					log.debug(pad + "  - [" + file.getName() + "] -> [file] -> [" + file.getCanonicalPath() + "]");
				}
			}
			
			if (files.length == 0) {
				log.debug(pad + "  - <Empty>");
			}
			
		} catch (IOException e) {
			log.error(e.getMessage(),e);
		}
		
	}

	public static boolean isEmpty(File file) {
		return file != null && file.isDirectory() && file.list().length == 0;
	}

}
