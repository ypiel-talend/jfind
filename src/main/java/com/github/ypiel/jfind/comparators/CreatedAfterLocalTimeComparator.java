package com.github.ypiel.jfind.comparators;

import  com.github.ypiel.jfind.FileInfo;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class CreatedAfterLocalTimeComparator implements IAcceptFile {

    private LocalDateTime ldt;

    public CreatedAfterLocalTimeComparator(LocalDateTime ldt){
        this.ldt = ldt;
    }

    @Override
    public boolean accept(FileInfo fi) {
        return ldt.toInstant(ZoneOffset.UTC).isBefore(fi.getAttributes().lastModifiedTime().toInstant());
    }
}
