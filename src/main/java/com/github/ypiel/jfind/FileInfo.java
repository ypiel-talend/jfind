package com.github.ypiel.jfind;

import java.nio.file.attribute.BasicFileAttributes;

public class FileInfo {

    private String name;
    private BasicFileAttributes attributes;
    private String path;

    public FileInfo(String name, String path, BasicFileAttributes attributes) {
        this.name = name;
        this.attributes = attributes;
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public BasicFileAttributes getAttributes() {
        return attributes;
    }

    public String getPath() {
        return path;
    }
}
