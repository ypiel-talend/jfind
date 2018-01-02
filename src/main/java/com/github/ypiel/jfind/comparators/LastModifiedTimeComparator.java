package com.github.ypiel.jfind.comparators;

import com.github.ypiel.jfind.FileInfo;

import java.nio.file.attribute.FileTime;

public class LastModifiedTimeComparator implements IAcceptFile {

    private FileTime ref;

    public LastModifiedTimeComparator(FileTime ref) {
        this.ref = ref;
    }


    @Override
    public boolean accept(FileInfo o) {
        return o.getAttributes().lastModifiedTime().compareTo(this.ref) > 0;
    }
}
