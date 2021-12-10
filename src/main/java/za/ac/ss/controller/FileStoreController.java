package za.ac.ss.controller;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import za.ac.ss.entities.FileStore;
import za.ac.ss.enums.DocumentProcess;
import za.ac.ss.exception.MyFileNotFoundException;
import za.ac.ss.exception.ResourceNotFoundException;
import za.ac.ss.service.faces.FileService;

@RestController
@RequestMapping("/document")
public class FileStoreController {

	@Autowired
	private FileService fileService;

	@PostMapping(path = "/upload/category", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public void uploadRafDocuments(@RequestPart("file") MultipartFile[] file,
			@RequestParam("rafId") String rafId, @RequestParam("categoryId") String[] categoryId)
			throws ResourceNotFoundException, IOException {
		fileService.updateFile(rafId, categoryId, file);
	}

	@GetMapping
	public Page<FileStore> getDocuments() {
		PageRequest pageRequest = PageRequest.of(0, 5);
		return this.fileService.findAll(pageRequest);
	}

	@GetMapping(path="/download/documents/{name}")
	public URI  getDocuments(@PathVariable("name") String name,
			HttpServletResponse response) throws MyFileNotFoundException, IOException {
		return this.fileService.download(name, response);
	}

	@PostMapping("/verify/{id}")
	public void approveOrReject(@PathVariable Long id, @RequestBody String status) {
		this.fileService.ApprovalOrReject(id, status); // Reject or Approval
	}

	@GetMapping("/pending-documents")
	public List<FileStore> getPendingDocuments() {
		return this.fileService.findPendingApprovalDocuments();
	}

	@GetMapping("/statuses")
	public DocumentProcess[] getStatuses() {
		return DocumentProcess.values();
	}

	@DeleteMapping("/remove/{name}/document")
	public void removeDocument(@PathVariable String name) {
		this.fileService.deleteDocument(name);
	}

	@GetMapping("/document-raf/{id}")
	public List<FileStore> getDocuments(@PathVariable Long id) {
		return this.fileService.getDocumentByRaf(id);
	}

	@GetMapping("/{id}")
	public Optional<FileStore> getDocument(@PathVariable Long id) {
		return this.fileService.internalFileStoreFindById(id);
	}

}
