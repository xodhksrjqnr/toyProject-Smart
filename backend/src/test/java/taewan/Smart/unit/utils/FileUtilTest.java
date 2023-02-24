package taewan.Smart.unit.utils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static taewan.Smart.fixture.FileUtilTestFixture.*;
import static taewan.Smart.global.utils.FileUtil.*;

public class FileUtilTest {

    private final String DIRECTORY_PATH = "product/";
    private final String EMPTY_PATH = "empty/noimg.jpeg";

    @AfterEach
    void destroy() {
        deleteDirectory(DIRECTORY_PATH);
    }

    @Test
    void 파일_저장() {
        //given
        MultipartFile imgFile = getImgFile();

        //when
        String fileName = saveFile(imgFile, DIRECTORY_PATH);

        //then
        assertThat(new File(IMG_FOLDER_PATH + DIRECTORY_PATH + fileName).isFile()).isTrue();
    }

    @Test
    void 파일_찾기() {
        //given
        MultipartFile imgFile = getImgFile();

        //when
        String saved = saveFile(imgFile, DIRECTORY_PATH);
        String found = findFile(DIRECTORY_PATH + saved);

        //then
        assertEquals(saved, found);
    }

    @Test
    void 없는_파일_찾기() {
        //given
        String name = "invalidFileName";

        //when //then
        assertThat(findFile(DIRECTORY_PATH + name)).isNull();
    }

    @Test
    void 디렉토리_내_파일_전체_찾기() {
        //given
        int count = 3;
        List<MultipartFile> files = getImgFiles(count);

        //when
        List<String> saved = saveFiles(files, DIRECTORY_PATH);
        List<String> found = findFiles(DIRECTORY_PATH);

        saved.sort((o1, o2) -> o1.compareTo(o2));
        found.sort((o1, o2) -> o1.compareTo(o2));

        //then
        assertEquals(found.size(), saved.size());
        for (int i = 0; i < found.size(); i++)
            assertEquals(found.get(i), saved.get(i));
    }

    @Test
    void 없는_디렉토리_내_파일_전체_찾기() {
        //when
        List<String> found = findFiles(DIRECTORY_PATH);

        //then
        assertEquals(found.size(), 1);
        assertThat(found.get(0)).isNull();
    }

    @Test
    void 디렉토리_삭제() {
        //given
        MultipartFile file = getImgFile();
        List<MultipartFile> files = getImgFiles(3);

        String save1 = saveFile(file, DIRECTORY_PATH);
        List<String> saved2 = saveFiles(files, DIRECTORY_PATH + "view/");

        //when
        deleteDirectory(DIRECTORY_PATH);

        //then
        assertThat(new File(IMG_FOLDER_PATH + DIRECTORY_PATH).exists()).isFalse();
    }

    @Test
    void 없는_디렉토리_삭제() {
        //when
        deleteDirectory(DIRECTORY_PATH);

        //then
        assertThat(new File(IMG_FOLDER_PATH + DIRECTORY_PATH).exists()).isFalse();
    }

    @Test
    void 파일_단일_경로_URL_변환() {
        //given
        List<String> saved = saveFiles(getImgFiles(3), DIRECTORY_PATH);

        //when
        String url = getAccessUrl(DIRECTORY_PATH + saved.get(0));

        //then
        assertEquals(url, ACCESS_IMG_URL + DIRECTORY_PATH + saved.get(0));
    }

    @Test
    void 없는_파일_단일_경로_URL_변환() {
        //given
        String fileName = "invalidFileName";

        //when
        String url = getAccessUrl(DIRECTORY_PATH + fileName);

        //then
        assertEquals(url, ACCESS_IMG_URL + EMPTY_PATH);
    }

    @Test
    void 파일_전체_경로_URL_변환() {
        //given
        List<String> saved = saveFiles(getImgFiles(3), DIRECTORY_PATH);

        //when
        List<String> urls = getAccessUrls(DIRECTORY_PATH);

        urls.sort((o1, o2) -> o1.compareTo(o2));
        saved.sort((o1, o2) -> o1.compareTo(o2));

        //then
        assertEquals(urls.size(), saved.size());
        for (int i = 0; i < urls.size(); i++)
            assertEquals(urls.get(i), ACCESS_IMG_URL + DIRECTORY_PATH + saved.get(i));
    }
}
