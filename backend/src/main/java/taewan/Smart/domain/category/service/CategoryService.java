package taewan.Smart.domain.category.service;

import taewan.Smart.domain.category.dto.CategoryInfoDto;
import taewan.Smart.domain.category.dto.CategorySaveDto;

import java.util.List;

public interface CategoryService {

    List<CategoryInfoDto> findAll();
    void save(CategorySaveDto dto);
}
