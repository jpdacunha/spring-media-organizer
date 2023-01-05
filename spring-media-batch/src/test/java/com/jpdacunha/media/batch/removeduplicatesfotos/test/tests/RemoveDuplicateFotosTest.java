package com.jpdacunha.media.batch.removeduplicatesfotos.test.tests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jpdacunha.media.batch.organizer.test.core.RootTest;
import com.jpdacunha.media.batch.removeduplicatesfotos.configuration.RemoveDuplicatesFotosYamlConfiguration;
import com.jpdacunha.media.batch.removeduplicatesfotos.service.RemoveDuplicateImagesService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles(profiles = "test")
public class RemoveDuplicateFotosTest extends RootTest {
	
	@Autowired
	private RemoveDuplicateImagesService service;
	
	@Autowired
	private RemoveDuplicatesFotosYamlConfiguration configuration;
	
	@Test
	public void RemoveDuplicatesNominal() {
		
		String testName =  new Object(){}.getClass().getEnclosingMethod().getName();
		
		super.removeImageDuplicates(configuration, service, testName);
		
	}
	
	@Test
	public void RemoveDuplicatesNominalNoDuplication() {
		
		String testName =  new Object(){}.getClass().getEnclosingMethod().getName();
		
		super.removeImageDuplicates(configuration, service, testName);
		
	}

}
