package taewan.Smart.domain.category.service;

import taewan.Smart.domain.category.dto.CategoryFullInfoDto;

import java.util.List;

public interface CategoryService {

    List<CategoryFullInfoDto> findAll();
}
