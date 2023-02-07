package taewan.Smart.domain.category.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import taewan.Smart.domain.category.dto.CategoryFullInfoDto;
import taewan.Smart.domain.category.dto.CategoryInfoDto;
import taewan.Smart.domain.category.dto.CategoryItemInfoDto;
import taewan.Smart.domain.category.entity.Category;
import taewan.Smart.domain.category.entity.CategoryItem;
import taewan.Smart.domain.category.repository.CategoryItemRepository;
import taewan.Smart.domain.category.repository.CategoryRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;
    private CategoryItemRepository categoryItemRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryItemRepository categoryItemRepository) {
        this.categoryRepository = categoryRepository;
        this.categoryItemRepository = categoryItemRepository;
    }

    public List<CategoryFullInfoDto> findAll() {
        List<Category> categories = categoryRepository.findAll(Sort.by("code"));
        List<CategoryItem> categoryItems = categoryItemRepository.findAll(Sort.by("parentCode"));
        List<CategoryFullInfoDto> result = new ArrayList<>();

        for (Category c : categories) {
            List<CategoryItemInfoDto> dtos = new ArrayList<>();
            while (!categoryItems.isEmpty() && categoryItems.get(0).getParentCode().equals(c.getCode()))
                dtos.add(new CategoryItemInfoDto(categoryItems.remove(0)));
            result.add(new CategoryFullInfoDto(new CategoryInfoDto(c), dtos));
        }
        return result;
    }
}
