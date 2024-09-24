package com.jpdacunha.media.batch.organizer.model.strategy;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
public class ClassificationStrategyConfiguration {
	
	@Bean
    @Order(1)
	@Qualifier("ClassificationStrategyList")
	public ClassificationStrategy getFirst() {
		return new LastModififiedClassificationStrategy();
	}
	
	@Bean
    @Order(2)
	@Qualifier("ClassificationStrategyList")
	public ClassificationStrategy getSecond() {
		return new FilenameClassificationStrategy();
	}

}