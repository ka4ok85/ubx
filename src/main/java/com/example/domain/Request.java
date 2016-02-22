package com.example.domain;

public class Request {
    private String id;
    private String name;
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
        return "Request [id=" + id + ", name=" + name + ", filename=" + filename + "]";
    }
}

