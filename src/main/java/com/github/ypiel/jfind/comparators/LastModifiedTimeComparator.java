package com.github.ypiel.jfind.comparators;

import com.github.ypiel.jfind.FileInfo;
import com.github.ypiel.jfind.JFind;

import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LastModifiedTimeComparator implements IAcceptFile {

    private FileTime ref;

    public LastModifiedTimeComparator setFileTime(FileTime ref) {
        this.ref = ref;

        return this;
    }

    public LastModifiedTimeComparator setParamDate(String date){
        try{
            SimpleDateFormat sdf = new SimpleDateFormat(JFind.DATE_FORMAT);
            Date d = sdf.parse(date);
            FileTime ft = FileTime.fromMillis(d.getTime());
            this.setFileTime(ft);
        }
        catch(Exception e){
            e.printStackTrace();
        }

        return this;
    }


    @Override
    public boolean accept(FileInfo o) {
        return o.getAttributes().lastModifiedTime().compareTo(this.ref) > 0;
    }

    @Override
    public String getParamName(){
        return "modifiedAfter";
    }

    @Override
    public String getDefaultParam(){
        return "date";
    }

}
