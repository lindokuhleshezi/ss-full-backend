package za.ac.ss.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import za.ac.ss.helper.AWSS3Properties;

@Configuration
public class AWSS3Configuration {

	@Autowired AWSS3Properties properties;
	
	@Bean
	public AmazonS3 getClientAccess() {
		AWSCredentials credentials = new BasicAWSCredentials(properties.getAccessKeyId(), properties.getSecretAccess());
		return AmazonS3ClientBuilder.standard().withRegion(Regions.valueOf(properties.getRegion()))
				.withCredentials(new AWSStaticCredentialsProvider(credentials)).build();
	}
	
}
