package taewan.Smart.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FileUtils {

    public static List<String> findImgFiles(String directoryPath, String root, String address) {
        List<String> found = new ArrayList<>();
        File dir = new File(root + directoryPath);
        File[] files = dir.listFiles();

        for (File f : files)
            if (f.isFile())
                found.add(address + directoryPath + "/" + f.getName());
        return found;
    }

    public static void saveImgFiles(List<MultipartFile> files, String path) {
        for (MultipartFile f : files)
            saveImgFile(f, path);
    }

    public static String saveImgFile(MultipartFile file, String path) {
        String extension = file.getContentType().replaceFirst(".*/", ".");
        String uploadName = UUID.randomUUID().toString() + extension;

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
