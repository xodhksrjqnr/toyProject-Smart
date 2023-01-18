package taewan.Smart.category.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import taewan.Smart.category.dto.CategoryFullInfoDto;
import taewan.Smart.category.dto.CategoryInfoDto;
import taewan.Smart.category.dto.CategoryItemInfoDto;
import taewan.Smart.category.entity.Category;
import taewan.Smart.category.entity.CategoryItem;
import taewan.Smart.category.repository.CategoryItemRepository;
import taewan.Smart.category.repository.CategoryRepository;

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

    public List<CategoryFullInfoDto> searchAll() {
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
