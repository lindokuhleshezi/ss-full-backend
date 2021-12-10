package za.ac.ss.helper;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties("slabbert.aws.s3")
public class AWSS3Properties {

	private String accessKeyId;
	private String secretAccess;
	private String bucketName;
	private String region;

}
