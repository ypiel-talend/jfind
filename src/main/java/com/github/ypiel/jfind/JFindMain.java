package com.github.ypiel.jfind;


import org.slf4j.ILoggerFactory;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class JFindMain {

    private static String rootSearchPath;

    public static void main(String[] args) {
        try {
            ILoggerFactory iLoggerFactory = LoggerFactory.getILoggerFactory();
            manageOptions(args);
            JFind jf = new JFind(rootSearchPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void manageOptions(String[] args) throws IllegalArgumentException {
        if (args.length <= 0) {
            usage();
        }

        rootSearchPath = args[0];
        Path p = Paths.get(rootSearchPath);
        if (!Files.exists(p)) {
            throw new IllegalArgumentException("The given search root folder doesn't exist '" + rootSearchPath + "'.");
        }
        if (!Files.isDirectory(p)) {
            throw new IllegalArgumentException("The given search root folder must be a folder '" + rootSearchPath + "'.");
        }

        String[] parameters = Arrays.copyOfRange(args, 1, args.length);

    }

    private static void usage() {
    }

}
