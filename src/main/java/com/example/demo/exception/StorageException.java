package com.example.demo.exception;

public class StorageException extends RuntimeException {
	public StorageException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public StorageException(String message, Exception e) {
		// TODO Auto-generated constructor stub
		super(message, e);
	}
	
}
