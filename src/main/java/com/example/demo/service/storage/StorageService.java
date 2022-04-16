package com.example.demo.service.storage;

import java.io.IOException;
import java.nio.file.Path;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

	void init();

	void delete(String storedFilename) throws IOException;

	Path load(String filename);

	Resource loadAsResource(String filename);

	void store(MultipartFile file, String storedFilename) throws Exception;

	String getStoredFileName(MultipartFile file, String id);

	void setRootLocation(String path);

}
