package taewan.Smart.unit.utils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static taewan.Smart.fixture.CustomFileUtilsTestFixture.IMG_FOLDER_PATH;
import static taewan.Smart.fixture.CustomFileUtilsTestFixture.getImgFiles;
import static taewan.Smart.global.util.CustomFileUtils.*;

public class CustomFileUtilsTest {

    private String toImgAccessLocal(String path) {
        return IMG_FOLDER_PATH + "/" + path;
    }

    @AfterEach
    void destroy() {
        deleteDirectory(IMG_FOLDER_PATH);
    }

    @Test
    @DisplayName("파일 저장 테스트")
    void save() {
        //given
        MultipartFile imgFile = getImgFiles(1).get(0);

        //when
        String fileName = saveFile(imgFile, IMG_FOLDER_PATH);

        //then
        assertThat(new File(toImgAccessLocal(fileName)).exists()).isTrue();
    }

    @Test
    @DisplayName("디렉토리 내 하위 파일 전체 찾기 테스트")
    void findAll() {
        //given
        int count = 3;
        List<MultipartFile> files = getImgFiles(count);

        //when
        List<String> saved = saveFiles(files, IMG_FOLDER_PATH);
        List<String> found = findFilePaths(IMG_FOLDER_PATH);

        saved.sort(Comparator.naturalOrder());
        found.sort(Comparator.naturalOrder());

        //then
        assertEquals(found.size(), saved.size());
        for (int i = 0; i < found.size(); i++)
            assertThat(found.get(i).contains(saved.get(i))).isTrue();
    }

    @Test
    @DisplayName("멀티 쓰레드 환경에서의 파일 전체 찾기 테스트")
    void findAll_multi_thread() {
        //given
        int size = 3;
        Map<String, List<String>> saved = new HashMap<>();
        List<Thread> threads = new ArrayList<>();
        Map<String, List<String>> found = new HashMap<>();

        for (int i = 0; i < 10; i++) {
            String key = "/save" + (i + 1);
            saved.put(key, saveFiles(getImgFiles(size), IMG_FOLDER_PATH + key));
            threads.add(new Thread(
                    () -> {
                        found.put(key, findFilePaths(IMG_FOLDER_PATH + key));

                        List<String> s = saved.get(key);
                        List<String> f = found.get(key);

                        s.sort(Comparator.naturalOrder());
                        f.sort(Comparator.naturalOrder());
                        for (int j = 0; j < size; j++) {
                            assertThat(f.get(j).contains(s.get(j))).isTrue();
                        }
                    }
            ));
        }

        //when //then
        threads.parallelStream().forEach(t -> t.start());
    }

    @Test
    @DisplayName("존재하지 않는 디렉토리 내 파일 전체 찾기")
    void findAll_invalid_directory() {
        //when
        List<String> found = findFilePaths(IMG_FOLDER_PATH);

        //then
        assertThat(found).isEmpty();
    }

    @Test
    @DisplayName("디렉토리 삭제 테스트")
    void delete() {
        //given
        MultipartFile file = getImgFiles(1).get(0);
        List<MultipartFile> files = getImgFiles(3);

        saveFile(file, IMG_FOLDER_PATH + "/info");
        saveFiles(files, IMG_FOLDER_PATH + "/view");

        //when
        deleteDirectory(IMG_FOLDER_PATH);

        //then
        assertThat(new File(IMG_FOLDER_PATH).exists()).isFalse();
    }

    @Test
    @DisplayName("존재하지 않는 디렉토리 삭제 테스트")
    void delete_invalid_directory() {
        //when
        deleteDirectory(IMG_FOLDER_PATH);

        //then
        assertThat(new File(IMG_FOLDER_PATH).exists()).isFalse();
    }
}
