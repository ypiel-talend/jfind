package com.github.ypiel.jfind;

import com.github.ypiel.jfind.comparators.IAcceptFile;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class SelectFileVisitor extends SimpleFileVisitor<Path> {

    List<IAcceptFile> criteria;
    private AtomicInteger nbFiles;

    public SelectFileVisitor(List<IAcceptFile> criteria, AtomicInteger nbFiles){
        this.criteria = criteria;
        this.nbFiles = nbFiles;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        FileInfo fi = new FileInfo(file.getFileName().toString(), file.toAbsolutePath().getParent().toString(), attrs);
        boolean accepted = this.checkCriteria(fi);
        if(accepted){
            System.out.println("\t - File :" + file.toAbsolutePath().toString()+" " + attrs.lastModifiedTime().toString());
        }
        this.nbFiles.incrementAndGet();

        return FileVisitResult.CONTINUE;
    }

    private boolean checkCriteria(FileInfo fi){
        boolean accept = true;

        Iterator<IAcceptFile> it = criteria.iterator();
        while(it.hasNext() && accept){
            IAcceptFile iaf = it.next();
            accept = accept && iaf.accept(fi);
        }

        return accept;
    }

}
