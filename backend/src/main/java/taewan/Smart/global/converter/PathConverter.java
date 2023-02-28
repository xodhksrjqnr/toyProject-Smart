package taewan.Smart.global.converter;


import taewan.Smart.global.util.PropertyUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 매개변수로 받은 경로에 대해 리소스 접근을 위한 추가적인 기본 경로를 추가한다.
 */
public class PathConverter {

    /**
     * 경로를 이미지 리소스에 접근하기 위한 url 형태로 변경한다.
     * @param imgPath : 이미지 파일의 위치
     * @return : 이미지 리소스 접근 url
     */
    public static String toImgAccessUrl(String imgPath) {
        return PropertyUtils.getAccessImgUrl() +
                (imgPath == null ? PropertyUtils.getEmptyImgPath() : imgPath);
    }

    /**
     * 모든 경로를 이미지 리소스에 접근하기 위한 url 형태로 변경한다.
     * @param imgPaths : 이미지 파일의 위치
     * @return 이미지 리소스 접근 url
     */
    public static List<String> toImgAccessUrl(List<String> imgPaths) {
        return imgPaths.stream()
                .map(p -> PropertyUtils.getAccessImgUrl() + (p == null ? PropertyUtils.getEmptyImgPath() : imgPaths))
                .collect(Collectors.toList());
    }

    /**
     * 경로를 이미지 리소스에 접근하기 위한 로컬 경로 형태로 변경한다.
     * @param imgPath : 이미지 파일의 위치
     * @return 이미지 리소스 접근 로컬 경로
     */
    public static String toImgAccessLocal(String imgPath) {
        return PropertyUtils.getImgFolderPath() + imgPath;
    }
}
