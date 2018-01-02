package com.github.ypiel.jfind;

import com.github.ypiel.jfind.comparators.IAcceptFile;
import com.github.ypiel.jfind.comparators.LastModifiedTimeComparator;
import com.github.ypiel.jfind.comparators.NameIs;
import com.github.ypiel.jfind.comparators.PathIs;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class JFind {
    public enum CRITERIA{
        OLDER_THAN, NAME_IS
    }

    private Path searchRoot;
    private List<IAcceptFile> criteria = new ArrayList<>();


    public JFind(String path){
        this.searchRoot = Paths.get(path);
    }


    public JFind olderThan(int type, int amount){
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(type, amount*-1);
        this.olderThan(c.getTime());
        return this;
    }

    public JFind olderThan(Date d){
        criteria.add(new LastModifiedTimeComparator(FileTime.fromMillis(d.getTime())));
        return this;
    }

    public JFind nameIs(String name, boolean isExact, boolean revert){
        criteria.add(new NameIs(name, isExact, revert));
        return this;
    }

    public JFind pathIs(String name, boolean isExact, boolean revert){
        criteria.add(new PathIs(name, isExact, revert));
        return this;
    }

    public void getFilesForPatch(){
        try {
            System.out.println("* Get Files for patch :");
            AtomicInteger nbFiles = new AtomicInteger(0);
            SelectFileVisitor sfv = new SelectFileVisitor(this.criteria, nbFiles);
            Files.walkFileTree(searchRoot, sfv);
            System.out.println("\t\t\t ----- Nb Files : "+nbFiles.get());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
