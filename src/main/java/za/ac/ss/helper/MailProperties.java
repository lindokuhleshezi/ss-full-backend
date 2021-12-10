package za.ac.ss.helper;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties("slabbert.mail")
public class MailProperties {
	
	    private String defaultEncoding;
	    private String host;
	    private String username;
	    private String password;
	    private Integer port;
	    private Map<String, String> properties;
	    private String protocol;
	    private String testConnection;
	    private String fromAddress;
}
