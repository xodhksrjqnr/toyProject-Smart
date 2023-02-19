package taewan.Smart.fixture;

import taewan.Smart.domain.category.dto.CategorySaveDto;

import java.util.ArrayList;
import java.util.List;

public class CategoryTestFixture {

    private static String[] category = {"상의", "하의", "아우터", "신발"};
    private static String[][] categoryItem = {
            {"후드 집업", "맨투맨"}, {"청바지", "숏팬츠"},
            {"코트", "패딩"}, {"운동화", "구두"}
    };

    private static final List<CategorySaveDto> categorySaveDtoList = createCategorySaveDtoList();

    public static CategorySaveDto createCategorySaveDto() {
        return createCategorySaveDto(0);
    }

    public static CategorySaveDto createCategorySaveDto(int index) {
        return CategorySaveDto.builder()
                .classification(category[index / 2])
                .middleClassification(categoryItem[index / 2][index % 2])
                .build();
    }

    public static List<CategorySaveDto> createCategorySaveDtoList() {
        return createCategorySaveDtoList(8);
    }

    public static List<CategorySaveDto> createCategorySaveDtoList(int size) {
        List<CategorySaveDto> list = new ArrayList<>();

        for (int i = 0; i < size; i++)
            list.add(createCategorySaveDto(i));
        return list;
    }

    public static List<CategorySaveDto> getCategorySaveDtoList() {
        return categorySaveDtoList;
    }
}
