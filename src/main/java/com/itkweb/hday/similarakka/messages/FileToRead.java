package com.itkweb.hday.similarakka.messages;

import java.io.Serializable;

public class FileToRead implements Serializable {

	private static final long serialVersionUID = -3802794032162167325L;
	

	private String filename;
	
	public FileToRead(String fn) {
		filename = fn;
	}

	public String getFilename() {
		return filename;
	}
	
}
