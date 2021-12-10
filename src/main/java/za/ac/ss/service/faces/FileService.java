package za.ac.ss.service.faces;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;

import za.ac.ss.entities.FileStore;
import za.ac.ss.exception.MyFileNotFoundException;
import za.ac.ss.exception.ResourceNotFoundException;


public interface FileService {
	//public FileStore updateFile(Long id, List<MultipartFile> file, Long categoryId) throws ResourceNotFoundException;
	//public Resource download(String filename) throws MalformedURLException, MyFileNotFoundException;
	public Page<FileStore> findAll(PageRequest pageRequest);
	public FileStore findByName(String name);
	public void ApprovalOrReject(Long id, String status);
	public List<FileStore> findPendingApprovalDocuments();
	public List<FileStore> getDocumentByRaf(Long id);
	Optional<FileStore> internalFileStoreFindById(Long id);
	public URI download(String filename, HttpServletResponse response) throws MyFileNotFoundException, IOException;
	public void deleteDocument(String name);
	public void updateFile(String rafId, String[] categoryId, MultipartFile[] file) throws ResourceNotFoundException;
}
