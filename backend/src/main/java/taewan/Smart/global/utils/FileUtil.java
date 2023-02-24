package taewan.Smart.global.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static taewan.Smart.global.utils.PropertyUtil.getAccessImgUrl;
import static taewan.Smart.global.utils.PropertyUtil.getImgFolderPath;

@Slf4j
public class FileUtil {
    private static final String EMPTY_PATH = "empty/noimg.jpeg";

    public static String getAccessUrl(String filePath) {
        return getAccessImgUrl() + (findFile(filePath) == null ? EMPTY_PATH : filePath);
    }

    public static List<String> getAccessUrls(String directoryPath) {
        return findFiles(directoryPath).stream()
                .map(f -> getAccessImgUrl() + (f == null ? EMPTY_PATH : directoryPath + f))
                .collect(Collectors.toList());
    }

    public static String findFile(String filePath) {
        try {
            File file = new File(getImgFolderPath() + filePath);

            if (file.isFile())
                return file.getName();
        } catch (NullPointerException ex) {
            log.error("[파일 경로가 유효하지 않습니다.] : {}", "요청 경로 : " + filePath);
        }
        return null;
    }

    public static List<String> findFiles(String directoryPath) {
        List<String> found = new ArrayList<>();

        try {
            File[] files = new File(getImgFolderPath() + directoryPath).listFiles();

            for (File f : files) {
                if (f.isFile()) {
                    found.add(f.getName());
                }
            }
        } catch (NullPointerException ex) {
            log.error("[디렉토리 경로가 유효하지 않습니다.] : {}", "요청 경로 : " + directoryPath);
            found.add(null);
        }
        return found;
    }

    public static List<String> saveFiles(List<MultipartFile> files, String directoryPath) {
        List<String> uploadNames = new ArrayList<>();

        for (MultipartFile f : files) {
            uploadNames.add(saveFile(f, directoryPath));
        }
        return uploadNames;
    }

    public static String saveFile(MultipartFile file, String directoryPath) {
        String extension = file.getContentType().replaceFirst(".*/", ".");
        String uploadName = UUID.randomUUID() + extension;
        String path = getImgFolderPath() + directoryPath;

        try {
            Files.createDirectories(Paths.get(path));
            file.transferTo(new File(path, uploadName));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        return uploadName;
    }

    public static void deleteDirectory(String directoryPath) {
        deleteAllFiles(getImgFolderPath() + directoryPath);
    }

    private static void deleteAllFiles(String directoryPath) {
        File dir = new File(directoryPath);
        File[] files = dir.listFiles();

        try {
            for (File f : files) {
                String target = directoryPath + f.getName();
                log.info(target);
                if (f.isDirectory())
                    deleteAllFiles(target + "/");
                Files.deleteIfExists(Paths.get(target));
                log.info("[파일을 삭제했습니다.] : {}", "삭제 파일 : " + target);
            }
            Files.deleteIfExists(Paths.get(directoryPath));
        } catch (NullPointerException ex1) {
            log.error("[디렉토리 경로가 유효하지 않습니다.] : {}", "요청 경로 : " + directoryPath);
        } catch (IOException ex2) {
            throw new RuntimeException(ex2);
        }
    }
}
