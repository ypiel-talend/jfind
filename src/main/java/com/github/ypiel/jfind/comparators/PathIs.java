package com.github.ypiel.jfind.comparators;

import com.github.ypiel.jfind.FileInfo;

import java.util.regex.Pattern;

public class PathIs implements IAcceptFile{

    private String name;
    private boolean isExact = true;
    private Pattern pattern;
    private boolean revert;

    public PathIs(String name, boolean isExact, boolean revert) {
        this.name = name;
        this.isExact = isExact;
        this.revert = revert;

        if(!isExact){
            pattern = Pattern.compile(name);
        }
    }

    @Override
    public boolean accept(FileInfo fi) {
        boolean accept = false;
        if(isExact){
            accept = name.equals(fi.getPath());
        }
        else{
            accept = pattern.matcher(fi.getPath()).find();
        }

        if(revert){
            accept = !accept;
        }

        return accept;
    }
}
