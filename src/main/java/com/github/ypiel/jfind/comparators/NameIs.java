package com.github.ypiel.jfind.comparators;

import com.github.ypiel.jfind.FileInfo;

import java.util.regex.Pattern;

public class NameIs implements IAcceptFile{

    private String name;
    private boolean isExact = true;
    private Pattern pattern;
    private boolean revert;

    public NameIs setName(String nam) {
        this.name = name;
        this.buildPattern();

        return this;
    }

    public NameIs setParamName(String s){
        return this.setName(s);
    }

    public NameIs isExact(boolean isExact) {
        this.isExact = isExact;
        this.buildPattern();

        return this;
    }

    public NameIs setParamExact(String s){
        return this.isExact(Boolean.parseBoolean(s));
    }

    public NameIs isRevert(boolean revert) {
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
            accept = name.equals(fi.getName());
        }
        else{
            accept = pattern.matcher(fi.getName()).find();
        }

        if(revert){
            accept = !accept;
        }

        return accept;
    }

    @Override
    public String getParamName(){
        return "nameIs";
    }

    @Override
    public String getDefaultParam(){
        return "name";
    }
}
