package com.github.ypiel.jfind;

import org.apache.commons.cli.*;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class JFindMain {

    private static String rootSearchPath;

    public static void main(String[] args) {
        try {
            manageOptions(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void manageOptions(String[] args) throws IllegalArgumentException, ParseException {
        Options options = buildOptions();

        if (args.length <= 0) {
            usage(options);
        }

        String root = args[0];
        Path p = Paths.get(root);
        if (!Files.exists(p)) {
            throw new IllegalArgumentException("The given search root folder doesn't exist '" + root + "'.");
        }
        if (!Files.isDirectory(p)) {
            throw new IllegalArgumentException("The given search root folder must be a folder '" + root + "'.");
        }

        String[] parameters = Arrays.copyOfRange(args, 1, args.length);

        CommandLineParser parser = new DefaultParser();
        CommandLine line = parser.parse(options, parameters, true);



    }

    private static Options buildOptions() {
        Options options = new Options();
        options.addOption("ot", true, "Search for files older than...");
        options.addOption("name", true, "Search file with their name matching the given regex");
        options.addOption("path", true,"Search for files into a path matching this regex");
        return options;
    }

    private static void usage(Options options) {
        HelpFormatter hf = new HelpFormatter();
        hf.printHelp("jfind rootSearchPath", options);
        System.exit(0);
    }

}
