package com.github.ypiel.jfind;

import com.github.ypiel.jfind.comparators.IAcceptFile;
import com.github.ypiel.jfind.comparators.LastModifiedTimeComparator;
import com.github.ypiel.jfind.comparators.NameIs;
import com.github.ypiel.jfind.comparators.PathIs;
import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class JFind {

    public enum CRITERIA{
        OLDER_THAN, NAME_IS
    }

    public final static String DATE_FORMAT = "YYYY-MM-dd HH:mm:ss";

    private Path searchRoot;
    private Map<String, IAcceptFile> compartors = new HashMap<>();
    private List<IAcceptFile> criteria = new ArrayList<>();


    public JFind(String path){
        this.searchRoot = Paths.get(path);
        this.loadComparators();
    }

    private void loadComparators(){
        try{
            ClassPath cp = ClassPath.from(ClassLoader.getSystemClassLoader());
            ImmutableSet<ClassPath.ClassInfo> topLevelClasses = cp.getTopLevelClasses("com.github.ypiel.jfind.comparators");
            for(ClassPath.ClassInfo ci : topLevelClasses){
                Class<?> clazz = ci.load();
                if(!clazz.isInterface()){
                    Constructor<?> c = clazz.getConstructor();
                    IAcceptFile iaf = (IAcceptFile)c.newInstance();
                    compartors.put(iaf.getParamName(), iaf);
                }
            }
        }
        catch(Exception e){
            System.out.println("Can't load files comparators : "+e.getMessage());
            e.printStackTrace();
        }
    }


    /*public JFind olderThan(int type, int amount){
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(type, amount*-1);
        this.olderThan(c.getTime());
        return this;
    }

    public JFind olderThan(Date d){
        LastModifiedTimeComparator lastModifiedTimeComparator = new LastModifiedTimeComparator();
        lastModifiedTimeComparator.setFileTime(FileTime.fromMillis(d.getTime()));
        criteria.add(lastModifiedTimeComparator);
        return this;
    }

    public JFind nameIs(String name, boolean isExact, boolean revert){
        NameIs ni = new NameIs();
        ni.setName(name).isExact(isExact).isRevert(revert);
        criteria.add(ni);
        return this;
    }

    public JFind pathIs(String name, boolean isExact, boolean revert){
        PathIs pi = new PathIs();
        pi.setName(name).isExact(isExact).isRevert(revert);
        criteria.add(pi);
        return this;
    }*/

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
