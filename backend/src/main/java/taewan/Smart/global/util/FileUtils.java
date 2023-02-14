package taewan.Smart.global.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FileUtils {

    private static final String EMPTY = "noimg.jpeg";

    public static String findFile(String directoryPath, String root, String address) {
        File[] files = new File(root + directoryPath).listFiles();

        if (files != null) {
            for (File f : files) {
                if (f.isFile()) {
                    return address + directoryPath + "/" + f.getName();
                }
            }
        }
        return address + directoryPath + "/" + EMPTY;
    }

    public static List<String> findFiles(String directoryPath, String root, String address) {
        List<String> found = new ArrayList<>();
        File[] files = new File(root + directoryPath).listFiles();

        if (files != null) {
            for (File f : files) {
                if (f.isFile()) {
                    found.add(address + directoryPath + "/" + f.getName());
                }
            }
        } else {
            found.add(address + directoryPath + "/" + EMPTY);
        }
        return found;
    }

    public static void saveFiles(List<MultipartFile> files, String path) {
        for (MultipartFile f : files) {
            saveFile(f, path);
        }
    }

    public static String saveFile(MultipartFile file, String path) {
        String extension = file.getContentType().replaceFirst(".*/", ".");
        String uploadName = UUID.randomUUID() + extension;

        try {
            Files.createDirectories(Paths.get(path));
            file.transferTo(new File(path, uploadName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return uploadName;
    }

    public static void deleteDirectory(String directoryPath) {
        File dir = new File(directoryPath);
        File[] files = dir.listFiles();

        try {
            for (File f : files) {
                String target = directoryPath + "/" + f.getName();
                if (f.isDirectory())
                    deleteDirectory(target);
                Files.deleteIfExists(Paths.get(target));
            }
            Files.deleteIfExists(Paths.get(directoryPath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
