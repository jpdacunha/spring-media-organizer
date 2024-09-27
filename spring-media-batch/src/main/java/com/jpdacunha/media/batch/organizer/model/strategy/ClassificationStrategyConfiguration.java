package com.jpdacunha.media.batch.organizer.model.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import com.jpdacunha.media.batch.organizer.configuration.MediaBatchYamlConfiguration;

@Configuration
public class ClassificationStrategyConfiguration {
	
	@Autowired
	private MediaBatchYamlConfiguration configuration;
	
	@Bean
    @Order(1)
	@Qualifier("ClassificationStrategyList")
	public ClassificationStrategy getSecond() {
		return new FilenameDatePatternClassificationStrategy(configuration);
	}
	
	@Bean
    @Order(2)
	@Qualifier("ClassificationStrategyList")
	public ClassificationStrategy getFirst() {
		return new LastModififiedClassificationStrategy(configuration);
	}

}
