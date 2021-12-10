package za.ac.ss.helper;

import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import io.micrometer.core.instrument.MeterRegistry;
import za.ac.ss.service.LoggingService;

@Component
public class ProjectConfig {
	@Bean
	public AWSS3Properties properties() {
		return new AWSS3Properties();
	}

	@Bean
	MeterRegistryCustomizer<MeterRegistry> metricsCommonTags() {
		return registry -> registry.config().commonTags("region", "south-africa");
	}

	@Bean
	public LoggingService loggingService() {
		return new LoggingService();
	}
}
