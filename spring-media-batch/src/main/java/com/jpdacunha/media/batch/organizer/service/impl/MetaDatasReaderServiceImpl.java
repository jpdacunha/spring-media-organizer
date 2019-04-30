package com.jpdacunha.media.batch.organizer.service.impl;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.jpdacunha.media.batch.organizer.service.MetaDatasReaderService;

@Service
public class MetaDatasReaderServiceImpl implements MetaDatasReaderService {
	
	private static final String FILE_MODIFIED_DATE = "File Modified Date";
	private static Logger log = LoggerFactory.getLogger(MetaDatasReaderServiceImpl.class);
	
	
	public String readModifiedDateAsString(File file) {
		
		String content = readMetadatas(file, FILE_MODIFIED_DATE);
		
		return content;
		
	}
	
	public Date readModifiedDate(File file) {

		try {
			String date = readMetadatas(file, FILE_MODIFIED_DATE);
			if (date == null) {
				return null;
			}
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
			formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
			return formatter.parse(date);
		} catch (ParseException e) {
			log.error("failed to parse date taken", e);
		}
		return null;
	}
	
	public String readMetadatas(File file, String metadateName) {
		
		try {
			Metadata metadata = ImageMetadataReader.readMetadata(file);
			
			for (Directory directory : metadata.getDirectories()) {
				for (Tag tag : directory.getTags()) {
			    	if (tag.getTagName().equals(metadateName)) {
			    		return tag.getDescription();
			    	}
			    }
			    
			}
			
		} catch (ImageProcessingException | IOException e) {
			log.error(e.getMessage(), e);
		}

		return null;
		
	}

//	/**
//	 * Write all extracted values to stdout.
//	 */
//	private static void print(Metadata metadata, String method) {
//		System.out.println();
//		System.out.println("-------------------------------------------------");
//		System.out.print(' ');
//		System.out.print(method);
//		System.out.println("-------------------------------------------------");
//		System.out.println();
//
//		//
//		// A Metadata object contains multiple Directory objects
//		//
//		for (Directory directory : metadata.getDirectories()) {
//
//			//
//			// Each Directory stores values in Tag objects
//			//
//			for (Tag tag : directory.getTags()) {
//				System.out.println(tag);
//			}
//
//			//
//			// Each Directory may also contain error messages
//			//
//			for (String error : directory.getErrors()) {
//				System.err.println("ERROR: " + error);
//			}
//		}
//	}
	
	
}
