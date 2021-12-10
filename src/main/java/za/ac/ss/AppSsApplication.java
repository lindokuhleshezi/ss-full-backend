package za.ac.ss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

import io.micrometer.core.instrument.MeterRegistry;
import za.ac.ss.helper.AWSS3Properties;
import za.ac.ss.helper.MailProperties;

@EnableConfigurationProperties(MailProperties.class)
@SpringBootApplication
@ComponentScan(basePackages = { "za.ac.ss", "za.ac.ss.helper", "za.ac.ss.config", "za.ac.ss.repository",
		"za.ac.ss.repository.specification", "za.ac.ss.repository.specification.metamodel", "za.ac.ss.entities",
		"za.ac.ss.dto", "za.ac.ss.exception", "za.ac.ss.controller", "za.ac.ss.service" })
@EnableJpaAuditing
@EnableAuthorizationServer
@EnableResourceServer
@EnableScheduling
public class AppSsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppSsApplication.class, args);
	}

}
