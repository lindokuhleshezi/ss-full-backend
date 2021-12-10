package za.ac.ss.service;

import java.io.IOException;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;
import za.ac.ss.entities.Category;
import za.ac.ss.entities.FileStore;
import za.ac.ss.entities.RoadAccidentFund;
import za.ac.ss.entities.Users;
import za.ac.ss.enums.DocumentProcess;
import za.ac.ss.exception.MyFileNotFoundException;
import za.ac.ss.exception.ResourceNotFoundException;
import za.ac.ss.helper.AWSS3Properties;
import za.ac.ss.helper.S3Util;
import za.ac.ss.repository.DocumentRepository;
import za.ac.ss.repository.RoadAccidentFundRepository;
import za.ac.ss.service.faces.CategoryService;
import za.ac.ss.service.faces.FileService;
import za.ac.ss.service.faces.UserService;

@Slf4j
@Service
public class FileServiceImpl implements FileService {

	@Autowired
	private DocumentRepository documentDao;
	@Autowired
	private RoadAccidentFundRepository roadAccidentFundDao;
	@Autowired
	private AWSS3Properties properties;
	@Autowired
	CategoryService catetegoryService;
	@Autowired
	private S3Util s3Util;
	@Autowired
	private UserService userService;

	@Override
	@Transactional
	public void updateFile(String rafId, String[] categoryId, MultipartFile[] file) throws ResourceNotFoundException {
		int index = 0;
		String fileName = null;
		
		try {
			if ((file.length > 0)) {
				for (MultipartFile f : file) {
					Optional<RoadAccidentFund> roadAccident = isRoadAccidentRecordExist(Long.valueOf(rafId));
					Optional<Category> category = catetegoryService.getCategory(Long.valueOf(categoryId[index]));
					fileName = createFileName(roadAccident, f, category);

					log.info(
							"updateFile method trigger:::: Road Accident No: {} Category Id: {} File: {} FileName: {} at {}",
							roadAccident.get(), category, f, fileName, LocalDateTime.now());

					this.createDocument(roadAccident, category, f, fileName, loggedUser());
					this.createFolderName(roadAccident, f, fileName);
					log.info("file " + fileName);
					index++;
				}
			}
		} catch (Exception e) {
			log.error("updateFile Method::: Looks like no file is attached at {}", LocalDateTime.now());
			throw new IllegalStateException("No file attached");
		}
	}

	private DocumentProcess loggedUser() throws ResourceNotFoundException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		System.out.println("username " + username);
		Optional<Users> loggedUser = userService.findByUsername(username);
		DocumentProcess status;
		if (loggedUser.get().getRoles().stream().anyMatch(a -> a.getName().equals("Admin"))) {
			status = DocumentProcess.APPROVED;
		} else if (loggedUser.get().getRoles().stream().anyMatch(
				a -> a.getName().equals("Customer") || a.getName().equals("User") || a.getName().equals("Employee"))) {
			status = DocumentProcess.PENDING;
		} else {
			log.error("createDocument method trigger:: at {}", LocalDateTime.now());
			throw new IllegalStateException("User doesn't have a role");
		}
		return status;
	}

	private void createDocument(Optional<RoadAccidentFund> roadAccident, Optional<Category> category,
			MultipartFile multipartFile, String fileName, DocumentProcess documentProcess) throws ResourceNotFoundException {
		log.info("file " + multipartFile.getOriginalFilename());
		FileStore fileStore = FileStore.builder().name(fileName).path(createPath(roadAccident))
				.size(multipartFile.getSize()).roadAccidentFund(roadAccident.get()).type(multipartFile.getContentType())
				.documentProcess(documentProcess).category(category.get()).version(0L).build();
		this.documentDao.save(fileStore);
	}

	private String createPath(Optional<RoadAccidentFund> roadAccident) {
		return roadAccident.get().getReferenceNumber().replace("/", "");
	}

	private String createFileName(Optional<RoadAccidentFund> roadAccident, MultipartFile multipartFile,
			Optional<Category> category) {
		String fileName = createPath(roadAccident) + "_" + category.get().getName();
		String name = fileName + "." + multipartFile.getOriginalFilename()
				.replaceAll("[-+/^,]","_")
				.substring(multipartFile.getOriginalFilename().lastIndexOf(".") + 1);
		return name;
	}

	private void createFolderName(Optional<RoadAccidentFund> roadAccident, MultipartFile multipartFile,
			String fileName) {
		String folderName = properties.getBucketName() + "/" + createPath(roadAccident);
		this.s3Util.uploadFileToS3Bucket(folderName, multipartFile, fileName);
	}

	// Check Road Accident Record exists using an id Road Accident Fund
	private Optional<RoadAccidentFund> isRoadAccidentRecordExist(Long id) {
		Optional<RoadAccidentFund> roadAccident = roadAccidentFundDao.findById(id);
		roadAccident.isPresent();
		roadAccident
				.orElseThrow(() -> new IllegalStateException(String.format("Road Accident Fund %s is not found", id)));
		return roadAccident;
	}

	@Override
	public URI download(String filename, HttpServletResponse response) throws MyFileNotFoundException, IOException {
		Optional<FileStore> fileStore = findFileByName(filename);
		return this.s3Util.downloadFileFromS3Bucket(properties.getBucketName(), fileStore.get().getPath() + "/",
				fileStore.get().getName());
	}

	private Optional<FileStore> findFileByName(String filename) {
		Optional<FileStore> fileStore = this.documentDao.findByName(filename);
		fileStore.isPresent();
		fileStore.orElseThrow(() -> new IllegalStateException(String.format("Document %s is not found", filename)));
		return fileStore;
	}

	@Override
	public Page<FileStore> findAll(PageRequest pageRequest) {
		PageRequest pq = PageRequest.of(0, 5);
		return documentDao.findAll(pq);
	}

	@Override
	public FileStore findByName(String name) {
		Optional<FileStore> fileStore = findFileByName(name);
		return fileStore.get();
	}

	// Finding Road Accident Fund and Status update for File Store
	@Override
	public Optional<FileStore> internalFileStoreFindById(Long id) {
		Optional<FileStore> roadReferenceNo = this.documentDao.findById(id);
		roadReferenceNo.isPresent();
		roadReferenceNo.orElseThrow(() -> new IllegalStateException(String.format("Document %s is not found", id)));
		return roadReferenceNo;
	}

	@Override
	public void ApprovalOrReject(Long id, String status) {
		// Step 1 (find document record)
		Optional<FileStore> roadReferenceNo = this.internalFileStoreFindById(id);
		roadReferenceNo.get().setDocumentProcess(DocumentProcess.valueOf(status));
		// Step 2 (Approve or Reject)
		FileStore fileStore = this.documentDao.save(roadReferenceNo.get());
		log.info("fileStore {}", fileStore);
		// Step 3 (Send an email)
	}

	@Override
	public List<FileStore> findPendingApprovalDocuments() {
		return this.documentDao.findByDocumentProcess(DocumentProcess.PENDING);
	}

	@Override
	public List<FileStore> getDocumentByRaf(Long id) {
		return this.documentDao.findAllByRoadAccidentFundId(id);
	}

	@Override
	@Transactional
	public void deleteDocument(String name) {
	Optional<FileStore> fileStore = findFileByName(name);
	this.s3Util.deleteFile(properties.getBucketName(), fileStore.get().getPath() + "/",name);
	this.documentDao.deleteByName(name);
	}
	

}
