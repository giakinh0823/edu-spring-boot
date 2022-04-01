package com.example.demo.service.storage.impl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.config.StorageProperties;
import com.example.demo.exception.StorageException;
import com.example.demo.exception.StorageFileNotFoundException;
import com.example.demo.service.storage.StorageService;

public class FileSytemStorageServiceImpl implements StorageService{
	/* Xác định đường dẫn gốc dùng để lưu file */
	private final Path rootLocation;
	
	/* Tạo ra file lưu trữ dựa vào id truyền vào - mean(Lấy thông tin tên file để lưu trữ vào database) */
	@Override
	public String getStoredFileName(MultipartFile file, String id) {
		/* 
		 * Lấy type file ví dụ (.png, .jpg, .webp) 
		 * file.getOriginalFilename(): Trả về tên tile được upload tới server
		 * 
		 * */
		String ext = FilenameUtils.getExtension(file.getOriginalFilename()); 
		return "p"+id+"."+ext;
	}
	
	// Tạo contructor truyền thông tin cấu hình lưu trữ - (Đưa các cấu hình để cho phép lưu trữ)
	public FileSytemStorageServiceImpl(StorageProperties properties) {
		/* 
		 * properties.getLocation() ->uploads/images
		 * Thông tin được lấy ở application.properties
		 * 
		 * */
		this.rootLocation = Paths.get(properties.getLocation());
	}
	
	// Lưu nội dùng của file từ thành phần MultipartFile
	@Override
	public void store(MultipartFile file, String storedFilename) throws Exception {
		try {
			/* Nếu file rỗng thì ném ra ngoại lệ */
			if(file.isEmpty()) {
				throw new Exception("Failed to store empty file");
			}
			/* 
			 * - Thực hiện tính toán 
			 * - lấy thông tin của storedFilename - 
			 * - Chuẩn hóa normalize
			 * - Chuyển đường dẫn thành absolute(tuyệt đối)
			 * 
			 * */
			Path destinationFile= this.rootLocation
					.resolve(Paths.get(storedFilename))
					.normalize().toAbsolutePath();
			
			/* Nếu đường dẫn khác thì ném ra ngoại lệ - tức là lưu file ra ngoài đường dẫn root */
			if(!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
				throw new Exception("Cannot store file outside current directory");
			}
			/*Lưu file vào đường dẫn và thay thế file hiện tại nếu file đã tồn tại*/
			try(InputStream inputSteam = file.getInputStream()){
				Files.copy(inputSteam, destinationFile, StandardCopyOption.REPLACE_EXISTING);
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception("Failed to store file", e);
		}
	}
	
	// Dùng để nạp nội dung của file dưới dạng resource
	@Override
	public Resource loadAsResource(String filename) {
		try {
			Path file = load(filename);
			Resource resource = new UrlResource(file.toUri());
			if(resource.exists() || resource.isReadable()) {
				return resource;
			}
			throw new StorageFileNotFoundException("Could not read file: "+filename);
		} catch (Exception e) {
			// TODO: handle exception
			throw new StorageFileNotFoundException("Could not read file: "+filename);
		}
	}
	
	
	@Override
	public Path load(String filename) {
		return rootLocation.resolve(filename);
	}
	
	
	// Xóa file
	@Override
	public void delete(String storedFilename) throws IOException {
		Path destinationFile = rootLocation.resolve(Paths.get(storedFilename)).normalize().toAbsolutePath();
		Files.delete(destinationFile);
	}
	
	// Khởi tạo thư mục
	@Override
	public void init() {
		try {
			Files.createDirectories(rootLocation);
			System.out.println(rootLocation.toString());
		} catch (Exception e) {
			// TODO: handle exception
			throw new StorageException("Could not initialize storage ", e);
		}
	}
}
