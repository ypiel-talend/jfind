package com.github.ypiel.jfind.comparators;

import  com.github.ypiel.jfind.FileInfo;
import com.github.ypiel.jfind.JFind;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class CreatedAfterLocalTimeComparator implements IAcceptFile {

    private LocalDateTime ldt;

    public CreatedAfterLocalTimeComparator setLocalDateTime(LocalDateTime ldt){
        this.ldt = ldt;
        return this;
    }

    public CreatedAfterLocalTimeComparator setParamDate(String s){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(JFind.DATE_FORMAT);
        return this.setLocalDateTime(LocalDateTime.parse(s, formatter));
    }

    @Override
    public boolean accept(FileInfo fi) {
        return ldt.toInstant(ZoneOffset.UTC).isBefore(fi.getAttributes().lastModifiedTime().toInstant());
    }

    @Override
    public String getParamName(){
        return "createdAfter";
    }

    @Override
    public String getDefaultParam(){
        return "date";
    }
}
