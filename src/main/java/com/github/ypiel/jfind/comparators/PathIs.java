package com.github.ypiel.jfind.comparators;

import com.github.ypiel.jfind.FileInfo;

import java.util.regex.Pattern;

public class PathIs implements IAcceptFile{

    private String name;
    private boolean isExact = true;
    private Pattern pattern;
    private boolean revert;

    public PathIs setName(String name) {
        this.name = name;
        this.buildPattern();

        return this;
    }

    public PathIs setParamName(String s){
        return this.setName(s);
    }

    public PathIs isExact(boolean isExactt) {
        this.isExact = isExact;
        this.buildPattern();

        return this;
    }

    public PathIs setParamExact(String exact){
        return this.isExact(Boolean.parseBoolean(exact));
    }

    public PathIs isRevert(boolean revert) {
        this.revert = revert;

        return this;
    }

    private void buildPattern(){
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

    @Override
    public String getParamName(){
        return "pathIs";
    }

    @Override
    public String getDefaultParam(){
        return "name";
    }
}
