package com.jimuv.common.util;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtils {

    public static void createDirectory(String folderDirectoryPath) {
        Path path = Paths.get(folderDirectoryPath);
        if (Files.notExists(path)) {
            try {
                Files.createDirectories(path);
            } catch (Exception e) {
                System.err.println("createDirectory error：" + e.getMessage());
            }
        }
    }

}
