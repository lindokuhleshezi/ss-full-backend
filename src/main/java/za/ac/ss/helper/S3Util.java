package za.ac.ss.helper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;

import lombok.extern.slf4j.Slf4j;
import za.ac.ss.config.AWSS3Configuration;

@Slf4j
@Component
public class S3Util {


	@Autowired private AWSS3Configuration awss3Configuration; 
	
	public void createFolder(final String bucketName, String path) {
		PutObjectRequest request = new PutObjectRequest(bucketName, path, path);
		awss3Configuration.getClientAccess().putObject(request);
	}  
	
    public void uploadFileToS3Bucket(final String bucketName, final MultipartFile multipartFile, String filename) {
    	File file = convertMultiPartFileToFile(multipartFile);
        awss3Configuration.getClientAccess().putObject(bucketName, filename, file);
    }
    
    public void deleteFile(String bucketName, String foldername, String filename) {
    	awss3Configuration.getClientAccess().deleteObject(bucketName, foldername + filename);
    }
    
    private File convertMultiPartFileToFile(final MultipartFile multipartFile) {
        final File file = new File(multipartFile.getOriginalFilename());
        try (final FileOutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write(multipartFile.getBytes());
        } catch (final IOException ex) {
            log.error("Error converting the multi-part file to file= ", ex.getMessage());
        }
        return file;
    }
    
    public URI downloadFileFromS3Bucket(String bucketName, String foldername, String filename) throws IOException {
    	S3Object s3object = awss3Configuration.getClientAccess().getObject(new GetObjectRequest(bucketName, foldername + filename));
		S3ObjectInputStream inputStream = s3object.getObjectContent();
		return inputStream.getHttpRequest().getURI();
    }
}
