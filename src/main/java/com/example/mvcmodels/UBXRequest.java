package com.example.mvcmodels;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UBXRequest {

	@Size(min=1, max=10)
	@NotNull
	private String id;
	
	@Size(min=3, max=30)
	@NotNull
	private String name;
	
	@Size(min=3, max=30)
	@NotNull
	private String filename;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	@Override
	public String toString() {
		return "UBXRequest [id=" + id + ", name=" + name + ", filename=" + filename + "]";
	}
	
	
}

