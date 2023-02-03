package taewan.Smart.category.service;

import taewan.Smart.category.dto.CategoryFullInfoDto;

import java.util.List;

public interface CategoryService {

    List<CategoryFullInfoDto> findAll();
}
