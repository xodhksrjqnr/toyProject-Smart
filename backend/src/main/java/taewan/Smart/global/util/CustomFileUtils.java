package taewan.Smart.global.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 * 해당 클래스는 파일에 대한 저장, 조회, 제거 기능을 처리하기 위해 구현되었다.
 */
public class CustomFileUtils {

    /**
     * 유효 파일 탐색 과정에서 발생할 수 있는 비효율적인 저장 방식의 개선과 스프링의 멀티 쓰레드 환경을 고려한
     * 필드이다.
     */
    private static final ThreadLocal<List<String>> foundImage = new ThreadLocal<>();

    /**
     * recursivelyFind 메서드를 호출하기 전과 후에 ThreadLocal에 대해 처리를 수행하는 메서드이다.
     * @param path : 파일을 탐색할 경로
     * @return : 탐색 과정에서 확인한 모든 파일들의 전체 경로
     */
    public static List<String> findFilePaths(String path) {
        foundImage.set(new ArrayList<>());
        recursivelyFind(path);

        List<String> found = new ArrayList<>(foundImage.get());

        foundImage.remove();
        return found;
    }

    /**
     * 해당 경로 하위에 존재하는 모든 파일들을 재귀적으로 탐색 및 찾은 파일의 전체 경로를 저장한다.
     * @param path : 파일을 탐색할 경로
     */
    private static void recursivelyFind(String path) {
        File[] files = new File(path).listFiles();

        if (files == null) {
            return;
        }
        for (File f : files) {
            String target = path + "/" + f.getName();

            if (f.isDirectory()) {
                recursivelyFind(target);
            }
            foundImage.get().add(target);
        }
    }

    /**
     * 요청으로 받은 MultipartFile 타입의 파일 각각을 saveFile 메서드의 매개변수로 전달한다.
     * @param files : 클라이언트 요청으로 받은 MultipartFile 타입의 파일들
     * @param path : 파일을 저장할 경로
     * @return 저장된 파일들의 파일명 반환
     */
    public static List<String> saveFiles(List<MultipartFile> files, String path) {
        List<String> uploadNames = new ArrayList<>();

        for (MultipartFile f : files) {
            uploadNames.add(saveFile(f, path));
        }
        return uploadNames;
    }

    /**
     * MultipartFile에서 추출한 확장자와 UUID로 생성한 문자열을 이용해 파일명을 정의하고, 매개변수로 입력받은
     * 경로에 파일을 저장한다.
     * @param file : 클라이언트 요청으로 받은 MultipartFile 타입의 파일
     * @param path : 파일을 저장할 경로
     * @return 저장된 파일의 파일명 반환
     */
    public static String saveFile(MultipartFile file, String path) {
        if (file.isEmpty() || file.getContentType() == null) {
            throw new IllegalArgumentException();
        }

        String extension = file.getContentType().replaceFirst(".*/", ".");
        String uploadName = UUID.randomUUID() + extension;

        try {
            Files.createDirectories(Paths.get(path));
            file.transferTo(new File(path, uploadName));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        return uploadName;
    }

    /**
     * 디렉토리 내에 존재하는 모든 파일을 재귀적으로 탐색 및 삭제 후 해당 디렉토리도 삭제한다.
     * @param path : 삭제하려는 파일 경로
     */
    public static void deleteDirectory(String path) {
        File[] files = new File(path).listFiles();

        if (files == null) {
            return;
        }
        try {
            for (File f : files) {
                String target = path + "/" + f.getName();

                if (f.isDirectory()) {
                    deleteDirectory(target);
                }
                Files.deleteIfExists(Paths.get(target));
            }
            Files.deleteIfExists(Paths.get(path));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
